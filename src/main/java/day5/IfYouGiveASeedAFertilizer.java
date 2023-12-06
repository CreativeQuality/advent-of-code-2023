package day5;

import base.Puzzle;

import java.util.*;
import java.util.stream.LongStream;

public class IfYouGiveASeedAFertilizer extends Puzzle {

    public static void main(String[] args) {
        Puzzle puzzle = new IfYouGiveASeedAFertilizer();
        System.out.printf("First star %s: %s", puzzle.getClass(), puzzle.firstStar());
        System.out.printf("Second star %s: %s", puzzle.getClass(), puzzle.secondStar());
    }

    private final List<Long> seeds = new ArrayList<>();
    private final List<AlmanacMapLine> seedToSoil = new ArrayList<>();
    private final List<AlmanacMapLine> soilToFertilizer = new ArrayList<>();
    private final List<AlmanacMapLine> fertilizerToWater = new ArrayList<>();
    private final List<AlmanacMapLine> waterToLight = new ArrayList<>();
    private final List<AlmanacMapLine> lightToTemperature = new ArrayList<>();
    private final List<AlmanacMapLine> temperatureToHumidity = new ArrayList<>();
    private final List<AlmanacMapLine> humidityToLocation = new ArrayList<>();

    public IfYouGiveASeedAFertilizer() {
        super("day5/input.txt");
    }

    public Long firstStar() {
        initAlmanacMaps(streamInput().toList());
        return getMinimalLocation(seeds.stream().mapToLong(l -> l));
    }

    public Long secondStar() {
        initAlmanacMaps(streamInput().toList());
        return getMinimalLocation(getRangedSeeds());
    }

    private LongStream getRangedSeeds() {
        LongStream rangedSeeds = LongStream.of();
        for (int i = 0; i < seeds.size(); i += 2) {
            long start = seeds.get(i);
            long range = seeds.get(i + 1);
            rangedSeeds = LongStream.concat(rangedSeeds, LongStream.range(start, start + range));
        }
        return rangedSeeds;
    }

    private Long getMinimalLocation(LongStream seeds) {
        OptionalLong minimalLocation = seeds.parallel()
                .map(seed -> sourceToDestination(seedToSoil, seed))
                .map(soil -> sourceToDestination(soilToFertilizer, soil))
                .map(fertilizer -> sourceToDestination(fertilizerToWater, fertilizer))
                .map(water -> sourceToDestination(waterToLight, water))
                .map(light -> sourceToDestination(lightToTemperature, light))
                .map(temperature -> sourceToDestination(temperatureToHumidity, temperature))
                .map(humidity -> sourceToDestination(humidityToLocation, humidity))
                .min();
        return minimalLocation.orElseThrow();
    }

    private Long sourceToDestination(List<AlmanacMapLine> almanacMap, long source) {
        Optional<Long> destination = almanacMap.stream().map(l -> l.map(source))
                .filter(Objects::nonNull).findFirst();
        return destination.orElse(source);
    }

    private void initAlmanacMaps(List<String> lines) {
        String activeMapName = null;
        for (String line : lines) {
            if (line.startsWith("seeds:")) Arrays.stream(line.substring(7).split(" "))
                    .forEachOrdered(n -> seeds.add(Long.parseLong(n)));
            else if (line.contains("map")) activeMapName = line.substring(0, line.length() - 5);
            else if (!line.isEmpty()) {
                List<AlmanacMapLine> activeMap = getAlmanacMap(activeMapName);
                List<Long> mapLineNumbers = Arrays.stream(line.split(" ")).map(Long::parseLong).toList();
                activeMap.add(new AlmanacMapLine(mapLineNumbers.get(0), mapLineNumbers.get(1), mapLineNumbers.get(2)));
            }
        }
    }

    private List<AlmanacMapLine> getAlmanacMap(String mapName) {
        return switch (mapName) {
            case null -> throw new RuntimeException("Unexpected map name!");
            case "seed-to-soil" -> seedToSoil;
            case "soil-to-fertilizer" -> soilToFertilizer;
            case "fertilizer-to-water" -> fertilizerToWater;
            case "water-to-light" -> waterToLight;
            case "light-to-temperature" -> lightToTemperature;
            case "temperature-to-humidity" -> temperatureToHumidity;
            case "humidity-to-location" -> humidityToLocation;
            default -> throw new RuntimeException("Unknown map name!");
        };
    }
}
