import day1.Trebuchet;
import day2.CubeConundrum;
import day3.GearRatios;
import day4.Scratchcards;
import day5.IfYouGiveASeedAFertilizer;

public class AdventOfCode {

    public static void main(String[] args) {
        System.out.println(solutions());
    }

    private static String solutions() {
        String output = "";
        Trebuchet trebuchet = new Trebuchet();
        output += String.format("Trebuchet: %s, %s\n", trebuchet.firstStar(), trebuchet.secondStar());
        CubeConundrum cubeConundrum = new CubeConundrum();
        output += String.format("Cube Conundrum: %s, %s\n", cubeConundrum.firstStar(), cubeConundrum.secondStar());
        GearRatios gearRatios = new GearRatios();
        output += String.format("Gear Ratios: %s, %s\n", gearRatios.firstStar(), gearRatios.secondStar());
        Scratchcards scratchcards = new Scratchcards();
        output += String.format("Scratchcards: %s, %s\n", scratchcards.firstStar(), scratchcards.secondStar());
        IfYouGiveASeedAFertilizer ifYouGiveASeedAFertilizer = new IfYouGiveASeedAFertilizer();
        output += String.format("If You Give A Seed A Fertilizer: %s, %s", ifYouGiveASeedAFertilizer.firstStar(), ifYouGiveASeedAFertilizer.secondStar());
        return output;
    }
}
