package day3;

import java.util.*;
import java.util.stream.Collectors;

public class EngineSchematic {

    private final Map<Coordinate, Character> values = new HashMap<>();

    private final int xSize, ySize;

    public EngineSchematic(int xSize, int ySize) {
        this.xSize = xSize;
        this.ySize = ySize;
    }

    public Set<Number> getPartNumbers() {
        return getNumbers().stream().filter(n -> {
            Set<Coordinate> adjacentCoordinates = getAdjacentCoordinates(n);
            return adjacentCoordinates.stream().anyMatch(c -> {
                Character character = getValue(c.x(), c.y());
                return !Character.isDigit(character) && character != '.';
            });
        }).collect(Collectors.toSet());
    }

    public Set<Set<Number>> getGearSets() {
        Set<Coordinate> gearCoordinates = getGearCoordinates();
        Set<Number> partNumbers = getPartNumbers();
        return gearCoordinates.stream().map(gc -> {
            Set<Coordinate> adjacentCoordinates = gc.adjacentCoordinates();
            return partNumbers.stream().filter(pn -> {
                HashSet<Coordinate> partNumberCoordinates = new HashSet<>(pn.coordinates());
                partNumberCoordinates.retainAll(adjacentCoordinates);
                return !partNumberCoordinates.isEmpty();
            }).collect(Collectors.toSet());
        }).collect(Collectors.toSet());
    }

    private Set<Coordinate> getGearCoordinates() {
        return values.entrySet().stream().filter(kv -> kv.getValue() == '*')
                .map(Map.Entry::getKey).collect(Collectors.toSet());
    }

    private Set<Number> getNumbers() {
        Set<Number> numbers = new HashSet<>();
        for (int y = 0; y < ySize; y++) {
            Coordinate start = null;
            String value = "";
            for (int x = 0; x < xSize; x++) {
                Character c = getValue(x, y);
                if (Character.isDigit(c)) {
                    if (start == null) start = new Coordinate(x, y);
                    value += c;
                } else if (!value.isEmpty()) {
                    numbers.add(new Number(start, value));
                    start = null;
                    value = "";
                }
            }
            if (!value.isEmpty()) {
                numbers.add(new Number(start, value));
            }
        }
        return numbers;
    }

    private Set<Coordinate> getAdjacentCoordinates(Number number) {
        return number.coordinates().stream().flatMap(c -> c.adjacentCoordinates().stream())
                .filter(c -> !number.coordinates().contains(c))
                .filter(c -> c.x() >= 0 && c.x() < xSize && c.y() >= 0 && c.y() < ySize)
                .collect(Collectors.toSet());
    }

    private Character getValue(int x, int y) {
        return values.get(new Coordinate(x, y));
    }

    private void setValue(int x, int y, Character c) {
        values.put(new Coordinate(x, y), c);
    }

    static EngineSchematic from(List<String> lines) {
        EngineSchematic engineSchematic = new EngineSchematic(lines.getFirst().length(), lines.size());
        for (int y = 0; y < lines.size(); y++) {
            char[] chars = lines.get(y).toCharArray();
            for (int x = 0; x < chars.length; x++)
                engineSchematic.setValue(x, y, chars[x]);
        }
        return engineSchematic;
    }
}
