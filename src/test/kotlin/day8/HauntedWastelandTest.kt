package day8

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class HauntedWastelandTest {

    @Test
    fun firstStar() {
        assertEquals(2, HauntedWasteland("day8/input1.txt").firstStar())
    }

    @Test
    fun secondStar() {
        assertEquals(6, HauntedWasteland("day8/input2.txt").secondStar())
    }
}