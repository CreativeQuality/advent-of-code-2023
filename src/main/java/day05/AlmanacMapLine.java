package day05;

public record AlmanacMapLine(long destinationRangeStart, long sourceRangeStart, long rangeLength) {

    public Long map(long source) {
        long sourceRangeEnd = sourceRangeStart + rangeLength - 1;
        if (sourceRangeStart <= source && source <= sourceRangeEnd) {
            return destinationRangeStart + (source - sourceRangeStart);
        }
        else return null;
    }
}
