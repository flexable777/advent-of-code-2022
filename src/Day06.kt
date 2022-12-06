fun main() {
    fun findIndex(datastream: String, sequenceSize: Int): Int =
        datastream.windowed(size = sequenceSize, partialWindows = true)
            .indexOfFirst { s ->
                s.toSet().size == sequenceSize
            } + sequenceSize

    fun part1(input: String): Int {
        val sequenceSize = 4
        return findIndex(datastream = input, sequenceSize = sequenceSize)
    }

    fun part2(input: String): Int {
        val sequenceSize = 14
        return findIndex(datastream = input, sequenceSize = sequenceSize)
    }

    val testInput = readInput("Day06_test")
    check(part1(testInput) == 7)
    check(part2(testInput) == 19)

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}