fun rotateMatrix(matrix: Array<CharArray>) {
    val n = matrix.size

    // Transpose the matrix
    for (i in 0 until n) {
        for (j in i until n) {
            val temp = matrix[i][j]
            matrix[i][j] = matrix[j][i]
            matrix[j][i] = temp
        }
    }

    // Reverse each row
    for (i in 0 until n) {
        for (j in 0 until n / 2) {
            val temp = matrix[i][j]
            matrix[i][j] = matrix[i][n - j - 1]
            matrix[i][n - j - 1] = temp
        }
    }
}

private fun List<String>.convertToValues(): List<Int> {
    var values = List(5) { -1 }
    forEach { line ->
        values = values.mapIndexed { index, int ->
            val value = if (line[index] == '#') 1 else 0
            int + value
        }
    }
    return values
}

fun main() {
    fun part1(input: List<String>) = input.joinToString("\n").split("\n\n").let { blocks ->
        val locks = blocks.filter { it.first() == '#' }
        val keys = blocks.filter { it.first() == '.' }
        check(locks.size + keys.size == blocks.size) { "not possible !" }

        val lockValues = locks.map { lock -> lock.split("\n").convertToValues() }
        val keysValues = keys.map { lock -> lock.split("\n").convertToValues() }
        lockValues.map { lockValue ->
            keysValues.map { keyValue ->
                lockValue
                    .mapIndexed { index, pin -> pin + keyValue[index] }
                    .all { it <= 5 }
                    .also { println("Lock $lockValue and key $keyValue: ${ if (it) "fit" else "overlap" }") }
                    .let { if (it) 1 else 0 }
            }.sum()
        }.sum()
    }

    val test = readInput("test")
    val input = readInput("Day25")
    part1(input).println()
}