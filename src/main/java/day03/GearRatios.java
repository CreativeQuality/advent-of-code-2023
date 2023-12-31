package day03;

import base.Puzzle;

import java.util.Set;

public class GearRatios extends Puzzle {

    public static void main(String[] args) {
        Puzzle puzzle = new GearRatios();
        System.out.printf("First star %s: %s%n", puzzle.getClass(), puzzle.firstStar());
        System.out.printf("Second star %s: %s%n", puzzle.getClass(), puzzle.secondStar());
    }

    public GearRatios() {
        super("day03/input.txt");
    }

    public Integer firstStar() {
        EngineSchematic engineSchematic = EngineSchematic.from(streamInput().toList());
        return engineSchematic.getPartNumbers().stream().mapToInt(n -> Integer.parseInt(n.value())).sum();
    }

    public Integer secondStar() {
        EngineSchematic engineSchematic = EngineSchematic.from(streamInput().toList());
        Set<Set<Number>> gearSets = engineSchematic.getGearSets();
        return gearSets.stream().filter(gs -> gs.size() > 1).mapToInt(gs ->
            gs.stream().mapToInt(g -> Integer.parseInt(g.value())).reduce((a, b) -> a * b).getAsInt()
        ).sum();
    }
}
