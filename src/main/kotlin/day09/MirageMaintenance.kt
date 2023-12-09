package day09

import base.KtPuzzle

object MirageMaintenance : KtPuzzle("day09/input.txt") {

    override fun firstStar(): Long =
        sequenceInput().map {
            val diffOp = { a: Long, b: Long -> b - a }
            val predictOp = { a: Long, b: Long -> a + b }
            predictNext(it.split("\\s+".toRegex()).map { n -> n.toLong() }, diffOp, predictOp)
        }.sum()

    override fun secondStar(): Long =
        sequenceInput().map {
            val diffOp = { a: Long, b: Long -> a - b }
            val predictOp = { a: Long, b: Long -> a - b }
            predictNext(it.split("\\s+".toRegex()).map { n -> n.toLong() }.reversed(), diffOp, predictOp)
        }.sum()

    private fun predictNext(values: List<Long>, diffOp: (Long, Long) -> Long, predictOp: (Long, Long) -> Long): Long {
        tailrec fun diff(input: List<Long>, acc: MutableList<List<Long>> = mutableListOf()): List<List<Long>> {
            acc.addLast(input)
            return if (input.all { it == 0L }) acc
            else diff(input.windowed(2, 1).map { diffOp(it[0], it[1]) }, acc)
        }

        tailrec fun predict(input: MutableList<List<Long>>, previousPrediction: Long = 0L): Long {
            val lastLine = input.removeLast()
            val prediction = predictOp(lastLine.last(), previousPrediction)
            return if (input.isEmpty()) prediction
            else predict(input, prediction)
        }

        val mutableDifferences = diff(values).toMutableList()
        mutableDifferences.removeLast()
        return predict(mutableDifferences)
    }
}

fun main() {
    println(MirageMaintenance.firstStar())
    println(MirageMaintenance.secondStar())
}