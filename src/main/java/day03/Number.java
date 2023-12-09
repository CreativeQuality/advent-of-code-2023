package day03;

import java.util.ArrayList;
import java.util.List;

public record Number(Coordinate start, String value) {

    public Integer length() {
        return value.length();
    }

    public List<Coordinate> coordinates() {
        ArrayList<Coordinate> coordinates = new ArrayList<>();
        coordinates.add(start);
        for (int i = 1; i < length(); i++) {
            coordinates.add(new Coordinate(start.x() + i, start.y()));
        }
        return coordinates;
    }
}
