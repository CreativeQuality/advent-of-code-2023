package day10

import base.KtPuzzle
import day03.Coordinate

object PipeMaze : KtPuzzle("day10/input.txt") {

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
        val loop = findLoop(start)
        return loop.size / 2
    }

    private fun findLoop(start: Coordinate): List<Coordinate> {
        tailrec fun findIt(node: Coordinate, path: MutableList<Coordinate> = mutableListOf()): List<Coordinate> {
            path.add(node)
            val next = connections(node).filter { !path.contains(it) }
            return if (next.isEmpty()) path
            else findIt(next.first(), path)
        }
        return findIt(start)
    }

    private fun connections(
        n: Coordinate,
        value: Char = maze[n] ?: throw RuntimeException("Invalid node value!")
    ): Set<Coordinate> =
        when (value) {
            '│' -> setOf(n.north(), n.south())
            '─' -> setOf(n.east(), n.west())
            '└' -> setOf(n.north(), n.east())
            '┘' -> setOf(n.north(), n.west())
            '┐' -> setOf(n.south(), n.west())
            '┌' -> setOf(n.south(), n.east())
            'S' -> connections(n, inferPipe(n))
            '░' -> setOf()
            else -> throw RuntimeException("Invalid node value!")
        }

    private fun inferPipe(node: Coordinate): Char =
        boxDrawing.values.first {
            connections(node, it).all { neighbor -> maze.containsKey(neighbor) && connections(neighbor).contains(node) }
        }

    private fun print(maze: Map<Coordinate, Char>) {
        val southEastCorner = maze.maxBy { (k, _) -> k.x + k.y }.key
        for (y in 0..southEastCorner.y) {
            var line = ""
            for (x in 0..southEastCorner.x) {
                line += maze[Coordinate(x, y)] ?: throw RuntimeException("Coordinate not found!")
            }
            println(line)
        }
    }
}

fun main() {
    println(PipeMaze.firstStar())
}