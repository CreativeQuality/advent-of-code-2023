package day4;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScratchcardsTest {

    @Test
    void firstStar() {
        Scratchcards scratchcards = new Scratchcards();
        assertEquals(13, scratchcards.firstStar());
    }

    @Test
    void secondStar() {
        Scratchcards scratchcards = new Scratchcards();
        assertEquals(30, scratchcards.secondStar());
    }
}