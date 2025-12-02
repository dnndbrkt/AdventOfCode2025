package problems

import Problem
import kotlin.math.pow


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

    fun checkInvalidityDueToTwoSequencesUsingDivision(id: Long): Boolean {
        val length = id.toString().length
        if (length % 2 != 0) return false
        else {
            val divisor = (10L exp (length / 2)).toLong()
            return id.floorDiv(divisor) == id.rem(divisor)
        }
    }

    fun checkInvalidityUsingRegex(id: String, sequenceLength: Int): Boolean {
        val numberOfDigits = id.length

        if (numberOfDigits == 1 || numberOfDigits % sequenceLength != 0) return false

        val sequence = id.slice(0..<sequenceLength)

        return "($sequence)+".toRegex() matches id
    }

    fun checkInvalidityForAllSequenceLengthsWithRegex(id: String): Boolean {
        return (true in (1..id.length / 2).map { checkInvalidityUsingRegex(id, sequenceLength = it) })
    }

    fun checkInvalidityForAllSequenceLengthsWithRegexAndFold(id: String): Boolean {
        return (1..id.length / 2).fold(false) { current, length ->
            current || checkInvalidityUsingRegex(
                id,
                length
            )
        }
    }

    infix fun Long.exp(power: Int) = this.toFloat().pow(power)

    fun solveA(input: List<LongRange>) {
        val invalidIDsV1 = input
            .flatMap { range -> range.filter { checkInvalidityDueToTwoSequencesUsingDivision(it) } }
        val invalidIDsV2 = input
            .flatMap { range ->
                range.filter {
                    checkInvalidityUsingRegex(
                        it.toString(),
                        sequenceLength = (it.toString().length) / 2
                    )
                }
            }

        println("First half answer (version 1): ${invalidIDsV1.sum()}")
        println("First half answer (version 2): ${invalidIDsV2.sum()}")
    }

    fun solveB(input: List<LongRange>) {
        val invalidIDs = input
            .flatMap { range -> range.filter { checkInvalidityForAllSequenceLengthsWithRegex(it.toString()) } }

        println("Second half answer: ${invalidIDs.sum()}")
    }
}

fun main() {
    Day2().solve(test = false)
}