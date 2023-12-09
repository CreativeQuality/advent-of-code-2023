package day08

import base.KtPuzzle
import kotlin.math.max

typealias InstructionSeq = Sequence<Pair<Char, Int>>

class HauntedWasteland(resourceLocation: String = "day08/input.txt") : KtPuzzle(resourceLocation) {

    private val instructionList = sequenceInput().first().toList()
    private val instructions: InstructionSeq =
        generateSequence(Pair(instructionList[0], 0)) {
            Pair(instructionList[(it.second + 1) % instructionList.size], it.second + 1)
        }
    private val nodes = sequenceInput().drop(2).associate {
        val (origin, left, right) = """(...) = \((...), (...)\)""".toRegex().find(it)?.destructured
            ?: throw RuntimeException("Invalid node!")
        Pair(origin, Pair(left, right))
    }

    override fun firstStar(): Int = move("AAA", instructions) { it == "ZZZ" }

    override fun secondStar(): Long {
        val steps = nodes.keys.filter { it.endsWith("A") }.map { pos ->
            move(pos, instructions) { it.endsWith("Z") }
        }
        return steps.map(Int::toLong).fold(1L) { a, b -> findLCM(a, b) }
    }

    private tailrec fun move(position: String, instructions: InstructionSeq, isDestination: (String) -> Boolean): Int =
        if (isDestination(position)) instructions.first().second
        else when (instructions.first().first) {
            'L' -> move(nodes[position]!!.first, instructions.drop(1), isDestination)
            'R' -> move(nodes[position]!!.second, instructions.drop(1), isDestination)
            else -> throw RuntimeException("Invalid instruction!")
        }

    private fun findLCM(a: Long, b: Long): Long {
        fun go(a: Long, b: Long, acc: Long): Long =
            if ((acc % a == 0L && acc % b == 0L) || acc > a * b) acc
            else go(a, b, acc + max(a, b))
        return go(a, b, max(a, b))
    }
}

fun main() {
    println(HauntedWasteland().firstStar())
    println(HauntedWasteland().secondStar())
}
