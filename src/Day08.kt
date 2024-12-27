import kotlin.math.abs

private fun Position.inBordersOf(input: List<String>) = x in IntRange(0, input.first().lastIndex) && y in IntRange(0, input.lastIndex)

private fun generateSignalMap(input: List<String>): Map<Char, List<Position>> {
    val signalMap = mutableMapOf<Char, List<Position>>()
    input.forEachIndexed { y, line ->
        line.forEachIndexed { x, char ->
            if (char != '.') signalMap[char] = Position(x, y).let { signalMap[char]?.plus(it) ?: listOf(it) }
        }
    }
    return signalMap
}

private fun generateAntinodes(a: Position, b: Position, multiplier: Int = 1): List<Position> {
    val xDiff = abs(a.x - b.x) * multiplier
    val yDiff = abs(a.y - b.y) * multiplier
    return listOf(
        Position(
            x = if (a.x > b.x) a.x + xDiff else a.x - xDiff,
            y = if (a.y > b.y) a.y + yDiff else a.y - yDiff,
        ),
        Position(
            x = if (a.x > b.x) b.x + xDiff else b.x - xDiff,
            y = if (a.y > b.y) b.y + yDiff else b.y - yDiff,
        ),
    )
}

private fun generateAllAntinodes(a: Position, b: Position, input: List<String>): List<Position> {
    var multiplier = 1
    var antinodes = emptyList<Position>()
    while (generateAntinodes(a, b, multiplier).any { antinode -> antinode.inBordersOf(input) }) {
        antinodes += generateAntinodes(a, b, multiplier)
        multiplier++
    }
    return antinodes
}

private fun List<Position>.displayAntinodes(input: List<String>) = input.toMutableList().let { newInput ->
    forEach { antinode ->
        newInput[antinode.y] = StringBuilder(newInput[antinode.y]).let { stringBuilder ->
            stringBuilder.setCharAt(antinode.x, '#')
            stringBuilder.toString()
        }
    }
    writeOutput("output", newInput)
}

fun main () {
    fun part1(input: List<String>): Int = generateSignalMap(input).entries
        .flatMap { (char, positions) ->
            positions.flatMap { a ->
                (positions - a).flatMap { b ->
                    generateAntinodes(a, b)
                        .filter { antinode ->
                            antinode.inBordersOf(input) && input[antinode.y][antinode.x] != char
                        }
                }
            }
        }
        .distinct()
        .also { antinodes -> antinodes.displayAntinodes(input) }
        .size

    fun part2(input: List<String>) = generateSignalMap(input).values
        .flatMap { positions ->
            positions.flatMap { a ->
                (positions - a).flatMap { b ->
                    generateAllAntinodes(a, b, input)
                        .filter { antinode ->
                            antinode.inBordersOf(input)
                        }
                }
            }
        }
        .distinct()
        .also { antinodes -> antinodes.displayAntinodes(input) }
        .size

    val test = readInput("test")
    val input = readInput("Day08")
    part1(test).println()
    part1(input).println()
    part2(test).println()
    part2(input).println()
}