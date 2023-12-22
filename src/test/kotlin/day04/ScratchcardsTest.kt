package day04

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class ScratchcardsTest {
    @Test
    fun firstStar() {
        assertEquals(13, Scratchcards().firstStar())
    }

    @Test
    fun secondStar() {
        assertEquals(30, Scratchcards().secondStar())
    }
}