package day6;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WaitForItTest {

    @Test
    void firstStar() {
        WaitForIt waitForIt = new WaitForIt();
        assertEquals(288, waitForIt.firstStar());
    }

    @Test
    void secondStar() {
        WaitForIt waitForIt = new WaitForIt();
        assertEquals(71503, waitForIt.secondStar());
    }
}