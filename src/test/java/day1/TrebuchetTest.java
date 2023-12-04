package day1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TrebuchetTest {

    @Test
    void firstStar() {
        Trebuchet trebuchet = new Trebuchet("day1/input1.txt");
        assertEquals(142, trebuchet.firstStar());
    }

    @Test
    void secondStar() {
        Trebuchet trebuchet = new Trebuchet("day1/input2.txt");
        assertEquals(281, trebuchet.secondStar());
    }
}