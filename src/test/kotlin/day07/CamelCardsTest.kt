package day07

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class CamelCardsTest {
    @Test
    fun firstStar() {
        assertEquals(6440, CamelCards().firstStar())
    }

    @Test
    fun secondStar() {
        assertEquals(5905, CamelCards().secondStar())
    }
}