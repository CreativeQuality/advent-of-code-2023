package day5;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IfYouGiveASeedAFertilizerTest {

    @Test
    void firstStar() {
        IfYouGiveASeedAFertilizer ifYouGiveASeedAFertilizer = new IfYouGiveASeedAFertilizer();
        assertEquals(35, ifYouGiveASeedAFertilizer.firstStar());
    }

    @Test
    void secondStar() {
        IfYouGiveASeedAFertilizer ifYouGiveASeedAFertilizer = new IfYouGiveASeedAFertilizer();
        assertEquals(46, ifYouGiveASeedAFertilizer.secondStar());
    }
}