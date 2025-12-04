package problems

import Problem

class Day3 : Problem(3) {
    override fun solve(test: Boolean) {
        val problemInput = (if (test) testInput else input).map { it.parseLine() }

        solveA(problemInput)
        solveB(problemInput)
    }

    fun solveA(input: List<List<Int>>) {
        val res = input.fold(0L) { currentJoltage, bank ->
            val joltage = bank.joltage12()
            currentJoltage + joltage.toLong()
        }
        println("First half answer: $res")
    }

    fun solveB(input: List<List<Int>>) {
        val res = input.fold(0L) { currentJoltage, bank ->
            val joltage = bank.joltage2()
            currentJoltage + joltage
        }
        println("Second half answer: $res")
    }

    fun String.parseLine(): List<Int> = this.map { it.digitToInt() }


    fun List<Int>.maxElementInSublist(lowerIndex: Int, upperIndex: Int): Pair<Int, Int> {
        // Returns the maximum element in a sublist specified by a lower bound (inclusive) and upper bound (exclusive)
        // and also returns its index in the original list.
        val sublist = this.subList(lowerIndex, upperIndex)
        val max = sublist.max()
        return Pair(max, lowerIndex + sublist.indexOf(max))
    }

    fun List<Int>.joltage12(): String {
        var count = ""
        var currIndex = 0
        println(this)
        (11 downTo 0).forEach {
            val (joltage, maxIndexInSublist) = maxElementInSublist(
                lowerIndex = currIndex,
                upperIndex = this.size - it
            )
            count += joltage.digitToChar()
            currIndex = maxIndexInSublist + 1
        }
        return count
    }

    fun List<Int>.joltage2(): Int {
        val max = this.subList(0, this.size - 1).max()
        val indexOfMax = indexOf(max)
        val secondMax = this.subList(indexOfMax + 1, this.size).max()
        return max * 10 + secondMax
    }
}

fun main() {
    Day3().solve(test = false)
}