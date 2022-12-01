fun main() {
    fun part1(input: String): Int {
        return input.split("\n\n").maxOf {
            it.split("\n").sumOf { it.toInt() }
        }
    }

    fun part2(input: String): Int {
        return input.split("\n\n").map {
            it.split("\n").sumOf { it.toInt() }
        }.sortedDescending().subList(0, 3).sum()
    }

    val testInput = readInput("Day01_test")

    check(part1(testInput) == 24000)
    check(part2(testInput) == 45000)

    val input = readInput("Day01")

    println(part1(input))
    println(part2(input))

    /*
    70509
    208567
     */
}