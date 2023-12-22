package day03

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class GearRatiosTest {
    @Test
    fun firstStar() {
        assertEquals(4361, GearRatios().firstStar())
    }

    @Test
    fun secondStar() {
        assertEquals(467835, GearRatios().secondStar())
    }
}