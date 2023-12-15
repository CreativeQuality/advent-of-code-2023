package day15

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class LensLibraryTest {

    @Test
    fun firstStar() {
        assertEquals(1320, LensLibrary.firstStar())
    }

    @Test
    fun secondStar() {
        assertEquals(145, LensLibrary.secondStar())
    }
}