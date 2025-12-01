package problems

import Problem
import kotlin.math.abs

class Dial {
    val max: Int = 100
    var position: Int = 50
        private set
    var zeroesAfterRotations: Int = 0
        private set
    var allZeroClicks: Int = 0
        private set

    fun getNewPosition(other: Int): Int = (position + other).mod(max)

    fun rotate(other: Int) {
        val newPosition = getNewPosition(other)

        val fullRotations = abs((other / max))
        val goesThroughZero = if (position + other.rem(max) !in 0..100 && position != 0) 1 else 0
        val endsAtZero = if (newPosition == 0) 1 else 0

        position = newPosition
        zeroesAfterRotations += endsAtZero
        allZeroClicks += fullRotations + goesThroughZero + endsAtZero
    }

}

class Day1 : Problem(1) {
    override fun solve(test: Boolean) {
        val problemInput = (if (test) testInput else input).map { it.parseLine() }
        solveAV1(problemInput)
        solveAV2(problemInput)
        solveB(problemInput)
    }

    fun String.parseLine(): Int {
        val (dir, clicks) = Pair(this[0], this.slice(1..<this.length).toInt())
        return if (dir == 'L') -1 * clicks else clicks
    }

    fun solveAV2(input: List<Int>) = Dial().apply {
        input.forEach { rotate(it) }
        println("First half answer (version 2): $zeroesAfterRotations")
    }

    fun solveB(input: List<Int>) = Dial().apply {
        input.forEach { rotate(it) }
        println("Second half answer: $allZeroClicks")
    }

    fun solveAV1(input: List<Int>) {
        val rotations = input
            .mapIndexed { i, item ->
                val offset = if (i == 0) 50 else 0
                offset + item
            }
        val dialsAfterRotation = rotations
            .mapIndexed { index, _ -> rotations.slice(0..index).sum() }

        val rotationsEndingAtZero = dialsAfterRotation
            .map { it.mod(100) }
            .filter { it == 0 }
            .size
        println("First half answer (version 1): $rotationsEndingAtZero")
    }
}

fun main() {
    Day1().solve(test = false)
}