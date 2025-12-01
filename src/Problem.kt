import java.io.File

abstract class Problem(number: Int) {
    val input = this.getParsedInput("problem_${number}_input.txt")
    val testInput = this.getParsedInput("problem_${number}_test_input.txt")

    private fun getParsedInput(filename: String): List<String> {
        return File("./src/problems/input/$filename")
            .bufferedReader()
            .readLines()
    }

    abstract fun solve(test: Boolean = false)
}