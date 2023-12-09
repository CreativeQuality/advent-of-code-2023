package day01;

import base.Puzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class Trebuchet extends Puzzle {

    public static void main(String[] args) {
        Puzzle puzzle = new Trebuchet();
        System.out.printf("First star %s: %s%n", puzzle.getClass(), puzzle.firstStar());
        System.out.printf("Second star %s: %s%n", puzzle.getClass(), puzzle.secondStar());
    }

    public Trebuchet() {
        super("day01/input.txt");
    }

    public Trebuchet(String input) {
        super(input);
    }

    public Integer firstStar() {
        Stream<String> strings = streamInput();
        return strings.mapToInt(s -> {
            List<Character> numbers = new ArrayList<>();
            for (char c : s.toCharArray())
                if (Character.isDigit(c)) numbers.add(c);
            return Integer.parseInt("" + numbers.getFirst() + numbers.getLast());
        }).sum();
    }

    public Integer secondStar() {
        Stream<String> strings = streamInput();
        return strings.mapToInt(s -> {
            List<String> numbers = new ArrayList<>();
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (Character.isDigit(c)) numbers.add(Character.toString(c));
                else findNumberWord(s.substring(i)).ifPresent(numbers::add);
            }
            return Integer.parseInt(numbers.getFirst() + numbers.getLast());
        }).sum();
    }

    private Optional<String> findNumberWord(String haystack) {
        List<String> numberWords =
                Arrays.asList("one", "two", "three", "four", "five", "six", "seven", "eight", "nine");
        for (int i = 0; i < numberWords.size(); i++) {
            String needle = numberWords.get(i);
            if (haystack.startsWith(needle))
                return Optional.of(Integer.toString(i + 1));
        }
        return Optional.empty();
    }
}
