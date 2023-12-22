package day01

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class TrebuchetTest {
    @Test
    fun firstStar() {
        assertEquals(142, Trebuchet("day01/input1.txt").firstStar())
    }

    @Test
    fun secondStar() {
        assertEquals(281, Trebuchet("day01/input2.txt").secondStar())
    }
}