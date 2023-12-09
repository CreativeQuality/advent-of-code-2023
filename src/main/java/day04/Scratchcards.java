package day04;

import base.Puzzle;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Scratchcards extends Puzzle {

    public static void main(String[] args) {
        Puzzle puzzle = new Scratchcards();
        System.out.printf("First star %s: %s%n", puzzle.getClass(), puzzle.firstStar());
        System.out.printf("Second star %s: %s%n", puzzle.getClass(), puzzle.secondStar());
    }

    public Scratchcards() {
        super("day04/input.txt");
    }

    public Integer firstStar() {
        Stream<List<Set<Integer>>> cardStream = streamInput().map(l ->
                Arrays.stream(l.replaceFirst("Card[\\s]+[\\d]+:", "").split("\\|"))
                        .map(s -> {
                            String[] numbersAsStrings = s.trim().split("\\s+");
                            return Arrays.stream(numbersAsStrings).map(Integer::parseInt).collect(Collectors.toSet());
                        }).toList()
        );
        return cardStream.mapToInt(card -> {
            Set<Integer> winningNumbers = card.get(0);
            Set<Integer> numbersYouHave = card.get(1);
            numbersYouHave.retainAll(winningNumbers);
            return (int) Math.pow(2, numbersYouHave.size() - 1);
        }).sum();
    }

    public Integer secondStar() {
        List<List<Set<Integer>>> cardList = streamInput().map(l ->
                Arrays.stream(l.replaceFirst("Card[\\s]+[\\d]+:", "").split("\\|"))
                        .map(s -> {
                            String[] numbersAsStrings = s.trim().split("\\s+");
                            return Arrays.stream(numbersAsStrings).map(Integer::parseInt).collect(Collectors.toSet());
                        }).toList()
        ).toList();
        Map<Integer, Integer> additionalCards = getAdditionalCards(cardList);
        int originalCards = cardList.size();
        int extraCards = additionalCards.values().stream().mapToInt(c -> c).sum();
        return originalCards + extraCards;
    }

    private static Map<Integer, Integer> getAdditionalCards(List<List<Set<Integer>>> cardList) {
        Map<Integer, Integer> additionalCards = new HashMap<>();
        for (int c = 0; c < cardList.size(); c++) {
            List<Set<Integer>> card = cardList.get(c);
            Set<Integer> winningNumbers = card.get(0);
            Set<Integer> numbersYouHave = card.get(1);
            numbersYouHave.retainAll(winningNumbers);
            for (int x = 1; x < numbersYouHave.size() + 1; x++) {
                int indexOfAdditionalCard = c + x;
                int copiesOfCurrentCard = 1 + additionalCards.getOrDefault(c, 0);
                int updatedValue = additionalCards.getOrDefault(indexOfAdditionalCard, 0) + copiesOfCurrentCard;
                additionalCards.put(indexOfAdditionalCard, updatedValue);
            }
        }
        return additionalCards;
    }
}
