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


    public static List<List<Card>> generateCombination(List<Card> numbers, int combinationLength) {
        Set<List<Card>> workSet = new HashSet<>();
        Random random = new Random();
        while(workSet.size() < 21) {
            List<Card> workList = new ArrayList<>();
            while(workList.size() < 5) {
                int index = random.nextInt(7);
                Card card = numbers.get(index);
                if(!workList.contains(card))
                    workList.add(card);
            }
            workSet.add(new ArrayList<>(workList));
        }

        return workSet.stream().toList();
    }
    /*return Player, who has the highest hand in showdown*/
    public static Player getSingleGameWinner(List<Player> singleGamePlayers) {
        List<Player> winnerPlayer = new ArrayList<>(singleGamePlayers);
        for(int i = 0; i < singleGamePlayers.size(); i++) {
            List<Card> cuffsCards = singleGamePlayers.get(i).getCuffsCards();
            List<Card> playersCardsInSingleGame = new ArrayList<>(PokerTable.getCommunityCards());
            playersCardsInSingleGame.addAll(cuffsCards);
            List<List<Card>> lists = generateCombination(playersCardsInSingleGame, 5);

        }
    }
}
