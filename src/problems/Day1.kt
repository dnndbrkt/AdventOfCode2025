package problems

import Problem
import problems.Dial.Companion.MAX
import problems.Dial.Companion.rotatedBy
import kotlin.Int
import kotlin.math.abs

class Dial {
    var position: Int = 50
        private set
    var zeroesAfterRotations: Int = 0
        private set
    var allZeroClicks: Int = 0
        private set

    fun rotate(rotation: Int) {
        val newPosition = position rotatedBy rotation

        val fullRotations = abs((rotation / MAX))
        val goesThroughZero = if (position + rotation.rem(MAX) !in 0..100 && position != 0) 1 else 0
        val endsAtZero = if (newPosition == 0) 1 else 0

        position = newPosition
        zeroesAfterRotations += endsAtZero
        allZeroClicks += fullRotations + goesThroughZero + endsAtZero
    }

    companion object {
        const val MAX: Int = 100

        infix fun Int.rotatedBy(other: Int): Int = (this + other).mod(MAX)
    }
}

class Day1 : Problem(1) {
    override fun solve(test: Boolean) {
        val problemInput = (if (test) testInput else input).map { it.parseLine() }
        solveAV1(problemInput)
        solveAV2(problemInput)
        solveAV3(problemInput)
        solveBV1(problemInput)
        solveBV2(problemInput)
    }

    fun String.parseLine(): Int {
        val (dir, clicks) = Pair(this[0], this.slice(1..<this.length).toInt())
        return if (dir == 'L') -1 * clicks else clicks
    }

    fun solveAV1(input: List<Int>) {
        val rotations = input
            .mapIndexed { i, item ->
                val offset = if (i == 0) 50 else 0
                offset + item
            }
        val dialsAfterRotation = rotations
            .mapIndexed { index, _ -> rotations.slice(0..index).sum() }
            .map { it.mod(100) }

        val rotationsEndingAtZero = dialsAfterRotation

            .filter { it == 0 }
            .size

        println("First half answer (version 1): $rotationsEndingAtZero")
    }

    fun solveAV2(input: List<Int>) = Dial().apply {
        input.forEach { rotate(it) }
        println("First half answer (version 2): $zeroesAfterRotations")
    }

    fun solveAV3(input: List<Int>) {
        var rotationsEndingAtZero = 0
        input.fold(initial = 50) { currentPosition, rotation ->
            val newPosition = currentPosition rotatedBy rotation
            if (newPosition == 0) rotationsEndingAtZero++
            newPosition
        }
        println("First half answer (version 3): $rotationsEndingAtZero")
    }

    fun solveBV1(input: List<Int>) = Dial().apply {
        input.forEach { rotate(it) }
        println("Second half answer (version 1): $allZeroClicks")
    }

    fun solveBV2(input: List<Int>) {
        var allZeroClicks = 0

        input.fold(50) { currentPosition, rotation ->
            val newPosition = currentPosition rotatedBy rotation

            val fullRotations = abs((rotation / MAX))
            val goesThroughZero = if (currentPosition + rotation.rem(MAX) !in 0..100 && currentPosition != 0) 1 else 0
            val endsAtZero = if (newPosition == 0) 1 else 0

            allZeroClicks += fullRotations + goesThroughZero + endsAtZero

            newPosition
        }

        println("Second half answer (version 2): $allZeroClicks")
    }
}

fun main() {
    Day1().solve(test = false)
}