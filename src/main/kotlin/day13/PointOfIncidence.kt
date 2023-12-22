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
        val reflections = patterns.map { Pair(findVerticalReflection(it), findHorizontalReflection(it)) }
        return reflections.sumOf{ (it.first ?: 0) + 100 * (it.second ?: 0) }
    }

    override fun secondStar(): Int {
        val reflections = patterns.asSequence().map { pattern ->
            val originalVerticalReflection = findVerticalReflection(pattern)
            val originalHorizontalReflection = findHorizontalReflection(pattern)
            val height = pattern.maxOf { it.y }
            val width = pattern.maxOf { it.x }
            val smudgedPatterns: Sequence<Pattern> = (0..height).asSequence().flatMap { y ->
                (0..width).asSequence().map { x ->
                    if (pattern.contains(Coordinate(x, y))) pattern.filterNot { it == Coordinate(x, y) }
                    else pattern + Coordinate(x, y)
                }
            }
            val smudgedReflections = smudgedPatterns.map {
                val newVerticalReflection = findVerticalReflection(it)
                val newHorizontalReflection = findHorizontalReflection(it)
                val vertical = Pair(originalVerticalReflection, newVerticalReflection)
                val horizontal = Pair(originalHorizontalReflection, newHorizontalReflection)
                Pair(vertical, horizontal)
            }
            val differentVerticalReflections = smudgedReflections.map{it.first}.filter {
                it.first != it.second && it.second != null
            }
            val differentHorizontalReflections = smudgedReflections.map{it.second}.filter {
                it.first != it.second && it.second != null
            }
            Pair(differentVerticalReflections.firstOrNull()?.second, differentHorizontalReflections.firstOrNull()?.second)
//            Pair(differentVerticalReflections, differentHorizontalReflections)
        }

        return reflections.sumOf{ (it.first ?: 0) + 100 * (it.second ?: 0) }
//        return reflections.map{diff ->
//            val diffsVert: Sequence<Pair<Int?, Int?>> = diff.first
//            val diffsHoriz: Sequence<Pair<Int?, Int?>> = diff.second
//            diffsVert.map { vert -> vert.second ?: 0 }.sum() + 100 * diffsHoriz.map { horiz -> horiz.second ?: 0 }.sum()
//        }.sum()
    }

    private fun findHorizontalReflection(pattern: Pattern): Int? {
        val height = pattern.maxOf { it.y }
        val reflection = (1..<height).mapNotNull { y ->
            val distanceToEdge = min(y - 1, height - y - 1)
            if ((0..distanceToEdge).all { d ->
                    val left = pattern.filter { it.y == y - d }
                    val right = pattern.filter { it.y == y + 1 + d }
                    left.all { l -> right.any { r -> l.x == r.x } }
                            && right.all { r -> left.any { l -> l.x == r.x } }
                }) y else null
        }
        return reflection.singleOrNull()
    }

    private fun findVerticalReflection(pattern: Pattern): Int? {
        val width = pattern.maxOf { it.x }
        val reflection = (1..<width).mapNotNull { x ->
            val distanceToEdge = min(x - 1, width - x - 1)
            if ((0..distanceToEdge).all { d ->
                    val left = pattern.filter { it.x == x - d }
                    val right = pattern.filter { it.x == x + 1 + d }
                    left.all { l -> right.any { r -> l.y == r.y } }
                            && right.all { r -> left.any { l -> l.y == r.y } }
                }) x else null
        }
        return reflection.singleOrNull()
    }
}

fun main() {
    println(PointOfIncidence().firstStar())
    println(PointOfIncidence().secondStar())
}