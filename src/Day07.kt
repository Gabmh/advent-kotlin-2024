import kotlin.math.abs

fun List<Int>.solverTimes(testValue: Int) = reduce { acc, value ->
    if (acc * value > testValue) acc + value
    else acc * value
}

fun List<Int>.solver(testValue: Int) = reduceIndexed { index, acc, value ->
    val sum = subList(index, size).sum()
    val mult = subList(index, size).reduce { tot, i -> tot * i  }
    mapOf(
        Pair(abs(acc + sum - testValue), "+"),
        Pair(abs(acc * sum - testValue), "*"),
        Pair(abs(acc * mult - testValue), "*"),
        Pair(abs(acc + mult - testValue), "+")
    )
        .also { it.println() }
        .minBy { it.key }
        .also { println("$acc${it.value}$value") }
        .let {
            when(it.value) {
                "+" -> acc + value
                "*" -> acc * value
                else -> throw Exception("not possible")
            }
        }
        .also { it.println() }
}

fun List<Int>.solverBis(testValue: Int) = reduceIndexed { index, acc, value ->
    subList(index, size).reduce { mult, i -> mult*i }.let { allmult ->
        println("$acc * $allmult ? $testValue")
        if (acc*allmult > testValue) acc + value
        else acc * value
    }
    if (acc * subList(index, size).reduce { it, i -> it*i } > testValue) acc + value
    else acc * value
}

fun main() {
    fun part1(input: List<String>): Int = input.sumOf { line ->
        val (testValueStr, valuesStr) = line.split(": ")
        val testValue = testValueStr.toInt()
        val values = valuesStr.split(" ").map { it.toInt() }

        testValue
    }

    fun part2(input: List<String>): Int = input.size

    val input = readInput("test")
    part1(input).println()
    part2(input).println()
}
