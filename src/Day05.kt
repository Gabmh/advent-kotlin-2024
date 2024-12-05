fun List<String>.parse() = indexOf("").let { index ->
    Pair(subList(0, index).map { it.split("|") }, subList(index + 1, size).map { it.split(",") })
}
fun main() {
    fun part1(input: List<String>): Int {
        val (rules, lines) = input.parse()
        return lines.sumOf { line ->
            line.mapIndexed { index, num ->
                line.minus(num).map { otherNum ->
                    val comparing = listOf(num, otherNum)
                    val rule = rules.find { rule -> rule[0] in comparing  && rule[1] in comparing }
                    if (rule?.first() == num) 1 else 0
                }.sum() == line.lastIndex - index
            }.let { result -> if (result.all { it }) line[line.lastIndex/2].toInt() else 0 }
        }
    }

    fun part2(input: List<String>): Int {
        val (rules, lines) = input.parse()
        return lines.sumOf { line ->
            line.sortedByDescending { num ->
                line.minus(num).map { otherNum ->
                    val comparing = listOf(num, otherNum)
                    val rule = rules.find { rule -> rule[0] in comparing  && rule[1] in comparing }
                    if (rule?.first() == num) 1 else 0
                }.sum()
            }.let { if (it != line) it[it.lastIndex/2].toInt() else 0 }
        }
    }

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}
