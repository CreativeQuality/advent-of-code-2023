package day10

import base.KtPuzzle
import day03.Coordinate

class PipeMaze(resource: String = "day10/input.txt") : KtPuzzle(resource) {

    private val boxDrawing: Map<Char, Char> =
        mapOf(
            Pair('|', '│'),
            Pair('-', '─'),
            Pair('L', '└'),
            Pair('J', '┘'),
            Pair('7', '┐'),
            Pair('F', '┌'),
            Pair('.', '░')
        )

    private val maze: Map<Coordinate, Char> = sequenceInput().flatMapIndexed { y, s ->
        s.mapIndexed { x, c -> Pair(Coordinate(x, y), boxDrawing.getOrDefault(c, c)) }
    }.toMap()

    override fun firstStar(): Int {
        print(maze)
        val start = maze.filterValues { it == 'S' }.keys.single()
        val loop = findLoop(start, maze)
        return loop.size / 2
    }

    override fun secondStar(): Int {
        print(maze)
        val enlargedMaze = enlargeMaze()
        val start = enlargedMaze.filterValues { it == 'S' }.keys.single()
        val loop = findLoop(start, enlargedMaze)
        val updatedEnlargedMaze = enlargedMaze.map { (k,v) -> if (loop.contains(k)) Pair(k, v) else Pair(k, '░') }.toMap()
        print(updatedEnlargedMaze)
        val reachable = findReachable(updatedEnlargedMaze)
        print(reachable.associateWith { '░' })
        val enclosedTiles =
            updatedEnlargedMaze.filter { it.value == '░' && !reachable.contains(it.key) && it.key.x % 2 == 0 && it.key.y % 2 == 0 }
        return enclosedTiles.size
    }

    private fun findLoop(start: Coordinate, maze: Map<Coordinate, Char>): List<Coordinate> {
        tailrec fun findIt(node: Coordinate, path: MutableList<Coordinate> = mutableListOf()): List<Coordinate> {
            path.add(node)
            val next = connections(node, maze).filter { !path.contains(it) }
            return if (next.isEmpty()) path
            else findIt(next.first(), path)
        }
        return findIt(start)
    }

    private fun connections(
        n: Coordinate,
        maze: Map<Coordinate, Char>,
        value: Char = maze[n] ?: throw RuntimeException("Invalid node value!")
    ): Set<Coordinate> =
        when (value) {
            '│' -> setOf(n.north(), n.south())
            '─' -> setOf(n.east(), n.west())
            '└' -> setOf(n.north(), n.east())
            '┘' -> setOf(n.north(), n.west())
            '┐' -> setOf(n.south(), n.west())
            '┌' -> setOf(n.south(), n.east())
            'S' -> connections(n, maze, inferPipe(n, maze))
            '░' -> setOf()
            else -> throw RuntimeException("Invalid node value!")
        }

    private fun inferPipe(node: Coordinate, maze: Map<Coordinate, Char>): Char =
        boxDrawing.values.first {
            connections(node, maze, it).all { neighbor -> maze.containsKey(neighbor) && connections(neighbor, maze).contains(node) }
        }

    private fun findReachable(maze: Map<Coordinate, Char>): Set<Coordinate> {
        tailrec fun find(
            current: Coordinate,
            planned: MutableList<Coordinate> = mutableListOf(),
            reachable: MutableList<Coordinate> = mutableListOf()
        ): List<Coordinate> {
            val neighbors = current.adjacentStraightCoordinates()
                .filter { maze.containsKey(it) && maze[it]!! == '░' && !planned.contains(it) && !reachable.contains(it) }
            reachable.add(current)
            planned.addAll(neighbors)
            if (planned.isEmpty()) return reachable
            else {
                val next = planned[0]
                planned.removeAt(0)
                return find(next, planned, reachable)
            }
        }

        val northWestCorner = maze.minBy { (k, _) -> k.x + k.y }.key
        return find(northWestCorner).toSet()
    }

    /**
     * Enlarge maze by scaling it 2x to the east and then south,
     * followed by adding a ring of empty tiles around the scaled up maze.
     */
    private fun enlargeMaze(): Map<Coordinate, Char> {
        val startNode = maze.filterValues { it == 'S' }.keys.single()
        val startValue = inferPipe(startNode, maze)
        val enlargedMaze = maze.map { (orig, value) ->
            val inferredValue = if (value == 'S') startValue else value
            val updated = Coordinate(2 * orig.x, orig.y)
            val added = Coordinate(updated.x + 1, updated.y)
            when (inferredValue) {
                '│', '░', '┘', '┐' -> setOf(Pair(updated, value), Pair(added, '░'))
                '─', '└', '┌' -> setOf(Pair(updated, value), Pair(added, '─'))
                else -> throw RuntimeException("Invalid node value!")
            }
        }.flatten().toMap().map { (orig, value) ->
            val inferredValue = if (value == 'S') startValue else value
            val updated = Coordinate(orig.x, 2 * orig.y)
            val added = Coordinate(updated.x, updated.y + 1)
            when (inferredValue) {
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
        return result.toMap()
    }

    private fun print(maze: Map<Coordinate, Char>) {
        val northWestCorner = maze.minBy { (k, _) -> k.x + k.y }.key
        val southEastCorner = maze.maxBy { (k, _) -> k.x + k.y }.key
        for (y in northWestCorner.x..southEastCorner.y) {
            var line = ""
            for (x in northWestCorner.y..southEastCorner.x) {
                line += maze[Coordinate(x, y)] ?: ' '
            }
            println(line)
        }
        println()
    }
}

fun main() {
    println(PipeMaze().firstStar())
    println(PipeMaze().secondStar())
}