package com.likeahim.texas.logic;

import com.likeahim.cardwar.cards.Card;
import com.likeahim.cardwar.cards.MapOfCards;

import java.util.*;
import java.util.stream.IntStream;

public class Hands {

    private static Card PAIR;
    public static Card getPAIR() {
        return PAIR;
    }

    public static void setPAIR(Card PAIR) {
        Hands.PAIR = PAIR;
    }


    //every hand should have a static number, retrieved if true

    MapOfCards mapOfCards = new MapOfCards();
    private Card retrievACard(int n) {
        return mapOfCards.getMapOfCards().get(n);
    }
    public static boolean checkPair(List<Card> showdownCards) {
        List<Integer> list = getListWithDuplicates(showdownCards);
        return list.size() == 1;
    }

    public static boolean checkTwoPairs(List<Card> showdownCards) {
        List<Integer> list = getListWithDuplicates(showdownCards);
        return list.size() == 2 && (list.get(0) + list.get(1)) / 2 != list.get(0);
    }

    public static boolean checkThreeOfAKind(List<Card> showdownCards) {
        List<Integer> list = getListWithDuplicates(showdownCards);
        int sum = getSumOfList(list);
        return list.size() == 2 && sum / list.size() == list.get(0);
    }

    public static boolean checkFourOfAKind(List<Card> showdownCards) {
        List<Integer> list = getListWithDuplicates(showdownCards);
        int sum = getSumOfList(list);
        return list.size() == 3 && sum / list.size() == list.get(0);
    }

    public static boolean checkStraight(List<Card> showdownCards) {
        List<Integer> list = showdownCards.stream()
                .map(Card::getStrength)
                .toList();
        OptionalInt max = IntStream.range(0, list.size() - 1)
                .map(n -> list.get(n))
                .max();
        int asInt = max.getAsInt() * 5;
        int sum = list.stream().mapToInt(integer -> integer)
                .sum();
        return asInt - sum == 10;
    }

    private static int getSumOfList(List<Integer> list) {
        int sum = 0;
        for (Integer strength : list) {
            sum += strength;
        }
        return sum;
    }


    private static List<Integer> getListWithDuplicates(List<Card> showdownCards) {
        HashSet<Integer> duplicates = new HashSet<>();
        List<Integer> list = showdownCards.stream()
                .map(card -> card.getStrength())
                .filter(n -> !duplicates.add(n))
                .toList();
        return list;
    }
}
