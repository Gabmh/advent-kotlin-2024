private fun String.cutInHalf(): List<Long> = listOf(
    substring(0, length.div(2)).toLong(),
    substring(length.div(2), length).toLong(),
)

private fun blink(stones: List<Long>): List<Long> {
    val newStones = mutableListOf<Long>()
    stones.forEach { stone ->
        when {
            stone.toInt() == 0 -> newStones.add(1)
            stone.toString().length.mod(2) == 0 -> newStones += stone.toString().cutInHalf()
            else -> newStones.add(stone*2024)
        }
    }
    return newStones.toList()
}

fun main() {
    fun part1(input: List<Long>): Int {
        var arrangements = input
        IntRange(1, 25).forEach { _ ->
            arrangements = blink(arrangements)
                .also { it.println() }
        }
        return arrangements.size
    }

    fun part2(input: List<Long>): Int = input.sumOf { stone ->
        var i = 0
        var newStones = listOf(stone)
        while(i < 75) {
            i++
            newStones = blink(newStones)
        }
        newStones.size
    }

    val input = readInput("Day11")
    val initialList = input.first().split(" ").map { it.toLong() }
    part1(initialList).println()
    part2(initialList).println() // Heap size error
}