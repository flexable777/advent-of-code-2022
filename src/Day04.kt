fun main() {

    infix fun IntRange.contains(other: IntRange): Boolean = this.first <= other.first && this.last >= other.last

    fun getRangePairs(line: String): Pair<IntRange, IntRange> {
        //Probably are some better ways of splitting
        val split = line.split(",")
        val assignment1 = split[0].split("-")
        val assignment1Range = assignment1[0].toInt()..assignment1[1].toInt()
        val assignment2 = split[1].split("-")
        val assignment2Range = assignment2[0].toInt()..assignment2[1].toInt()
        return Pair(assignment1Range, assignment2Range)
    }

    fun part1(input: List<String>): Int =
        input.count { line ->
            val (assignment1Range, assignment2Range) = getRangePairs(line)
            assignment1Range contains assignment2Range || assignment2Range contains assignment1Range
        }

    infix fun IntRange.overlaps(other: IntRange): Boolean {
        //TODO Could be improved
        for (n in this) {
            if (n in other) {
                return true
            }
        }
        return false
    }

    fun part2(input: List<String>): Int =
        input.count { line ->
            val (assignment1Range, assignment2Range) = getRangePairs(line)
            assignment1Range overlaps assignment2Range
        }

    val testInput = readInputAsLines("Day04_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInputAsLines("Day04")
    println(part1(input))
    println(part2(input))
}