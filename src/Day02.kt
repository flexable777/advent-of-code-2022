import Draw.*

enum class Draw(val value: Int) {
    ROCK(1), PAPER(2), SCISSORS(3)
}

fun main() {

    fun getDraw(draw: String): Draw {
        return when (draw) {
            "A", "X" -> ROCK
            "B", "Y" -> PAPER
            "C", "Z" -> SCISSORS
            else -> error("Invalid draw")
        }
    }

    fun calculateScore(opponentDraw: Draw, playerDraw: Draw): Int {
        return when (opponentDraw) {
            ROCK -> {
                when (playerDraw) {
                    ROCK -> ROCK.value + 3
                    PAPER -> PAPER.value + 6
                    SCISSORS -> SCISSORS.value + 0
                }
            }

            PAPER -> {
                when (playerDraw) {
                    ROCK -> ROCK.value + 0
                    PAPER -> PAPER.value + 3
                    SCISSORS -> SCISSORS.value + 6
                }
            }

            SCISSORS -> {
                when (playerDraw) {
                    ROCK -> ROCK.value + 6
                    PAPER -> PAPER.value + 0
                    SCISSORS -> SCISSORS.value + 3
                }
            }
        }
    }

    fun chooseMyDraw(opponentDraw: Draw, strategy: String): Draw {
        return when (opponentDraw) {
            ROCK -> {
                when (strategy) {
                    "X" -> SCISSORS
                    "Y" -> ROCK
                    "Z" -> PAPER
                    else -> error("Invalid strategy")
                }
            }

            PAPER -> {
                when (strategy) {
                    "X" -> ROCK
                    "Y" -> PAPER
                    "Z" -> SCISSORS
                    else -> error("Invalid strategy")
                }
            }

            SCISSORS -> {
                when (strategy) {
                    "X" -> PAPER
                    "Y" -> SCISSORS
                    "Z" -> ROCK
                    else -> error("Invalid strategy")
                }
            }
        }
    }

    fun part1(input: List<String>): Int =
        input.sumOf { line ->
            val (opponentDraw, playerDraw) = line.split(" ").map { getDraw(it) }
            calculateScore(opponentDraw, playerDraw)
        }

    fun part2(input: List<String>): Int =
        input.sumOf { line ->
            val (opponentDraw, strategy) = line.split(" ")
            calculateScore(getDraw(opponentDraw), chooseMyDraw(getDraw(opponentDraw), strategy))
        }

    val testInput = readInputAsLines("Day02_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 12)

    val input = readInputAsLines("Day02")
    println(part1(input))
    println(part2(input))
}