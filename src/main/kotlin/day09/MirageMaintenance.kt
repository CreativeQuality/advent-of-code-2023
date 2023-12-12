package day09

import base.KtPuzzle
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

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
        tailrec fun diff(input: List<Long>, acc: PersistentList<List<Long>>): PersistentList<List<Long>> {
            val newAcc = acc.add(input)
            return if (input.all { it == 0L }) newAcc
            else diff(input.windowed(2, 1).map { diffOp(it[0], it[1]) }, newAcc)
        }

        tailrec fun predict(input: PersistentList<List<Long>>, previousPrediction: Long): Long {
            val prediction = predictOp(input.last().last(), previousPrediction)
            val newInput = input.dropLast(1).toPersistentList()
            return if (newInput.isEmpty()) prediction
            else predict(newInput, prediction)
        }

        val differences = diff(values, persistentListOf())
        return predict(differences.dropLast(1).toPersistentList(), 0L)
    }
}

fun main() {
    println(MirageMaintenance.firstStar())
    println(MirageMaintenance.secondStar())
}