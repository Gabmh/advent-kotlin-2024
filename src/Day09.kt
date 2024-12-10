fun main() {
    fun part1(input: List<String>) = input.first().flatMapIndexed { index, char ->
        List(char.toString().toInt()) { if (index.mod(2) == 0) index.div(2).toString() else "." }
    }.joinToString("").let { string ->
        val start = System.currentTimeMillis()
        val memory = StringBuilder(string)
        while (memory.indexOfLast { it.isDigit() } > memory.indexOf('.')) {
            memory.last { it.isDigit() }.let { digit ->
                memory.setCharAt(memory.indexOf('.'), digit)
                memory.setCharAt(memory.lastIndexOf(digit), '.')
                memory.println()
            }
        }
        System.currentTimeMillis().minus(start).div(1000).println()
        memory.filter { it.isDigit() }.map { it.toString().toInt() }.reduceIndexed { index, acc, value -> acc + index * value }
    }


    val input = readInput("Day09")
    val test = listOf("2333133121414131402")
    part1(input).println()
}