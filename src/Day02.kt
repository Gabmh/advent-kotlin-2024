private fun List<Int>.checkRangeAfterRemove(index: Int, range: IntRange) = toMutableList().let { mutableList ->
    mutableList.removeAt(index)
    mutableList
        .zipWithNext { prev, next -> next - prev }
        .all { it in range }
}

fun main() {
    fun part1(input: List<String>): Int {
        return input.map { line ->
            val original = line.split(" ").map { it.toInt() }

            val diffs = original.zipWithNext { prev, next ->
                next - prev
            }

            val allIncreasing = diffs.all { it in 1..3 }
            val allDecreasing = diffs.all { it in -3..-1 }

            allIncreasing || allDecreasing
        }.count { it }
    }

    fun part2(input: List<String>): Int {
        return input.map { line ->
            val original = line.split(" ").map { it.toInt() }

            original.zipWithNext { prev, next -> next - prev }
                .let { diffs ->
                    val increasing = diffs.count { it in 1..3 }
                    val decreasing = diffs.count { it in -3..-1 }
                    when {
                        increasing == diffs.size || decreasing == diffs.size -> true
                        increasing > decreasing -> {
                            val check1 = original.checkRangeAfterRemove(diffs.indexOfFirst { it !in 1..3 } + 1, 1..3)
                            val check2 = original.checkRangeAfterRemove(diffs.indexOfFirst { it !in 1..3 }, 1..3)
                            check1 || check2
                        }
                        decreasing > increasing -> {
                            val check1 = original.checkRangeAfterRemove(diffs.indexOfFirst { it !in -3..-1 } + 1, -3..-1)
                            val check2 = original.checkRangeAfterRemove(diffs.indexOfFirst { it !in -3..-1 }, -3..-1)
                            check1 || check2
                        }
                        else -> false
                    }
                }
        }.count { it }
    }

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
