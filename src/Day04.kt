import Direction.*

enum class Direction {
    TOP,
    TOP_RIGHT,
    TOP_LEFT,
    BOTTOM,
    BOTTOM_RIGHT,
    BOTTOM_LEFT,
    RIGHT,
    LEFT,
}

fun main() {

    val xmas = "XMAS"

    val topDirections = listOf(TOP, TOP_RIGHT, TOP_LEFT)
    val bottomDirection = listOf(BOTTOM, BOTTOM_RIGHT, BOTTOM_LEFT)
    val leftDirection = listOf(LEFT, TOP_LEFT, BOTTOM_LEFT)
    val rightDirection = listOf(RIGHT, TOP_RIGHT, BOTTOM_RIGHT)

    fun part1(input: List<String>): Int {
        return input.mapIndexed { y, line ->
            line.mapIndexed { x, _ ->
                var directions = Direction.entries.toList()
                if (x < 3) directions = directions.filter { it !in leftDirection }
                if (y < 3) directions = directions.filter { it !in topDirections }
                if (x > line.lastIndex - 3) directions = directions.filter { it !in rightDirection }
                if (y > input.lastIndex - 3) directions = directions.filter { it !in bottomDirection }
                directions.map { direction ->
                    when(direction) {
                        TOP -> listOf(input[x][y], input[x][y-1], input[x][y-2], input[x][y-3]).joinToString("") == xmas
                        TOP_RIGHT -> listOf(input[x][y], input[x+1][y-1], input[x+2][y-2], input[x+3][y-3]).joinToString("") == xmas
                        TOP_LEFT -> listOf(input[x][y], input[x-1][y-1], input[x-2][y-2], input[x-3][y-3]).joinToString("") == xmas
                        BOTTOM -> listOf(input[x][y], input[x][y+1], input[x][y+2], input[x][y+3]).joinToString("") == xmas
                        BOTTOM_RIGHT -> listOf(input[x][y], input[x+1][y+1], input[x+2][y+2], input[x+3][y+3]).joinToString("") == xmas
                        BOTTOM_LEFT -> listOf(input[x][y], input[x-1][y+1], input[x-2][y+2], input[x-3][y+3]).joinToString("") == xmas
                        RIGHT -> listOf(input[x][y], input[x+1][y], input[x+2][y], input[x+3][y]).joinToString("") == xmas
                        LEFT -> listOf(input[x][y], input[x-1][y], input[x-2][y], input[x-3][y]).joinToString("") == xmas
                    }
                }.count { it }
            }
        }.flatten().sum()
    }

    fun part2(input: List<String>): Int {
        val mas = setOf("MAS", "SAM")
        return input.mapIndexed { y, line ->
            line.mapIndexed { x, char ->
                if (char != 'A' || x < 1 || y < 1 || x > line.lastIndex - 1 || y > input.lastIndex - 1) false
                else listOf(input[y-1][x-1], char, input[y+1][x+1]).joinToString("") in mas
                        && listOf(input[y-1][x+1], char, input[y+1][x-1]).joinToString("") in mas
            }.count { it }
        }.sum()
    }

    val test = listOf(".M.S......", "..A..MSMS.", ".M.S.MAA..", "..A.ASMSM.", ".M.S.M....", "..........", "S.S.S.S.S.", ".A.A.A.A..", "M.M.M.M.M.", "..........")

    val input = readInput("Day04")
    part1(input).println()
    check(part2(test) == 9)
    part2(input).println()
}
