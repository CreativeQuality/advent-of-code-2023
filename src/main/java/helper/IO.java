package helper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Stream;

public class IO {

    public static Stream<String> streamLines(String resource) {
        InputStream inputStream = IO.class.getClassLoader().getResourceAsStream(resource);
        if (inputStream == null)
            return Stream.empty();
        return new BufferedReader(new InputStreamReader(inputStream)).lines();
    }
}
