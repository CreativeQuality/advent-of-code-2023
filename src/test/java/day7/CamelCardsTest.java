package day7;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CamelCardsTest {

    @Test
    void firstStar() {
        CamelCards camelCards = new CamelCards();
        assertEquals(6440, camelCards.firstStar());
    }

    @Test
    void secondStar() {
        CamelCards camelCards = new CamelCards();
        assertEquals(5905, camelCards.secondStar());
    }
}