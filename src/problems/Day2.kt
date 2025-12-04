package problems

import Problem

class Day2 : Problem(2) {
    override fun solve(test: Boolean) {
        val problemInput = (if (test) testInput else input).map { it.parseLine() }.first()
        solveA(problemInput)
        solveB(problemInput)
    }

    fun String.parseLine(): List<LongRange> {
        val listOfRanges = this
            .split(',')
            .map { range -> range.split('-').map { it.toLong() } }
        return listOfRanges.map { it[0].rangeTo(it[1]) }
    }

    fun Long.isInvalidDueToTwoRepeatingSequences(): Boolean {
        val length = this.toString().length
        if (length % 2 != 0) return false
        else {
            val divisor = (10L exp (length / 2)).toLong()
            return this.floorDiv(divisor) == this.rem(divisor)
        }
    }

    fun String.isInvalidDueToRepeatingSequencesOfLength(sequenceLength: Int): Boolean {
        val numberOfDigits = this.length

        if (numberOfDigits == 1 || numberOfDigits % sequenceLength != 0) return false

        val sequence = this.slice(0..<sequenceLength)

        return "($sequence)+".toRegex() matches this
    }

    fun String.isInvalid(): Boolean =
        (1..this.length / 2).map { length -> this.isInvalidDueToRepeatingSequencesOfLength(length) }
            .contains(true)


    fun String.isInvalidButCheckedByUsingFold(): Boolean {
        return (1..this.length / 2).fold(false) { current, length ->
            current || this.isInvalidDueToRepeatingSequencesOfLength(length)
        }
    }

    fun solveA(input: List<LongRange>) {
        val invalidIDsV1 = input
            .flatMap { range -> range.filter { it.isInvalidDueToTwoRepeatingSequences() } }
        val invalidIDsV2 = input
            .flatMap { range ->
                range.filter {
                    val id = it.toString()
                    id.isInvalidDueToRepeatingSequencesOfLength(id.length / 2)
                }
            }

        println("First half answer (version 1): ${invalidIDsV1.sum()}")
        println("First half answer (version 2): ${invalidIDsV2.sum()}")
    }

    fun solveB(input: List<LongRange>) {
        val invalidIDs = input
            .flatMap { range -> range.filter { it.toString().isInvalid() } }

        println("Second half answer: ${invalidIDs.sum()}")
    }
}

fun main() {
    Day2().solve(test = false)
}