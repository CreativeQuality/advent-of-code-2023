package day08

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class HauntedWastelandTest {

    @Test
    fun firstStar() {
        assertEquals(2, HauntedWasteland("day08/input1.txt").firstStar())
    }

    @Test
    fun secondStar() {
        assertEquals(6, HauntedWasteland("day08/input2.txt").secondStar())
    }
}