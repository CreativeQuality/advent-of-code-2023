package day10

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class PipeMazeTest {

    @Test
    fun firstStar() {
        assertEquals(8, PipeMaze("day10/input1.txt").firstStar())
    }

    @Test
    fun secondStar() {
        assertEquals(4, PipeMaze("day10/input2.txt").secondStar())
        assertEquals(8, PipeMaze("day10/input3.txt").secondStar())
        assertEquals(10, PipeMaze("day10/input4.txt").secondStar())
    }
}