package day12

import base.KtPuzzle

object HotSprings : KtPuzzle("day12/input.txt") {

    override fun firstStar(): Int =
        sequenceInput().map { process(it) }.sum()

    override fun secondStar(): Int =
        sequenceInput().map { process(it, 5) }.sum()

    private fun process(line: String, folds: Int = 1): Int {
        println("Processing $line")
        val parts = line.split(" ")
        val record = parts.first()
        val groups = parts[1].split(",").map { group -> group.toInt() }
        val foldedRecord = (0..<folds).joinToString("?") { record }
        val foldedGroups = (0..<folds).flatMap{ groups }
        val arrangements = fillBlanks(foldedRecord, foldedGroups)
        val count = arrangements.count { matches(it, foldedGroups) }
        return count
    }

    private fun fillBlanks(record: String, groups: List<Int>): Sequence<String> {
        tailrec fun fill(records: Sequence<String>): Sequence<String> =
            if (!records.first().contains('?')) records
            else {
                val newRecords = records.flatMap { record ->
                    sequenceOf(record.replaceFirst('?', '.'), record.replaceFirst('?', '#'))
                }
                val viable = newRecords.filter { record ->
                    val currentGroups = record.substringBefore('?').split("\\.+".toRegex())
                        .map { it.length }.filter { it > 0 }
                    val matching = currentGroups.zip(groups).takeWhile { (f, g) -> f == g }
                    matching.size >= (currentGroups.size - 1)
                }
                fill(viable)
            }

        return fill(sequenceOf(record))
    }

    private fun matches(arrangement: String, groups: List<Int>): Boolean {
        val count = arrangement.split("\\.+".toRegex()).map { it.length }.filter { it > 0 }
        return count == groups
    }
}

fun main() {
    print(HotSprings.firstStar())
}