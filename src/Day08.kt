fun main() {


    fun part1(input: List<String>): Int {
        val countedTrees = mutableSetOf<Pair<Int, Int>>()

        //read input as ints
        val matrix = input.map { r ->
            r.toList().map { it.digitToInt() }
        }
        val indices = matrix.first().indices

        var previouslyHighestTree: Int

        //first from left to right
        for (x in indices) {
            previouslyHighestTree = -1
            for (y in indices) {
                if (matrix[x][y] > previouslyHighestTree) {
                    countedTrees += x to y
                    previouslyHighestTree = matrix[x][y]
                }
            }
        }

        //then from right to left
        for (x in indices) {
            previouslyHighestTree = -1
            for (y in indices.reversed()) {
                if (matrix[x][y] > previouslyHighestTree) {
                    countedTrees += x to y
                    previouslyHighestTree = matrix[x][y]
                }
            }
        }

        //then from up to down
        for (x in indices) {
            previouslyHighestTree = -1
            for (y in indices) {
                if (matrix[y][x] > previouslyHighestTree) {
                    countedTrees += y to x
                    previouslyHighestTree = matrix[y][x]
                }
            }
        }

        //then from down to up
        for (x in indices) {
            previouslyHighestTree = -1
            for (y in indices.reversed()) {
                if (matrix[y][x] > previouslyHighestTree) {
                    countedTrees += y to x
                    previouslyHighestTree = matrix[y][x]
                }
            }
        }

        return countedTrees.size
    }

    //TODO heavy refactor needed

    fun getScenicScoreLeftToRight(x: Int, y: Int, matrix: List<List<Int>>): Int {
        val myTreeHeight = matrix[x][y]

        var previouslyHighestTree = myTreeHeight
        var count = 0

        if (y == matrix.lastIndex) {
            return 0
        }

        for (j in (y + 1)..matrix.lastIndex) {
            count++
            if (matrix[x][j] >= myTreeHeight) {
                break
            }

            if (matrix[x][j] <= previouslyHighestTree) {
                if (matrix[x][j] == myTreeHeight) {
                    return count
                }
                if (matrix[x][j] > previouslyHighestTree) {
                    previouslyHighestTree = matrix[x][j]
                }
            } else {
                break
            }
        }
        return count
    }

    fun getScenicScoreRightToLeft(x: Int, y: Int, matrix: List<List<Int>>): Int {
        val myTreeHeight = matrix[x][y]

        var previouslyHighestTree = myTreeHeight
        var count = 0

        if (y == 0) {
            return 0
        }

        for (j in (0 until y).reversed()) {
            count++
            if (matrix[x][j] >= myTreeHeight) {
                break
            }
            if (matrix[x][j] <= previouslyHighestTree) {

                if (matrix[x][j] == myTreeHeight) {
                    return count
                }
                if (matrix[x][j] > previouslyHighestTree) {
                    previouslyHighestTree = matrix[x][j]
                }
            } else {
                break
            }
        }
        return count
    }

    fun getScenicScoreUpToDown(x: Int, y: Int, matrix: List<List<Int>>): Int {
        val myTreeHeight = matrix[x][y]

        var previouslyHighestTree = myTreeHeight
        var count = 0

        if (x == matrix.lastIndex) {
            return 0
        }

        for (i in (x + 1)..matrix.lastIndex) {
            count++
            if (matrix[i][y] >= myTreeHeight) {
                break
            }
            if (matrix[i][y] <= previouslyHighestTree) {

                if (matrix[i][y] == myTreeHeight) {
                    return count
                }
                if (matrix[i][y] > previouslyHighestTree) {
                    previouslyHighestTree = matrix[i][y]
                }
            } else {
                break
            }
        }
        return count
    }

    fun getScenicScoreDownToUp(x: Int, y: Int, matrix: List<List<Int>>): Int {
        val myTreeHeight = matrix[x][y]

        var previouslyHighestTree = myTreeHeight
        var count = 0

        if (x == 0) {
            return 0
        }

        for (i in (0 until x).reversed()) {
            count++
            if (matrix[i][y] >= myTreeHeight) {
                break
            }
            if (matrix[i][y] <= previouslyHighestTree) {
                if (matrix[i][y] == myTreeHeight) {
                    return count
                }
                if (matrix[i][y] > previouslyHighestTree) {
                    previouslyHighestTree = matrix[i][y]
                }
            } else {
                break
            }
        }
        return count
    }

    fun part2(input: List<String>): Int {
        val countedTrees = mutableMapOf<Pair<Int, Int>, Int>()

        //read input as ints
        val matrix = input.map { row ->
            row.toList().map { it.digitToInt() }
        }

        for (x in matrix.indices) {
            for (y in matrix.indices) {
                if (x to y in countedTrees) {
                    continue
                }
                val scoreLeftToRight = getScenicScoreLeftToRight(x, y, matrix)
                if (scoreLeftToRight == 0) {
                    countedTrees += x to y to 0
                    continue
                }
                val scoreRightToLeft = getScenicScoreRightToLeft(x, y, matrix)
                if (scoreRightToLeft == 0) {
                    countedTrees += x to y to 0
                    continue
                }
                val scoreUpToDown = getScenicScoreUpToDown(x, y, matrix)
                if (scoreUpToDown == 0) {
                    countedTrees += x to y to 0
                    continue
                }
                val scoreDownToUp = getScenicScoreDownToUp(x, y, matrix)
                if (scoreDownToUp == 0) {
                    countedTrees += x to y to 0
                    continue
                }
                countedTrees += x to y to scoreLeftToRight * scoreRightToLeft * scoreUpToDown * scoreDownToUp
            }
        }

        return countedTrees.maxOf { it.value }
    }

    val testInput = readInputAsLines("Day08_test")
    check(part1(testInput) == 21)
    check(part2(testInput) == 8)

    val input = readInputAsLines("Day08")
    println("Part 1: " + part1(input))
    println("Part 2: " + part2(input))
}