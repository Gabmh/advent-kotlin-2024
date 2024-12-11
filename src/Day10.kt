fun List<String>.getValueAt(position: Position) = get(position.y)[position.x].toString().toInt()

fun List<String>.findNextPositions(position: Position, value: Int): Pair<Int, MutableSet<Position>> {
    val result = mutableSetOf<Position>()
    val newValue = value + 1
    Position(position.x - 1, position.y).let { left -> if (left.x in 0..first().lastIndex && getValueAt(left) == newValue) result += left }
    Position(position.x + 1, position.y).let { right -> if (right.x in 0..first().lastIndex && getValueAt(right) == newValue) result += right }
    Position(position.x, position.y - 1).let { up -> if (up.y in 0..lastIndex && getValueAt(up) == newValue) result += up }
    Position(position.x, position.y + 1).let { down -> if (down.y in 0..lastIndex && getValueAt(down) == newValue) result += down }
    return Pair(newValue, result)
}

fun main() {
    fun part1(input: List<String>) = input.mapIndexed { y, line ->
        line.mapIndexed { x, char ->
            if (char.isDigit() && char.toString().toInt() == 0) {
                var currentValue = 0
                var currentPositions = setOf(Position(x, y))
                while (currentValue < 9) {
                    val positions = currentPositions.toSet()
                    val value = currentValue
                    currentPositions = emptySet()
                    positions.forEach { position ->
                        input.findNextPositions(position, value)
                            .let { (newValue, newPositions) ->
                                if (newPositions.isNotEmpty()) {
                                    currentValue = newValue
                                    currentPositions += newPositions
                                }
                            }
                    }
                    // No way to 9
                    if (currentValue == value) {
                        println("No way to 9 !")
                        currentValue = 9
                        currentPositions = emptySet()
                    }
                }
                currentPositions.size
            } else 0
        }.sum()
    }.sum()

    fun part2(input: List<String>) = input.mapIndexed { y, line ->
        line.mapIndexed { x, char ->
            if (char.isDigit() && char.toString().toInt() == 0) {
                var currentValue = 0
                var currentPositions = listOf(Position(x, y))
                while (currentValue < 9) {
                    val positions = currentPositions.toList()
                    val value = currentValue
                    currentPositions = emptyList()
                    positions.forEach { position ->
                        input.findNextPositions(position, value)
                            .let { (newValue, newPositions) ->
                                if (newPositions.isNotEmpty()) {
                                    currentValue = newValue
                                    currentPositions += newPositions
                                }
                            }
                    }
                    // No way to 9
                    if (currentValue == value) {
                        println("No way to 9 !")
                        currentValue = 9
                        currentPositions = emptyList()
                    }
                    currentPositions.println()
                }
                currentPositions.size
            } else 0
        }.sum()
    }.sum()


    val input = readInput("Day10")
    part1(input).println()
    part2(input).println()
}