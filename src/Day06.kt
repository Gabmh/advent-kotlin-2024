import kotlin.math.max
import kotlin.math.min

data class Position(val x: Int, val y: Int)
enum class Orientation(val key: Char) {
    Up('^'),
    Down('v'),
    Right('>'),
    Left('<'),
}

fun Position.next(orientation: Orientation) = when(orientation) {
    Orientation.Up -> Position(x, y-1)
    Orientation.Down -> Position(x, y+1)
    Orientation.Right -> Position(x+1, y)
    Orientation.Left -> Position(x-1, y)
}

fun Orientation.rotate() = when(this) {
    Orientation.Up -> Orientation.Right
    Orientation.Down -> Orientation.Left
    Orientation.Right -> Orientation.Down
    Orientation.Left -> Orientation.Up
}

fun List<String>.checkBetween(a: Position, b: Position): Boolean = when {
    a.x == b.x -> !(min(a.y, b.y)..max(a.y, b.y)).any { get(it)[a.x] == '#' }
    a.y == b.y -> !get(a.y).substring(min(a.x, b.x)..max(a.x, b.x)).contains("#")
    else -> throw Exception("not possible")
}

fun main() {
    fun part1(input: List<String>): Int {
        val positions = mutableListOf<Position>()
        var currentOrientation = Orientation.Up
        var currentPosition = input.find { it.contains("^") }!!.let { Position(it.indexOf("^"), input.indexOf(it)) }
        while (currentPosition.x in (1..<input.first().lastIndex) && currentPosition.y in (1..<input.lastIndex)) {
            if (currentPosition !in positions) positions += currentPosition
            while (currentPosition.next(currentOrientation).let { input[it.y][it.x] } == '#') currentOrientation = currentOrientation.rotate()
            currentPosition = currentPosition.next(currentOrientation)
        }
        positions += currentPosition
        return positions.size
    }

    fun part2(input: List<String>): Int {
        val loops = mutableListOf<Position>()
        val positions = mutableMapOf<Position, Orientation>()
        var currentOrientation = Orientation.Up
        var currentPosition = input.find { it.contains("^") }!!.let { Position(it.indexOf("^"), input.indexOf(it)) }
        while (currentPosition.x in (1..<input.first().lastIndex) && currentPosition.y in (1..<input.lastIndex)) {
            // test if at any moment we can take back a way on right line
            val possibleLoops = when(currentOrientation.rotate()) {
                Orientation.Up -> positions.filter { (position, orientation) -> position.x == currentPosition.x && position.y < currentPosition.y && orientation == currentOrientation.rotate() }
                Orientation.Down -> positions.filter { (position, orientation) -> position.x == currentPosition.x && position.y > currentPosition.y && orientation == currentOrientation.rotate() }
                Orientation.Right -> positions.filter { (position, orientation) -> position.y == currentPosition.y && position.x > currentPosition.x && orientation == currentOrientation.rotate() }
                Orientation.Left -> positions.filter { (position, orientation) -> position.y == currentPosition.y && position.x < currentPosition.x && orientation == currentOrientation.rotate() }
            }
            if (possibleLoops.any { input.checkBetween(currentPosition, it.key)}) loops += currentPosition

            // continue like part 1
            if (currentPosition !in positions) positions[currentPosition] = currentOrientation
            while (currentPosition.next(currentOrientation).let { input[it.y][it.x] } == '#') currentOrientation = currentOrientation.rotate()
            currentPosition = currentPosition.next(currentOrientation)
        }
        positions[currentPosition] = currentOrientation
        loops.println()
        return loops.size
    }

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}
