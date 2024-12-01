import kotlin.math.abs

private fun List<String>.separateLeftRight() =
    map { it
        .split("   ")
        .run { Pair(first().toInt(), last().toInt()) }
    }
    .unzip()

fun main() {
    fun part1(input: List<String>): Int {
        val (left, right) = input.separateLeftRight()

        return left.sorted()
            .zip(right.sorted())
            .sumOf { (leftValue, rightValue) ->
                abs(leftValue - rightValue)
                // .also { result -> println("$leftValue - $rightValue -> $result") }
            }
    }

    fun part2(input: List<String>): Int {
        val (left, right) = input.separateLeftRight()

        return left.sumOf { leftValue ->
            right
                .count { rightValue -> leftValue == rightValue }
                // .also { println("$leftValue -> $it times") }
                .let { leftValue * it }
        }
    }

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
