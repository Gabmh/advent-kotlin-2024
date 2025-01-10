import kotlin.math.max

private fun findMax(coefX: Int, coefY: Int, totalX: Int, totalY:Int) = max(totalX / coefX, totalY / coefY)

private fun Pair<Int, Int>.cost() = first * 3 + second

private data class ClawMachine(
    val btnA: Position,
    val btnB: Position,
    val prize: Position,
) {
    val maxA get() = findMax(btnA.x, btnA.y, prize.x, prize.y)
    val maxB get() = findMax(btnB.x, btnB.y, prize.x, prize.y)

    fun solveMod(a: Int, b: Int) =
        prize.x.mod(a * btnA.x + b * btnB.x) == 0
            && prize.y.mod(a * btnA.y + b * btnB.y) == 0

    fun solve(a: Int, b: Int) =
        prize.x == a * btnA.x + b * btnB.x
                && prize.y == a * btnA.y + b * btnB.y
}

fun main() {
    fun part1(input: List<String>) = input
        .joinToString("\n")
        .split("\n\n")
        .sumOf { clawMachineRaw ->
            Regex("Button \\w: X\\+(\\d+), Y\\+(\\d+)|\\w+: X=(\\d+), Y=(\\d+)").findAll(clawMachineRaw).toList().let { result ->
                ClawMachine(
                    btnA = result[0].destructured.let { (x, y) -> Position(x.toInt(), y.toInt()) },
                    btnB = result[1].destructured.let { (x, y) -> Position(x.toInt(), y.toInt()) },
                    prize = result.last().destructured.let { (_, _, x, y) -> Position(x.toInt(), y.toInt()) }
                ).let { clawMachine ->
                    var results = emptyList<Pair<Int, Int>>()
                    IntRange(1, clawMachine.maxA).forEach { a ->
                        IntRange(1, clawMachine.maxB).forEach { b ->
                            if (clawMachine.solve(a, b)) results += Pair(a, b)
                        }
                    }
                    results.println()
                    if (results.isEmpty()) 0 else results.minOf { it.cost() }
                }
            }
        }

    val input = readInput("Day13")
    part1(input).println()
}