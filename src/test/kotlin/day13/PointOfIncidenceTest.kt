package day13

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class PointOfIncidenceTest {

    @Test
    fun firstStar() {
        assertEquals(405, PointOfIncidence().firstStar())
        assertEquals(405, PointOfIncidence("day13/input-bottom-and-right-edge.txt").firstStar())
        assertEquals(401, PointOfIncidence("day13/input-top-and-left-edge-and-double-reflection.txt").firstStar())
    }

    @Test
    fun secondStar() {
        assertEquals(400, PointOfIncidence().secondStar())
    }
}