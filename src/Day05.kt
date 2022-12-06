import java.util.*

fun main() {

    fun loadStack(stackNumber: Int, crate: Char, arrayOfStacks: Array<Stack<Char>>) {
        arrayOfStacks[stackNumber].add(0, crate)
    }

    fun moveFromStack(stackNumberFrom: Int, stackNumberTo: Int, arrayOfStacks: Array<Stack<Char>>) {
        arrayOfStacks[stackNumberTo].push(arrayOfStacks[stackNumberFrom].pop())
    }

    fun moveFromStack(stackNumberFrom: Int, stackNumberTo: Int, amount: Int, arrayOfStacks: Array<Stack<Char>>) {
        //is there a pop(), but for multiple elements?

        val stackFrom = arrayOfStacks[stackNumberFrom]
        val cratesToMove = stackFrom.takeLast(amount)
        val newStackFrom = Stack<Char>()
        newStackFrom.addAll(arrayOfStacks[stackNumberFrom].subList(0, stackFrom.size - amount))
        arrayOfStacks[stackNumberFrom] = newStackFrom
        arrayOfStacks[stackNumberTo].addAll(cratesToMove)
    }

    fun readInputAndFillStacks(
        input: String,
        arrayOfStacks: Array<Stack<Char>>
    ) {
        input.lines().forEach { line ->
            line.chunked(4).forEachIndexed { index, s ->
                if (s.isNotBlank() && s[1].isLetter()) {
                    loadStack(
                        stackNumber = index,
                        crate = s[1],
                        arrayOfStacks = arrayOfStacks
                    )
                }
            }
        }
    }

    fun part1(input: String): String {
        val (stacksInput, instructionsInput) = input.split("\n\n")

        val arrayOfStacks = Array(9) { Stack<Char>() }

        readInputAndFillStacks(stacksInput, arrayOfStacks)

        //perform instructions
        instructionsInput.lines().forEach { instruction ->
            val c = instruction
                .replace("move ", "")
                .replace("from ", "")
                .replace("to ", "")

            val (move, from, to) = c.split(" ").map { it.toInt() }

            repeat(move) {
                moveFromStack(
                    stackNumberFrom = from - 1,
                    stackNumberTo = to - 1,
                    arrayOfStacks = arrayOfStacks
                )
            }
        }

        return arrayOfStacks.joinToString("") { if (it.isNotEmpty()) it.peek().toString() else "" }
    }

    fun part2(input: String): String {
        val (stacksInput, instructionsInput) = input.split("\n\n")

        val arrayOfStacks = Array(9) { Stack<Char>() }

        readInputAndFillStacks(stacksInput, arrayOfStacks)

        //perform instructions
        instructionsInput.lines().forEach { instruction ->
            val c = instruction
                .replace("move ", "")
                .replace("from ", "")
                .replace("to ", "")

            val (move, from, to) = c.split(" ").map { it.toInt() }

            moveFromStack(
                stackNumberFrom = from - 1,
                stackNumberTo = to - 1,
                amount = move,
                arrayOfStacks = arrayOfStacks
            )
        }

        return arrayOfStacks.joinToString("") { if (it.isNotEmpty()) it.peek().toString() else "" }
    }

    val testInput = readInput("Day05_test")

    check(part1(testInput) == "CMZ")
    check(part2(testInput) == "MCD")

    val input = readInput("Day05")

    println(part1(input))
    println(part2(input))
}