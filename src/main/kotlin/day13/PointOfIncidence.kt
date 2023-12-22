package day13

import base.KtPuzzle
import day03.Coordinate
import java.lang.Integer.min

typealias Pattern = List<Coordinate>

class PointOfIncidence(resource: String = "day13/input.txt") : KtPuzzle(resource) {

    val patterns: List<Pattern>

    init {
        val patternsAsStrings = mutableListOf<MutableList<String>>()
        patternsAsStrings.add(mutableListOf())
        sequenceInput().forEach {
            if (it.isEmpty()) patternsAsStrings.add(mutableListOf())
            else patternsAsStrings.last().add(it)
        }
        patterns = patternsAsStrings.map { strings ->
            strings.mapIndexed { y, line ->
                line.mapIndexed { x, char -> if (char == '#') Coordinate(x + 1, y + 1) else null }
            }.flatten().filterNotNull()
        }
    }

    override fun firstStar(): Int {
        val reflections = patterns.map { Pair(findVerticalReflections(it), findHorizontalReflections(it)) }
        return reflections.sumOf{ (it.first.singleOrNull() ?: 0) + 100 * (it.second.singleOrNull() ?: 0) }
    }

    override fun secondStar(): Int {
        val reflections: Sequence<Pair<Int?, Int?>> = patterns.asSequence().map { pattern ->
            val originalVerticalReflection = findVerticalReflections(pattern).singleOrNull()
            val originalHorizontalReflection = findHorizontalReflections(pattern).singleOrNull()
            val height = pattern.maxOf { it.y }
            val width = pattern.maxOf { it.x }
            val smudgedPatterns: Sequence<Pattern> = (1..height).asSequence().flatMap { y ->
                (1..width).asSequence().map { x ->
                    if (pattern.contains(Coordinate(x, y))) pattern - Coordinate(x, y)
                    else pattern + Coordinate(x, y)
                }
            }
            val newVerticalReflection = smudgedPatterns.flatMap { p ->
                findVerticalReflections(p)
            }.filter { it != originalVerticalReflection }.distinct().singleOrNull()
            val newHorizontalReflection = smudgedPatterns.flatMap { p ->
                findHorizontalReflections(p)
            }.filter { it != originalHorizontalReflection }.distinct().singleOrNull()
            Pair(newVerticalReflection, newHorizontalReflection)
        }

        return reflections.sumOf{ (it.first ?: 0) + 100 * (it.second ?: 0) }
    }

    private fun findHorizontalReflections(pattern: Pattern): List<Int> {
        val height = pattern.maxOf { it.y }
        val reflections = (1..<height).mapNotNull { y ->
            val distanceToEdge = min(y - 1, height - y - 1)
            if ((0..distanceToEdge).all { d ->
                    val top = pattern.filter { it.y == y - d }
                    val bottom = pattern.filter { it.y == y + 1 + d }
                    top.all { l -> bottom.any { r -> l.x == r.x } }
                            && bottom.all { r -> top.any { l -> l.x == r.x } }
                }) y else null
        }
        return reflections
    }

    private fun findVerticalReflections(pattern: Pattern): List<Int> {
        val width = pattern.maxOf { it.x }
        val reflections = (1..<width).mapNotNull { x ->
            val distanceToEdge = min(x - 1, width - x - 1)
            if ((0..distanceToEdge).all { d ->
                    val left = pattern.filter { it.x == x - d }
                    val right = pattern.filter { it.x == x + 1 + d }
                    left.all { l -> right.any { r -> l.y == r.y } }
                            && right.all { r -> left.any { l -> l.y == r.y } }
                }) x else null
        }
        return reflections
    }
}

fun main() {
    println(PointOfIncidence().firstStar())
    println(PointOfIncidence().secondStar())
}