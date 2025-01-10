fun main() {
    fun part1(input: List<String>, width: Int, height: Int, time: Int) = input.flatMap { line ->
        Regex("p=(\\d+,\\d+) v=(-?\\d+,-?\\d+)").findAll(line).map { it.destructured.let { (positionRaw, velocityRaw) ->
            val position = positionRaw.split(",").let { (x, y) -> Position(x.toInt(), y.toInt()) }
            val velocity = velocityRaw.split(",").let { (x, y) -> Position(x.toInt(), y.toInt()) }
            Position(
                x = (position.x + velocity.x * time).mod(width),
                y = (position.y + velocity.y * time).mod(height),
            )
        }}
    }.also { result ->
        IntRange(0, height - 1).map { y ->
            IntRange(0, width - 1).joinToString("") { x ->
                result.filter { position -> position == Position(x, y) }.let { positions ->
                    if (positions.isEmpty()) "."
                    else positions.size.toString()
                }
            }
        }.let { writeOutput("output", it) }
    }.let { result ->
        result.filter { it.x < (width-1)/2 && it.y < (height-1)/2 }.size
            .times(result.filter { it.x > (width-1)/2 && it.y < (height-1)/2 }.size)
            .times(result.filter { it.x < (width-1)/2 && it.y > (height-1)/2 }.size)
            .times(result.filter { it.x > (width-1)/2 && it.y > (height-1)/2 }.size)
    }

    val input = readInput("Day14")
    part1(input, 101, 103, 100).println()
}