package day02

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class CubeConundrumTest {
    @Test
    fun firstStar() {
        assertEquals(8, CubeConundrum().firstStar())
    }

    @Test
    fun secondStar() {
        assertEquals(2286, CubeConundrum().secondStar())
    }
}