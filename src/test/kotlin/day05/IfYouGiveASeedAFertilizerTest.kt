package day05

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class IfYouGiveASeedAFertilizerTest {
    @Test
    fun firstStar() {
        assertEquals(35, IfYouGiveASeedAFertilizer().firstStar())
    }

    @Test
    fun secondStar() {
        assertEquals(46, IfYouGiveASeedAFertilizer().secondStar())
    }
}