package day03;

import java.util.HashSet;
import java.util.Set;

public record Coordinate(int x, int y) {

    public Set<Coordinate> adjacentCoordinates() {
        return new HashSet<>() {
            {
                addAll(adjacentDiagonalCoordinates());
                addAll(adjacentStraightCoordinates());
            }
        };
    }

    public Set<Coordinate> adjacentDiagonalCoordinates() {
        return Set.of(northWest(), northEast(), southEast(), southWest());
    }

    public Set<Coordinate> adjacentStraightCoordinates() {
        return Set.of(north(), east(), south(), west());
    }

    public Coordinate north() {
        return new Coordinate(x, y - 1);
    }

    public Coordinate east() {
        return new Coordinate(x + 1, y);
    }

    public Coordinate south() {
        return new Coordinate(x, y + 1);
    }

    public Coordinate west() {
        return new Coordinate(x - 1, y);
    }

    public Coordinate northWest() {
        return new Coordinate(x - 1, y - 1);
    }

    public Coordinate northEast() {
        return new Coordinate(x + 1, y - 1);
    }

    public Coordinate southEast() {
        return new Coordinate(x + 1, y + 1);
    }

    public Coordinate southWest() {
        return new Coordinate(x - 1, y + 1);
    }
}
