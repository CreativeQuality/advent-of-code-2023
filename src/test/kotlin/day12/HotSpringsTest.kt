package day12

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class HotSpringsTest {

    @Test
    fun firstStar() {
        assertEquals(21, HotSprings.firstStar())
    }

    @Test
    fun secondStar() {
        assertEquals(525152, HotSprings.secondStar())
    }
}