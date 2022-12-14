fun main() {

    fun Char.getPriority(): Int =
        if (this.isLowerCase()) {
            this - 'a' + 1
        } else {
            this - 'A' + 27
        }

    fun part1(input: List<String>): Int =
        input.sumOf { line ->
            //Better way to split these?
            val middleIndex = line.length / 2
            val compartment1 = line.substring(0, middleIndex).map { it.getPriority() }.toSet()
            val compartment2 = line.substring(middleIndex).map { it.getPriority() }.toSet()
            compartment1.intersect(compartment2).single()
        }

    fun part2(input: List<String>): Int {
        //Alt. 1
//        input.chunked(3).sumOf { chunk ->
//            chunk.flatMap { it.toSet().map { char -> char.getPriority() } }
//                .groupBy { it }
//                .filter { it.value.size == 3 }
//                .keys.first()
//        }

        //Alt. 2
        return input.chunked(3).sumOf { chunk ->
            chunk.reduce { acc, s ->
                s.toSet().intersect(acc.toSet()).joinToString(separator = "")
            }.map { it.getPriority() }.sum()
        }

        //Alt. 3 retainAll?
    }

    val testInput = readInputAsLines("Day03_test")
    check(part1(testInput) == 157)
    check(part2(testInput) == 70)

    val input = readInputAsLines("Day03")
    println(part1(input))
    println(part2(input))
}