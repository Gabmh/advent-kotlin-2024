private const val FREE = -1

private fun String.toBlocks() = flatMapIndexed { index, char ->
    List(char.toString().toInt()) {
        if (index.mod(2) == 0) index.div(2) else FREE
    }
}

private fun List<Int>.compact() = toMutableList().let { memory ->
    while (memory.indexOfLast { it != FREE } > memory.indexOf(FREE)) {
        memory.last { it != FREE }.let { value ->
            memory[memory.indexOf(FREE)] = value
            memory[memory.lastIndexOf(value)] = FREE
        }
    }
    memory.toList()
}

private fun List<Int>.findFreeSpaceIndexes(): List<Pair<Int, Int>> {
    var freeSpaceIndexes = listOf(Pair(indexOf(FREE), indexOf(FREE)))
    mapIndexed { index, value -> index to value }
        .filter { it.second == FREE }
        .zipWithNext { a, b ->
            freeSpaceIndexes = if (b.first == a.first + 1) freeSpaceIndexes - freeSpaceIndexes.last() + Pair(freeSpaceIndexes.last().first, b.first)
            else freeSpaceIndexes + Pair(b.first, b.first)
        }
    return freeSpaceIndexes
}

private fun List<Int>.compactByGroup() = toMutableList().let { memory ->
    var currentGroup = memory.last { it != FREE }
    while (currentGroup > FREE) {
        val groupRange = IntRange(memory.indexOf(currentGroup), memory.lastIndexOf(currentGroup))
        memory.subList(0, memory.lastIndexOf(currentGroup)).findFreeSpaceIndexes().firstOrNull { (it.second - it.first + 1) >= groupRange.count() }?.let { freeSpace ->
            IntRange(freeSpace.first, freeSpace.first + groupRange.count() - 1).forEach { index ->
                memory[index] = currentGroup
            }
            groupRange.forEach { index ->
                memory[index] = FREE
            }
        }
        currentGroup--
        memory.println()
    }
    memory.toList()
}

fun main() {
    fun part1(input: List<String>) = input.first()
        .toBlocks()
        .compact()
        .filter { it != FREE }
        .map { it.toLong() }
        .reduceIndexed { index, acc, value -> acc + index * value }

    fun part2(input:List<String>) = input.first()
        .toBlocks()
        .also { it.println() }
        .compactByGroup()
        .map { it.toLong() }
        .reduceIndexed { index, acc, value -> if (value != FREE.toLong()) acc + index * value else acc }

    val input = readInput("Day09")
    val test = listOf("2333133121414131402")
    part1(test).println()
    part1(input).println()
    part2(test).println()
    part2(input).println()
}