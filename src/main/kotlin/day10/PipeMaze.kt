package day10

import base.KtPuzzle
import day03.Coordinate

class PipeMaze(resource: String = "day10/input.txt") : KtPuzzle(resource) {

    override fun firstStar(): Int {
        val maze = Maze.fromString(sequenceInput())
        val loop = maze.findLoop()
        return loop.size / 2
    }

    override fun secondStar(): Int {
        val maze = Maze.fromString(sequenceInput())
        maze.print()
        val enlargedMaze = enlargeMaze(maze)
        val loop = enlargedMaze.findLoop()
        val updatedEnlargedMaze =
            Maze(enlargedMaze.tiles.map { (k, v) -> if (loop.contains(k)) Pair(k, v) else Pair(k, '░') }.toMap())
        updatedEnlargedMaze.print()
        val reachable = Maze(findReachable(updatedEnlargedMaze).associateWith { '░' })
        reachable.print()
        val enclosedTiles =
            updatedEnlargedMaze.tiles.filter { it.value == '░' && !reachable.hasNode(it.key) && it.key.x % 2 == 0 && it.key.y % 2 == 0 }
        return enclosedTiles.size
    }

    private fun findReachable(maze: Maze): Set<Coordinate> {
        tailrec fun find(
            current: Coordinate,
            planned: MutableList<Coordinate> = mutableListOf(),
            reachable: MutableList<Coordinate> = mutableListOf()
        ): List<Coordinate> {
            val neighbors = current.adjacentStraightCoordinates()
                .filter { maze.hasTile(it, '░') && !planned.contains(it) && !reachable.contains(it) }
            reachable.add(current)
            planned.addAll(neighbors)
            if (planned.isEmpty()) return reachable
            else {
                val next = planned[0]
                planned.removeAt(0)
                return find(next, planned, reachable)
            }
        }
        return find(maze.northWestCorner).toSet()
    }

    /**
     * Enlarge maze by scaling it 2x to the east and then south,
     * followed by adding a ring of empty tiles around the scaled up maze.
     */
    private fun enlargeMaze(input: Maze): Maze {
        val enlargedMaze = input.tiles.map { (orig, value) ->
            val actualValue = if (value == 'S') input.inferredStartValue else value
            val updated = Coordinate(2 * orig.x, orig.y)
            val added = Coordinate(updated.x + 1, updated.y)
            when (actualValue) {
                '│', '░', '┘', '┐' -> setOf(Pair(updated, value), Pair(added, '░'))
                '─', '└', '┌' -> setOf(Pair(updated, value), Pair(added, '─'))
                else -> throw RuntimeException("Invalid node value!")
            }
        }.flatten().toMap().map { (orig, value) ->
            val actualValue = if (value == 'S') input.inferredStartValue else value
            val updated = Coordinate(orig.x, 2 * orig.y)
            val added = Coordinate(updated.x, updated.y + 1)
            when (actualValue) {
                '─', '░', '┘', '└' -> setOf(Pair(updated, value), Pair(added, '░'))
                '│', '┐', '┌' -> setOf(Pair(updated, value), Pair(added, '│'))
                else -> throw RuntimeException("Invalid node value!")
            }
        }.flatten().toMap()
        val northWestCorner = enlargedMaze.minBy { (k, _) -> k.x + k.y }.key
        val southEastCorner = enlargedMaze.maxBy { (k, _) -> k.x + k.y }.key
        val result: MutableMap<Coordinate, Char> = enlargedMaze.toMutableMap().apply {
            putAll((northWestCorner.x - 1..(southEastCorner.x + 1))
                .map { Pair(Coordinate(it, northWestCorner.y - 1), '░') })
            putAll((northWestCorner.x - 1..(southEastCorner.x + 1))
                .map { Pair(Coordinate(it, southEastCorner.y + 1), '░') })
            putAll((northWestCorner.y..(southEastCorner.y))
                .map { Pair(Coordinate(northWestCorner.x - 1, it), '░') })
            putAll((northWestCorner.y..(southEastCorner.y))
                .map { Pair(Coordinate(southEastCorner.x + 1, it), '░') })
        }
        return Maze(result.toMap())
    }

}

fun main() {
    println(PipeMaze().firstStar())
    println(PipeMaze().secondStar())
}