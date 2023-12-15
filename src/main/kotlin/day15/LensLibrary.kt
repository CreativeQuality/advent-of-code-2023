package day15

import base.KtPuzzle

object LensLibrary : KtPuzzle("day15/input.txt") {

    override fun firstStar() =
            sequenceInput().single().splitToSequence(",").map { hash(it) }.sum()

    override fun secondStar(): Int {
        val boxes: MutableMap<Int, MutableMap<String, Int>> = mutableMapOf()
        sequenceInput().single().splitToSequence(",").forEach { step ->
            val (label: String, op: String, focalLength: String) = """(\w+)([=|-])(\d?)""".toRegex()
                    .find(step)?.destructured ?: throw RuntimeException("Unexpected step: $step")
            val box = hash(label)
            when (op) {
                "=" -> boxes.getOrPut(box) { mutableMapOf() }[label] = focalLength.toInt()
                "-" -> boxes[box]?.remove(label)
                else -> throw RuntimeException("Unexpected operation: $op")
            }
        }
        return boxes.flatMap { (box, slots) -> slots.toList().mapIndexed { slot, (_, focalLength) ->
            (1 + box) * (slot + 1) * focalLength } }.sum()
    }

    private fun hash(s: String): Int =
            s.fold(0) { currentValue, char -> ((char.code + currentValue) * 17) % 256 }
}

fun main() {
    println(LensLibrary.firstStar())
    println(LensLibrary.secondStar())
}