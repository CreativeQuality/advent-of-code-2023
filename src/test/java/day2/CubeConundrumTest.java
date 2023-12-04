package day2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CubeConundrumTest {

    @Test
    void firstStar() {
        CubeConundrum cubeConundrum = new CubeConundrum();
        assertEquals(8, cubeConundrum.firstStar());
    }

    @Test
    void secondStar() {
        CubeConundrum cubeConundrum = new CubeConundrum();
        assertEquals(2286, cubeConundrum.secondStar());
    }
}