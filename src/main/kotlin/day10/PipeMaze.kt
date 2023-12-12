package day10

import base.KtPuzzle
import day03.Coordinate
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentMap

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
        val reachable = Maze(updatedEnlargedMaze.findReachable().associateWith { '░' })
        reachable.print()
        val enclosedTiles =
            updatedEnlargedMaze.tiles.filter { it.value == '░' && !reachable.hasNode(it.key) && it.key.x % 2 == 0 && it.key.y % 2 == 0 }
        return enclosedTiles.size
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
        val nw = enlargedMaze.minBy { (k, _) -> k.x + k.y }.key
        val se = enlargedMaze.maxBy { (k, _) -> k.x + k.y }.key
        val result: PersistentMap<Coordinate, Char> = enlargedMaze.toPersistentMap()
            .putAll((nw.x - 1..(se.x + 1))
                .associate { Pair(Coordinate(it, nw.y - 1), '░') })
            .putAll((nw.x - 1..(se.x + 1))
                .associate { Pair(Coordinate(it, se.y + 1), '░') })
            .putAll((nw.y..(se.y))
                .associate { Pair(Coordinate(nw.x - 1, it), '░') })
            .putAll((nw.y..(se.y))
                .associate { Pair(Coordinate(se.x + 1, it), '░') })
        return Maze(result)
    }

}

fun main() {
    println(PipeMaze().firstStar())
    println(PipeMaze().secondStar())
}