package day09

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class MirageMaintenanceTest {

    @Test
    fun firstStar() {
        assertEquals(114, MirageMaintenance.firstStar())
    }

    @Test
    fun secondStar() {
        assertEquals(2, MirageMaintenance.secondStar())
    }
}