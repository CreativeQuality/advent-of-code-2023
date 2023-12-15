package day11

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class CosmicExpansionTest {

    @Test
    fun firstStar() {
        assertEquals(374, CosmicExpansion.firstStar())
    }

    @Test
    fun expandAndCalculate() {
        assertEquals(1030, CosmicExpansion.expandAndCalculate(9))
    }
}