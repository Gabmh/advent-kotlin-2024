import kotlin.math.abs

fun antinodes(a: Position, b: Position): List<Position> {
    val xDiff = abs(a.x - b.x)
    val yDiff = abs(a.y - b.y)
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

fun List<Position>.display(input: List<String>) = input.toMutableList().let { newInput ->
    forEach { antinode ->
        newInput[antinode.y] = StringBuilder(newInput[antinode.y]).let { stringBuilder ->
            stringBuilder.setCharAt(antinode.x, '#')
            stringBuilder.toString()
        }
    }
    writeOutput("output", newInput)
}

fun main () {
    fun part1(input: List<String>): Int {
        val signalMap = mutableMapOf<Char, List<Position>>()
        input.mapIndexed { y, line ->
            line.mapIndexed { x, char ->
                if (char != '.') signalMap[char] = Position(x, y).let { signalMap[char]?.plus(it) ?: listOf(it) }
            }
        }
        return signalMap.values
            .flatMap { positions ->
                positions.flatMap { a ->
                    (positions - a).flatMap { b ->
                        antinodes(a, b)
                    }
                }
            }
            .filter { antinode ->
                antinode.x in IntRange(0, input.first().lastIndex) && antinode.y in IntRange(0, input.lastIndex) && input[antinode.y][antinode.x] == '.'
            }
            .also { antinodes -> antinodes.display(input) }
            .size
    }

    fun part2(input: List<String>) = input.size

    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()
}