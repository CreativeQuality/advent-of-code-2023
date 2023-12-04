package base;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Stream;

public abstract class Puzzle {

    public Puzzle(String resourceLocation) {
        this.resourceLocation = resourceLocation;
    }

    private final String resourceLocation;

    public Stream<String> streamInput() {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(resourceLocation);
        if (inputStream == null)
            return Stream.empty();
        return new BufferedReader(new InputStreamReader(inputStream)).lines();
    }
}
