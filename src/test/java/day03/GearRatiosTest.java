package day03;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GearRatiosTest {

    @Test
    void firstStar() {
        GearRatios gearRatios = new GearRatios();
        assertEquals(4361, gearRatios.firstStar());
    }

    @Test
    void secondStar() {
        GearRatios gearRatios = new GearRatios();
        assertEquals(467835, gearRatios.secondStar());
    }
}