private fun List<Long>.testSolutionBin(testValue: Long) = IntRange(0, "1".repeat(lastIndex).toInt(2))
    .map { it.toString(2).padStart(lastIndex, '0') }
    .any { binary ->
        reduceIndexed { index, acc, long ->
            when(binary[index - 1]) {
                '0' -> acc + long
                '1' -> acc * long
                else -> throw Exception("not possible")
            }
        } == testValue
    }

private fun List<Long>.testSolutionTer(testValue: Long) = LongRange(0, "2".repeat(lastIndex).toLong(3))
    .map { it.toString(3).padStart(lastIndex, '0') }
    .any { ternary ->
        reduceIndexed { index, acc, long ->
            when(ternary[index - 1]) {
                '0' -> acc + long
                '1' -> acc * long
                '2' -> (acc.toString() + long.toString()).toLong()
                else -> throw Exception("not possible")
            }
        } == testValue
    }

fun main() {
    fun part1(input: List<String>) = input.sumOf { line ->
        line.split(": ").let { (testValueStr, valuesStr) ->
            val testValue = testValueStr.toLong()
            val values = valuesStr.split(" ").map { it.toLong() }

            if (values.testSolutionBin(testValue)) testValue else 0
        }
    }

    fun part2(input: List<String>) = input.sumOf { line ->
        line.split(": ").let { (testValueStr, valuesStr) ->
            val testValue = testValueStr.toLong()
            val values = valuesStr.split(" ").map { it.toLong() }

            if (values.testSolutionTer(testValue)) testValue else 0
        }
    }

    val test = readInput("test")
    val input = readInput("Day07")
    part1(test).println()
    part1(input).println()
    part2(test).println()
//    not optimized !
//    part2(input).println()
}
