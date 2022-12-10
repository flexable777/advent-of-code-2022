import java.util.*

fun main() {

    data class File(
        val name: String,
        val size: Long,
    )

    data class Folder(
        val name: String,
        val folders: MutableSet<Folder> = mutableSetOf(),
        val files: MutableSet<File> = mutableSetOf(),
    )

    fun Folder.size(): Long {
        var totalSize = folders.sumOf { it.size() }

        totalSize += files.sumOf { file ->
            file.size
        }
        return totalSize
    }

    fun getAllFolders(folder: Folder): MutableSet<Folder> {
        val folders = mutableSetOf<Folder>()
        folders.addAll(folder.folders.map {
            getAllFolders(it)
        }.flatten())

        folders += folder.folders
        return folders
    }

    fun readFileSystem(input: List<String>): Stack<Folder> {
        val folderStack = Stack<Folder>()
        input.forEach { line ->
            when {
                //listing of folder contents
                !line.startsWith("$") -> {
                    val (filename, filesize) = line.split(" ")
                    if (filename != "dir") {
                        folderStack.peek().files += File(name = filesize, size = filename.toLong())
                    }
                }

                //Change directory
                line.startsWith("$ cd") -> {
                    val (_, _, folderName) = line.split(" ")

                    if (folderName == "..") {
                        folderStack.pop()
                    } else {
                        if (folderStack.isEmpty()) {
                            folderStack += Folder(name = folderName)
                        } else {
                            if (!folderStack.peek().folders.any { it.name == folderName }) {
                                with(Folder(name = folderName)) {
                                    folderStack.peek().folders += this
                                    folderStack.push(this)
                                }
                            } else {
                                folderStack.push(folderStack.peek().folders.first { it.name == folderName })
                            }
                        }
                    }
                }
            }
        }
        return folderStack
    }

    fun part1(input: List<String>): Long =
        getAllFolders(readFileSystem(input).first()).filter { folder ->
            folder.size() <= 100_000
        }.sumOf { f ->
            f.size()
        }

    fun part2(input: List<String>): Long {
        val folderStack = readFileSystem(input)

        val free = 70000000 - folderStack.first().size()

        val missing = 30000000 - free

        val sortedFoldersBySize = getAllFolders(folderStack.first()).filter { folder ->
            folder.size() >= missing
        }.sortedBy { f ->
            f.size()
        }

        return sortedFoldersBySize.first().size()
    }

    val testInput = readInputAsLines("Day07_test")
    check(part1(testInput) == 95437L)

    check(part2(testInput) == 24933642L)

    val input = readInputAsLines("Day07")
    println("Part 1: " + part1(input))
    println("Part 2: " + part2(input))
}