package day14

import base.KtPuzzle
import day03.Coordinate

object ParabolicReflectorDish : KtPuzzle("day14/input.txt") {

    override fun firstStar(): Int {
        val initialGrid = sequenceInput().mapIndexed { y, line ->
            line.mapIndexed { x, char -> Pair(Coordinate(x + 1, y + 1), char) }
        }.flatten().toMap()
        val updatedGrid = tilt(initialGrid)
        val height = updatedGrid.maxOf { it.key.y }
        return updatedGrid.filter { it.value == 'O' }.keys.sumOf { height - it.y + 1 }
    }

    override fun secondStar(): Int {
        tailrec fun cycleBillionTimesShortCircuited(
            grid: Map<Coordinate, Char>,
            counter: Int = 1,
            history: List<Map<Coordinate, Char>> = listOf(grid)
        ): Map<Coordinate, Char> {
            println(counter)
            val cycledGrid = cycle(grid)
            val oneBillion = 1_000_000_000
            return if (counter == oneBillion) grid
            else if (history.contains(cycledGrid)) {
                val previousCycle = history.indexOf(cycledGrid)
                val stepsBetweenCycles = counter - previousCycle
                val stepsToConsider = ((oneBillion - counter) % stepsBetweenCycles)
                val equivalentToBillionthStep = previousCycle + stepsToConsider
                println("Cycle detected at step $counter, previous occurrence at step $previousCycle, billionth: $equivalentToBillionthStep")
                return history[equivalentToBillionthStep]
            }
            else cycleBillionTimesShortCircuited(cycledGrid, counter + 1, history + cycledGrid)
        }

        val initialGrid = sequenceInput().mapIndexed { y, line ->
            line.mapIndexed { x, char -> Pair(Coordinate(x + 1, y + 1), char) }
        }.flatten().toMap()
        val updatedGrid = cycleBillionTimesShortCircuited(initialGrid)
        val height = updatedGrid.maxOf { it.key.y }
        return updatedGrid.filter { it.value == 'O' }.keys.sumOf { height - it.y + 1 }
    }

    private fun tilt(initialGrid: Map<Coordinate, Char>): Map<Coordinate, Char> =
        initialGrid.keys.sortedBy { it.y }.fold(initialGrid) { grid, coordinate ->
            if (grid[coordinate] == 'O') {
                val cellsToTheNorth = grid.filter { it.key.x == coordinate.x && it.key.y < coordinate.y }.toList()
                if (cellsToTheNorth.isNotEmpty()) {
                    val blocker = cellsToTheNorth.sortedByDescending { it.first.y }
                        .firstOrNull { it.second == 'O' || it.second == '#' }
                    val newCoordinate =
                        if (blocker == null) Coordinate(coordinate.x, 1)
                        else Coordinate(coordinate.x, blocker.first.y + 1)
                    grid - coordinate - newCoordinate + Pair(coordinate, '.') + Pair(newCoordinate, 'O')
                } else grid
            } else grid
        }

    private fun cycle(grid: Map<Coordinate, Char>): Map<Coordinate, Char> {
        val tiltedNorth = tilt(grid)
        val tiltedWest = tilt(rotate(tiltedNorth))
        val tiltedSouth = tilt(rotate(tiltedWest))
        val tiltedEast = tilt(rotate(tiltedSouth))
        return rotate(tiltedEast)
    }

    private fun rotate(grid: Map<Coordinate, Char>): Map<Coordinate, Char> {
        val width = grid.maxOf { it.key.x }
        val height = grid.maxOf { it.key.y }
        val rotated = (1..width).flatMap { xFromLeft ->
            (1..height).map { yFromBottom ->
                val x = height - yFromBottom + 1
                val y = xFromLeft
                val value = grid[Coordinate(xFromLeft, yFromBottom)]!!
                Pair(Coordinate(x, y), value)
            }
        }.toMap()
        return rotated
    }
}

fun main() {
    println(ParabolicReflectorDish.firstStar())
    println(ParabolicReflectorDish.secondStar())
}