package day14

import kotlinx.collections.immutable.persistentMapOf
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ParabolicReflectorDishTest {

    @Test
    fun firstStar() {
        assertEquals(136, ParabolicReflectorDish.firstStar())
    }

    @Test
    fun secondStar() {
        assertEquals(persistentMapOf(Pair(1, 'a'), Pair(2, 'b')), persistentMapOf(Pair(2, 'b'), Pair(1, 'a')))
        assertEquals(64, ParabolicReflectorDish.secondStar())
    }
}