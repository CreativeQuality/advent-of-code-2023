package day3;

import java.util.Set;

public record Coordinate(int x, int y) {

    public Set<Coordinate> adjacentCoordinates() {
        return Set.of(
                new Coordinate(x - 1, y - 1), new Coordinate(x, y - 1), new Coordinate(x + 1, y - 1),
                new Coordinate(x - 1, y), new Coordinate(x + 1, y),
                new Coordinate(x - 1, y + 1), new Coordinate(x, y + 1), new Coordinate(x + 1, y + 1)
        );
    }
}
