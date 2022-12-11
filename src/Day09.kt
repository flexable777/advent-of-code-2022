fun main() {

    infix fun Pair<Int, Int>.isCloseEnough(other: Pair<Int, Int>): Boolean {
        val (tailRow, tailCol) = this
        val (headRow, headCol) = other

        return (tailRow == headRow - 1 || tailRow == headRow || tailRow == headRow + 1) &&
                (tailCol == headCol - 1 || tailCol == headCol || tailCol == headCol + 1)
    }

    fun printBoard(headPosition: Pair<Int, Int>, tailPosition: Pair<Int, Int>) {
        val (tailRow, tailCol) = tailPosition
        val (headRow, headCol) = headPosition

        for (row in 0..4) {
            for (col in 0..5) {
                if (tailRow == row && tailCol == col && headRow == row && headCol == col) {
                    print("H")
                } else if (tailRow == row && tailCol == col) {
                    print("T")
                } else if (headRow == row && headCol == col) {
                    print("H")
                } else {
                    print(".")
                }
            }
            println()
        }
    }

    fun printBoardVisited(visited: Set<Pair<Int, Int>>, colSize: Int, rowSize: Int) {
        for (row in 0 until rowSize) {
            for (col in 0 until colSize) {
                if (row to col in visited) {
                    print("#")
                } else {
                    print(".")
                }
            }
            println()
        }
    }

    fun simulateMotions(snakeSize: Int, input: List<String>): Int {
        val placesVisitedByTail = mutableSetOf<Pair<Int, Int>>()

        var row = 0
        var col = 0

        val movingParts = mutableListOf<Pair<Int, Int>>()
        repeat(snakeSize) {
            movingParts += row to col
        }

        placesVisitedByTail += row to col

        input.forEach { instruction ->
            val (direction, amount) = instruction.split(" ")

            repeat(amount.toInt()) {
                //move head
                when (direction) {
                    "R" -> {
                        movingParts[0] = row to ++col
                    }

                    "L" -> {
                        movingParts[0] = row to --col
                    }

                    "U" -> {
                        movingParts[0] = --row to col
                    }

                    "D" -> {
                        movingParts[0] = ++row to col
                    }
                }

                for ((index, tailPosition) in movingParts.withIndex()) {
                    val headToFollow = if (index == 0) {
                        movingParts.first()
                    } else {
                        movingParts[index - 1]
                    }

                    if (tailPosition isCloseEnough headToFollow) {
                        //Don't move
                        continue
                    } else {
                        //Move tail
                        //simple right
                        if (headToFollow.first == tailPosition.first && headToFollow.second > tailPosition.second) {
                            movingParts[index] = tailPosition.first to tailPosition.second + 1
                        }
                        //simple left
                        else if (headToFollow.first == tailPosition.first && headToFollow.second < tailPosition.second) {
                            movingParts[index] = tailPosition.first to tailPosition.second - 1
                        }
                        //up diagonally
                        else if (headToFollow.first < tailPosition.first && headToFollow.second != tailPosition.second) {
                            //which way?
                            movingParts[index] = if (headToFollow.second > tailPosition.second) {
                                tailPosition.first - 1 to tailPosition.second + 1
                            } else {
                                tailPosition.first - 1 to tailPosition.second - 1
                            }
                        }
                        //down diagonally
                        else if (headToFollow.first > tailPosition.first && headToFollow.second != tailPosition.second) {
                            //which way?
                            movingParts[index] = if (headToFollow.second > tailPosition.second) {
                                tailPosition.first + 1 to tailPosition.second + 1
                            } else {
                                tailPosition.first + 1 to tailPosition.second - 1
                            }
                        }
                        //simple up
                        else if (headToFollow.first < tailPosition.first) {
                            movingParts[index] = tailPosition.first - 1 to tailPosition.second
                        }
                        //simple down
                        else if (headToFollow.first > tailPosition.first) {
                            movingParts[index] = tailPosition.first + 1 to tailPosition.second
                        }

                        //only keep track of last tail
                        if (index == movingParts.lastIndex) {
                            placesVisitedByTail += movingParts[index]
                        }
                    }
                }
                //printBoard(headPosition, tailPosition)
            }
        }
        return placesVisitedByTail.size
    }

    fun part1(input: List<String>): Int = simulateMotions(snakeSize = 2, input = input)

    fun part2(input: List<String>): Int = simulateMotions(snakeSize = 10, input = input)

    val testInput = readInputAsLines("Day09_test")
    check(part1(testInput) == 13)

    val testInputPart2 = readInputAsLines("Day09_part2_test")
    check(part2(testInputPart2) == 36)

    val input = readInputAsLines("Day09")
    println("Part 1: " + part1(input))
    println("Part 2: " + part2(input))
}