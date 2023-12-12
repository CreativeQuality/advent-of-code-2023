package day10

import day03.Coordinate
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

class Maze(originalTiles: Map<Coordinate, Char>) {

    companion object Factory {
        fun fromString(seq: Sequence<String>): Maze =
            Maze(seq.flatMapIndexed { y, s -> s.mapIndexed { x, c -> Pair(Coordinate(x, y), c) } }.toMap())
    }

    val tiles: Map<Coordinate, Char>
    val inferredStartValue: Char
    val northWestCorner: Coordinate = originalTiles.minBy { (k, _) -> k.x + k.y }.key

    private val startNode: Coordinate = originalTiles.filterValues { it == 'S' }.keys.singleOrNull() ?: northWestCorner
    private val southEastCorner: Coordinate = originalTiles.maxBy { (k, _) -> k.x + k.y }.key
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

    init {
        tiles = originalTiles.map { (k, v) -> Pair(k, boxDrawing.getOrDefault(v, v)) }.toMap()
        inferredStartValue = boxDrawing.values.first {
            connections(startNode, it).all { neighbor ->
                tiles.containsKey(neighbor) && connections(neighbor).contains(startNode)
            }
        }
    }

    fun hasNode(node: Coordinate): Boolean = tiles.containsKey(node)

    fun hasTile(node: Coordinate, value: Char): Boolean = hasNode(node) && tiles[node]!! == value

    fun findLoop(): List<Coordinate> {
        tailrec fun findIt(node: Coordinate, path: PersistentList<Coordinate> = persistentListOf()): List<Coordinate> {
            val newPath = path.add(node)
            val next = connections(node).filter { !newPath.contains(it) }
            return if (next.isEmpty()) newPath
            else findIt(next.first(), newPath)
        }
        return findIt(startNode)
    }

    fun print() {
        for (y in northWestCorner.x..southEastCorner.y) {
            var line = ""
            for (x in northWestCorner.y..southEastCorner.x) {
                line += tiles[Coordinate(x, y)] ?: ' '
            }
            println(line)
        }
        println()
    }

    private fun connections(
        n: Coordinate,
        value: Char = tiles[n] ?: throw RuntimeException("Invalid node value!")
    ): Set<Coordinate> =
        when (value) {
            '│' -> setOf(n.north(), n.south())
            '─' -> setOf(n.east(), n.west())
            '└' -> setOf(n.north(), n.east())
            '┘' -> setOf(n.north(), n.west())
            '┐' -> setOf(n.south(), n.west())
            '┌' -> setOf(n.south(), n.east())
            '░' -> setOf()
            'S' -> connections(n, inferredStartValue)
            else -> throw RuntimeException("Invalid node value!")
        }
}

