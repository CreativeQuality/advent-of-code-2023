package day10

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class PipeMazeTest {

    @Test
    fun firstStar() {
        assertEquals(8, PipeMaze.firstStar())
    }

    @Test
    fun secondStar() {
        PipeMaze.secondStar()
    }
}