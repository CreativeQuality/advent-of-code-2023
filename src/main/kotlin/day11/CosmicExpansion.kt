package day11

import base.KtPuzzle
import kotlin.math.abs

object CosmicExpansion : KtPuzzle("day11/input.txt") {

    override fun firstStar(): Long {
        return expandAndCalculate(1)
    }

    override fun secondStar(): Long {
        return expandAndCalculate(999999L)
    }

    internal fun expandAndCalculate(expansion: Long): Long {
        val galaxies = sequenceInput().toList().flatMapIndexed { y, line ->
            line.mapIndexedNotNull { x, char -> if (char == '#') Pair(x.toLong(), y.toLong()) else null }
        }
        val width = galaxies.maxOf { it.first }
        val height = galaxies.maxOf { it.second }
        val emptyColumns = (0..width).filter { x -> galaxies.none { it.first == x } }
        val emptyRows = (0..height).filter { y -> galaxies.none { it.second == y } }
        val galaxiesExpandedWidth = emptyColumns.foldIndexed(galaxies) { index, acc, empty ->
            acc.map { (x, y) -> if (x > empty + (index * expansion)) Pair(x + expansion, y) else Pair(x, y) }
        }
        val galaxiesExpanded = emptyRows.foldIndexed(galaxiesExpandedWidth) { index, acc, empty ->
            acc.map { (x, y) -> if (y > empty + (index * expansion)) Pair(x, y + expansion) else Pair(x, y) }
        }
        return galaxiesExpanded.flatMap { a -> galaxiesExpanded.map { b -> taxi(a, b) } }.sum() / 2
    }

    private fun taxi(a: Pair<Long, Long>, b: Pair<Long, Long>) = abs(a.first - b.first) + abs(a.second - b.second)
}

fun main() {
    println(CosmicExpansion.firstStar())
    println(CosmicExpansion.secondStar())
}