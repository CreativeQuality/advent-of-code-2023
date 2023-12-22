package day06

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class WaitForItTest {
    @Test
    fun firstStar() {
        assertEquals(288, WaitForIt().firstStar())
    }

    @Test
    fun secondStar() {
        assertEquals(71503, WaitForIt().secondStar())
    }
}