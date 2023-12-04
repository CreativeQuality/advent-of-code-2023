import day1.Trebuchet;

public class AdventOfCode {

    public static void main(String[] args) {
        System.out.println(solutions());
    }

    private static String solutions() {
        String output = "";
        Trebuchet trebuchet = new Trebuchet();
        output += String.format("Trebuchet: %s, %s\n", trebuchet.firstStar(), trebuchet.secondStar());
        return output;
    }
}
