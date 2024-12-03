fun main() {
    fun part1(input: List<String>): Int = Regex("mul\\((\\d*?),(\\d*?)\\)")
        .findAll(input.joinToString())
        .sumOf { match ->
            match.groupValues[1].toInt() * match.groupValues[2].toInt()
        }

    fun part2(input: List<String>): Int = input.joinToString()
        .let { allInput ->
            // Find all between do and don't
            Regex("(do\\(\\))(.*?)(don't\\(\\))")
                .findAll(allInput)
                .map { result -> result.groupValues[2] }
                .plus(
                    // Add back the first part
                    Regex("(.*?)(don't\\(\\)|do\\(\\))")
                        .find(allInput)
                        .let { result -> result!!.value }
                )
                .toList()
                .let { validated -> part1(validated) }
        }

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
