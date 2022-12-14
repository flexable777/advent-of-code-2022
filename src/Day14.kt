fun main() {

    data class Coordinate(val row: Int, val col: Int, val type: String = "#") {
        override fun equals(other: Any?): Boolean {
            other as Coordinate
            return this.row == other.row && this.col == other.col
        }

        override fun hashCode(): Int {
            return this.row.hashCode() + this.col.hashCode()
        }
    }

    fun List<String>.toCoordinate(): Coordinate {
        return Coordinate(row = this[1].toInt(), col = this[0].toInt())
    }

    infix fun Int.toward(to: Int): IntProgression {
        val step = if (this > to) -1 else 1
        return IntProgression.fromClosedRange(this, to, step)
    }

    fun printBoard(takenTiles: Set<Coordinate>) {
        val minRow = 0
        val maxRow = takenTiles.maxBy { it.row }.row + 2

        val minCol = takenTiles.minBy { it.col }.col
        val maxCol = takenTiles.maxBy { it.col }.col

        for (row in minRow..maxRow) {
            for (col in minCol..maxCol) {
                if (Coordinate(row, col) in takenTiles) {
                    print(takenTiles.find { it.row == row && it.col == col }!!.type)
                } else {
                    print(".")
                }
            }
            println()
        }
    }

    fun part1(input: List<String>): Int {
        val takenTiles = mutableSetOf<Coordinate>()

        input.forEach { toParse ->
            toParse.replace(" ", "").split("->").windowed(size = 2).forEach { i ->
                val from = i[0].split(",").toCoordinate()
                val to = i[1].split(",").toCoordinate()

                if (from.row == to.row) {
                    for (i in from.col toward to.col) {
                        takenTiles += Coordinate(row = from.row, col = i)
                    }
                } else if (from.col == to.col) {
                    for (i in from.row toward to.row) {
                        takenTiles += Coordinate(row = i, col = from.col)
                    }
                } else {
                    error("how?")
                }
            }
        }

        var sandCounter = 0

        var moves: Int

        val sandStartCoordinate = Coordinate(row = 0, col = 500, type = "o")

        while (true) {
            var attemptedSandMove = sandStartCoordinate.copy()

            moves = 0

            val maxMoves = 300
            while (attemptedSandMove !in takenTiles && moves <= maxMoves) {
                moves++

                //try to go straight down
                if (attemptedSandMove.copy(row = attemptedSandMove.row + 1) !in takenTiles) {
                    attemptedSandMove = attemptedSandMove.copy(row = attemptedSandMove.row + 1)
                    continue
                }

                //try down left
                if (attemptedSandMove.copy(
                        row = attemptedSandMove.row + 1,
                        col = attemptedSandMove.col - 1
                    ) !in takenTiles
                ) {
                    attemptedSandMove =
                        attemptedSandMove.copy(row = attemptedSandMove.row + 1, col = attemptedSandMove.col - 1)
                    continue
                }

                //try down right
                if (attemptedSandMove.copy(
                        row = attemptedSandMove.row + 1,
                        col = attemptedSandMove.col + 1
                    ) !in takenTiles
                ) {
                    attemptedSandMove =
                        attemptedSandMove.copy(row = attemptedSandMove.row + 1, col = attemptedSandMove.col + 1)
                    continue
                }
                break
            }

            if (moves >= maxMoves) {
                break
            }

            //sand landed
            takenTiles += attemptedSandMove
            sandCounter++

            //printboard
//            println("== Sand $sandCounter ==")
//            printBoard(takenTiles)
//            println()
        }

        return sandCounter
    }

    fun part2(input: List<String>): Int {
        val takenTiles = mutableSetOf<Coordinate>()

        input.forEach { toParse ->
            toParse.replace(" ", "").split("->").windowed(size = 2).forEach { i ->
                val from = i[0].split(",").toCoordinate()
                val to = i[1].split(",").toCoordinate()

                if (from.row == to.row) {
                    for (i in from.col toward to.col) {
                        takenTiles += Coordinate(row = from.row, col = i)
                    }
                } else if (from.col == to.col) {
                    for (i in from.row toward to.row) {
                        takenTiles += Coordinate(row = i, col = from.col)
                    }
                } else {
                    error("how?")
                }
            }
        }

        //Add floor
        val floorRow = takenTiles.maxBy { it.row }.row + 2

        var sandCounter = 0

        var moves: Int

        val sandStartCoordinate = Coordinate(row = 0, col = 500, type = "o")

        while (true) {
            var attemptedSandMove = sandStartCoordinate.copy()

            if (attemptedSandMove in takenTiles) {
                //full
                break
            }

            moves = 0

            val maxMoves = 300
            while (attemptedSandMove !in takenTiles && moves <= maxMoves) {
                moves++

                if (floorRow == attemptedSandMove.row + 1) {
                    break
                }

                //try to go straight down
                if (attemptedSandMove.copy(row = attemptedSandMove.row + 1) !in takenTiles) {
                    attemptedSandMove = attemptedSandMove.copy(row = attemptedSandMove.row + 1)
                    continue
                }

                //try down left
                if (attemptedSandMove.copy(
                        row = attemptedSandMove.row + 1,
                        col = attemptedSandMove.col - 1
                    ) !in takenTiles
                ) {
                    attemptedSandMove =
                        attemptedSandMove.copy(row = attemptedSandMove.row + 1, col = attemptedSandMove.col - 1)
                    continue
                }

                //try down right
                if (attemptedSandMove.copy(
                        row = attemptedSandMove.row + 1,
                        col = attemptedSandMove.col + 1
                    ) !in takenTiles
                ) {
                    attemptedSandMove =
                        attemptedSandMove.copy(row = attemptedSandMove.row + 1, col = attemptedSandMove.col + 1)
                    continue
                }
                break
            }

            if (moves >= maxMoves) {
                break
            }

            //sand landed
            takenTiles += attemptedSandMove
            sandCounter++

            //printboard
//            println("== Sand $sandCounter ==")
//            printBoard(takenTiles)
//            println()
        }

        return sandCounter
    }

    val testInput = readInputAsLines("Day14_test")
    check(part1(testInput) == 24)
    check(part2(testInput) == 93)

    val input = readInputAsLines("Day14")
    println("Part 1 : " + part1(input))
    println("Part 2 : " + part2(input))
}