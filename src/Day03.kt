fun main() {
    fun part1(input: List<String>): Int {
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val testInput = readInputAsLines("Day03_test")
    check(part1(testInput) == 2)

    val input = readInputAsLines("Day03")
    println(part1(input))
    println(part2(input))
}