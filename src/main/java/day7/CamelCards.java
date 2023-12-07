package day7;

import base.Puzzle;

import java.util.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.stream.Collectors;

public class CamelCards extends Puzzle {

    public static void main(String[] args) {
        Puzzle puzzle = new CamelCards();
        System.out.printf("First star %s: %s%n", puzzle.getClass(), puzzle.firstStar());
        System.out.printf("Second star %s: %s%n", puzzle.getClass(), puzzle.secondStar());
    }

    private final List<Character> rankedCardsWithoutJokers =
            List.of('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2').reversed();

    private final List<Character> rankedCardsWithJokers =
            List.of('A', 'K', 'Q', 'T', '9', '8', '7', '6', '5', '4', '3', '2', 'J').reversed();

    private enum HandType {
        HIGH_CARD(0), ONE_PAIR(1), TWO_PAIR(2), THREE_OF_A_KIND(3), FULL_HOUSE(4), FOUR_OF_A_KIND(5), FIVE_OF_A_KIND(6);

        public final int value;

        HandType(int value) {
            this.value = value;
        }
    }

    public CamelCards() {
        super("day7/input.txt");
    }

    public Integer firstStar() {
        return solve(false);
    }

    public Integer secondStar() {
        return solve(true);
    }

    private Integer solve(boolean withJokers) {
        List<SimpleEntry<String, Integer>> cardsAndBid = streamInput().map(l -> {
            String[] cardsAndBidString = l.split("\\s+");
            return new SimpleEntry<>(cardsAndBidString[0], Integer.parseInt(cardsAndBidString[1]));
        }).collect(Collectors.toList());
        Comparator<SimpleEntry<String, Integer>> handTypeComparator =
                Comparator.comparing(e -> determineHandType(e.getKey(), withJokers).value);
        List<Character> rankedCards = withJokers ? rankedCardsWithJokers : rankedCardsWithoutJokers;
        Comparator<SimpleEntry<String, Integer>> handTypeThenCardValueComparator =
                handTypeComparator.thenComparing(SimpleEntry::getKey, (c1, c2) -> {
                    Character[] c = determineFirstDifferingCards(c1, c2);
                    return Integer.compare(rankedCards.indexOf(c[0]), rankedCards.indexOf(c[1]));
                });
        cardsAndBid.sort(handTypeThenCardValueComparator);
        int result = 0;
        for (int i = 0; i < cardsAndBid.size(); i++) {
            result += (i + 1) * cardsAndBid.get(i).getValue();
        }
        return result;
    }

    private HandType determineHandType(String hand, boolean withJokers) {
        Map<Character, Integer> cardCount = new HashMap<>();
        for (Character c : hand.toCharArray())
            cardCount.put(c, cardCount.getOrDefault(c, 0) + 1);
        Integer numberOfJokers = withJokers ? cardCount.remove('J') : null;
        List<Integer> counts = cardCount.values().stream().filter(c -> c > 0).toList();
        if (withJokers && numberOfJokers != null) return determineHandTypeWithJokers(counts, numberOfJokers);
        else return determineHandTypeWithoutJokers(counts);
    }

    private HandType determineHandTypeWithJokers(List<Integer> countsExcludingJokers, int numberOfJokers) {
        return switch (numberOfJokers) {
            case 5, 4 -> HandType.FIVE_OF_A_KIND;
            case 3 -> Collections.max(countsExcludingJokers) == 2 ? HandType.FIVE_OF_A_KIND : HandType.FOUR_OF_A_KIND;
            case 2 -> switch (Collections.max(countsExcludingJokers)) {
                case 3 -> HandType.FIVE_OF_A_KIND;
                case 2 -> HandType.FOUR_OF_A_KIND;
                case 1 -> HandType.THREE_OF_A_KIND;
                default -> throw new RuntimeException("Unexpected max cards excluding jokers!");
            };
            case 1 -> switch (Collections.max(countsExcludingJokers)) {
                case 4 -> HandType.FIVE_OF_A_KIND;
                case 3 -> HandType.FOUR_OF_A_KIND;
                case 2 -> countsExcludingJokers.size() == 2 ? HandType.FULL_HOUSE : HandType.THREE_OF_A_KIND;
                case 1 -> HandType.ONE_PAIR;
                default -> throw new RuntimeException("Unexpected max cards excluding jokers!");
            };
            default -> throw new RuntimeException("Unexpected number of jokers!");
        };
    }

    private HandType determineHandTypeWithoutJokers(List<Integer> counts) {
        return switch (counts.size()) {
            case 5 -> HandType.HIGH_CARD;
            case 4 -> HandType.ONE_PAIR;
            case 3 -> Collections.max(counts) == 3 ? HandType.THREE_OF_A_KIND : HandType.TWO_PAIR;
            case 2 -> Collections.max(counts) == 4 ? HandType.FOUR_OF_A_KIND : HandType.FULL_HOUSE;
            case 1 -> HandType.FIVE_OF_A_KIND;
            default -> throw new RuntimeException("Unexpected card count!");
        };
    }

    private Character[] determineFirstDifferingCards(String hand1, String hand2) {
        Character[] chars = new Character[2];
        for (int i = 0; i < hand1.length(); i++) {
            chars[0] = hand1.charAt(i);
            chars[1] = hand2.charAt(i);
            if (chars[0] != chars[1]) return chars;
        }
        return chars;
    }
}
