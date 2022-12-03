fun main() {

    fun Char.getPriority(): Int =
        if (this in 'a'..'z') {
            this - 'a' + 1
        } else {
            this - 'A' + 27
        }

    fun part1(input: List<String>): Int =
        input.sumOf { line ->
            //Better way to split these?
            val middleIndex = line.length / 2
            val compartment1 = line.substring(0, middleIndex).map { it.getPriority() }
            val compartment2 = line.substring(middleIndex).map { it.getPriority() }
            compartment1.intersect(compartment2).first()
        }

    fun part2(input: List<String>): Int =
        //How to intersect multiple sets in Kotlin?
        input.chunked(3).sumOf { chunk ->
            chunk.flatMap { it.toSet().map { char -> char.getPriority() } }
                .groupBy { it }
                .filter { it.value.size == 3 }
                .keys.first()
        }

    val testInput = readInputAsLines("Day03_test")
    check(part1(testInput) == 157)
    check(part2(testInput) == 70)

    val input = readInputAsLines("Day03")
    println(part1(input))
    println(part2(input))
}