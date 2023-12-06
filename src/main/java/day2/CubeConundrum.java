package day2;

import base.Puzzle;
import day1.Trebuchet;

import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class CubeConundrum extends Puzzle {

    public static void main(String[] args) {
        Puzzle puzzle = new CubeConundrum();
        System.out.printf("First star %s: %s", puzzle.getClass(), puzzle.firstStar());
        System.out.printf("Second star %s: %s", puzzle.getClass(), puzzle.secondStar());
    }

    public CubeConundrum() {
        super("day2/input.txt");
    }

    public Integer firstStar() {
        Stream<String> games = streamInput();
        Stream<String> possibleGames = games.filter(l ->
                Arrays.stream(l.substring(l.indexOf(":") + 2).split("[,;]"))
                        .allMatch(this::isPossibleGame));
        Pattern pattern = Pattern.compile("Game (\\d+):");
        return possibleGames.mapToInt(l -> {
                    Matcher matcher = checkMatch(pattern.matcher(l));
                    String group = matcher.group(1);
                    return Integer.parseInt(group);
                }
        ).sum();
    }

    private boolean isPossibleGame(String reveal) {
        int red = 12;
        int green = 13;
        int blue = 14;
        Pattern pattern = Pattern.compile("(\\d+) (\\w+)");
        Matcher matcher = checkMatch(pattern.matcher(reveal));
        int count = Integer.parseInt(matcher.group(1));
        return switch (matcher.group(2)) {
            case "red" -> count <= red;
            case "green" -> count <= green;
            case "blue" -> count <= blue;
            default -> throw new RuntimeException("Unknown color!");
        };
    }

    public Integer secondStar() {
        Stream<String> games = streamInput();
        return games.map(g -> g.substring(g.indexOf(":") + 2))
                .mapToInt(g -> findMaximumCubes(g).reduce(1, (a, b) -> a * b))
                .sum();
    }

    private Stream<Integer> findMaximumCubes(String game) {
        Stream<String> reveals = Arrays.stream(game.split("[,;]"));
        Pattern pattern = Pattern.compile("(\\d+) (\\w+)");
        HashMap<String, Integer> maximumCubeCount = new HashMap<>();
        reveals.forEach(reveal -> {
            Matcher matcher = checkMatch(pattern.matcher(reveal));
            int count = Integer.parseInt(matcher.group(1));
            String color = matcher.group(2);
            int max = Math.max(count, maximumCubeCount.getOrDefault(color, 0));
            maximumCubeCount.put(color, max);
        });
        return maximumCubeCount.values().stream();
    }

    /**
     * This silly method is needed because Java requires us to check if anything is found by the regex
     * before filling in the capture groups.
     */
    private Matcher checkMatch(Matcher matcher) {
        if (!matcher.find())
            throw new RuntimeException("Unexpected mismatch");
        return matcher;
    }
}
