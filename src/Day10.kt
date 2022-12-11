fun main() {
    fun part1(input: List<String>): Int {
        var x = 1
        var totalSignalStrength = 0
        var cycle = 1
        input.forEach { instruction ->
            if (instruction == "noop") {
//                println("start of cycle $cycle, x = $x")
//                println("no operation")

                //check during
                if (cycle in listOf(20, 60, 100, 140, 180, 220)) {
                    val signalStrength = cycle * x
//                    println("signalStrength during cycle $cycle: $signalStrength")
                    totalSignalStrength += signalStrength
                }

//                println("end of cycle $cycle, x = $x")
                cycle++
            } else {
//                println("start of cycle $cycle, x = $x")

                //check during
                if (cycle in listOf(20, 60, 100, 140, 180, 220)) {
                    val signalStrength = cycle * x
//                    println("signalStrength during cycle $cycle: $signalStrength")
                    totalSignalStrength += signalStrength
                }

                //add instruction
                val (action, amount) = instruction.split(" ")
//                println("action: $action, amount: $amount")
                val toAddInNextCycle = amount.toInt()

//                println("end of cycle $cycle, x = $x")
                cycle++
//                println()
//                println("start of cycle $cycle, x = $x")

                //during
                if (cycle in listOf(20, 60, 100, 140, 180, 220)) {
                    val signalStrength = cycle * x
//                    println("signalStrength during cycle $cycle: $signalStrength")
                    totalSignalStrength += signalStrength
                }

                //after
                //perform previous cycles instructions
                x += toAddInNextCycle

//                println("end of cycle $cycle, x = $x")
                cycle++
            }
        }

        return totalSignalStrength
    }

    fun part2(input: List<String>): String {
        val result = mutableListOf<Char>()

        var x = 1
        var cycle = 0
        input.forEach { instruction ->
            if (instruction == "noop") {
                //check during
                result += if (x == cycle || (x - 1) == cycle || (x + 1) == cycle) {
                    '#'
                } else {
                    ' '
                }
                cycle++
            } else {
                //check during
                result += if (x == cycle || (x - 1) == cycle || (x + 1) == cycle) {
                    '#'
                } else {
                    ' '
                }

                //add instruction
                val (action, amount) = instruction.split(" ")
                val toAddInNextCycle = amount.toInt()

                cycle++

                //check during
                result += if (x == cycle || (x - 1) == cycle || (x + 1) == cycle) {
                    '#'
                } else {
                    ' '
                }

                //after
                //perform previous cycles instructions
                x += toAddInNextCycle

                cycle++
            }

            if (cycle == 40) {
                cycle = 0
            }
        }

        return result.chunked(40).map { line ->
            line.joinToString("") { it.toString() }
        }.joinToString("") { it + "\n" }
    }

    val testInput = readInputAsLines("Day10_test")

    check(part1(testInput) == 13140)

//    part2(testInput)

    val input = readInputAsLines("Day10")
    println("Part 1: " + part1(input))
    println("Part 2:\n" + part2(input))
}