package day6;

import base.Puzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.LongStream;

public class WaitForIt extends Puzzle {

    public static void main(String[] args) {
        Puzzle puzzle = new WaitForIt();
        System.out.printf("First star %s: %s%n", puzzle.getClass(), puzzle.firstStar());
        System.out.printf("Second star %s: %s%n", puzzle.getClass(), puzzle.secondStar());
    }

    public WaitForIt() {
        super("day6/input.txt");
    }

    public Long firstStar() {
        List<String> input = streamInput().toList();
        List<Long> times = Arrays.stream(input.getFirst().substring(5).trim().split("\\s+")).map(Long::parseLong).toList();
        List<Long> distances = Arrays.stream(input.get(1).substring(9).trim().split("\\s+")).map(Long::parseLong).toList();
        List<Long> numberOfWaysToWin = new ArrayList<>();
        for (int i = 0; i < times.size(); i++) {
            long time = times.get(i);
            long distance = distances.get(i);
            numberOfWaysToWin.add(LongStream.range(0, time).map(t -> calculateDistance(t, time)).filter(d -> d > distance).count());
        }
        return numberOfWaysToWin.stream().reduce((a, b) -> a * b).orElseThrow();
    }

    public Long secondStar() {
        List<String> input = streamInput().toList();
        long time = Long.parseLong(input.getFirst().substring(5).trim().replaceAll("\\s+", ""));
        long distance = Long.parseLong(input.get(1).substring(9).trim().replaceAll("\\s+", ""));
        return LongStream.range(0, time).map(t -> calculateDistance(t, time)).filter(d -> d > distance).count();
    }

    private long calculateDistance(long buttonPressTime, long raceTime) {
        long time = raceTime - buttonPressTime;
        return buttonPressTime * time;
    }
}
