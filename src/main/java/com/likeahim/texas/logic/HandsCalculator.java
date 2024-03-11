package com.likeahim.texas.logic;

import com.likeahim.cardwar.cards.Card;
import com.likeahim.cardwar.cards.CardColor;
import com.likeahim.cardwar.cards.MapOfCards;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class HandsCalculator {

    private static Card PAIR;

    public static Card getPAIR() {
        return PAIR;
    }

    public static void setPAIR(Card PAIR) {
        HandsCalculator.PAIR = PAIR;
    }

    //every hand should have a static number, retrieved if true

    MapOfCards mapOfCards = new MapOfCards();

    private Card retrievACard(int n) {
        return mapOfCards.getMapOfCards().get(n);
    }

    //method to call as last
    public static int checkHigherCard(List<Card> showdownCards) {
        return showdownCards.stream()
                .map(Card::getStrength)
                .reduce(0, Integer::sum);
    }

    public static boolean checkPair(List<Card> showdownCards) {
        List<Integer> list = getListWithDuplicates(showdownCards);
        return list.size() == 1;
    }

    public static boolean checkTwoPairs(List<Card> showdownCards) {
        List<Integer> list = getListWithDuplicates(showdownCards);
        return list.size() == 2 && ((list.get(0) + list.get(1)) / 2) != list.get(0);
    }

    public static boolean checkThreeOfAKind(List<Card> showdownCards) {
        List<Integer> list = getListWithDuplicates(showdownCards);
        double sum = getSumOfList(list);
        return list.size() == 2 && sum / list.size() == list.get(0);
    }

    //method proofs if the power difference between biggest and smallest card equals 4
    public static boolean checkStraight(List<Card> showdownCards) {
        if (!getListWithDuplicates(showdownCards).isEmpty())
            return false;
        List<Integer> sortedList = showdownCards.stream()
                .sorted()
                .map(Card::getStrength)
                .toList();
        return sortedList.get(0) - sortedList.get(4) == 4;
    }

    //this method must be called after checkStraightFlush() and checkStraight() methods, if they return false
    public static boolean checkFlush(List<Card> showdownCards) {
        CardColor handColor = showdownCards.get(0).getColor();
        return showdownCards.stream()
                .allMatch(card -> card.getColor() == handColor);
    }

    public static boolean checkFullHouse(List<Card> showdownCards) {
        List<Integer> list = getListWithDuplicates(showdownCards);
        double sum = getSumOfList(list);
        return list.size() == 3 && sum / list.size() != list.get(0);
    }

    public static boolean checkFourOfAKind(List<Card> showdownCards) {
        List<Integer> list = getListWithDuplicates(showdownCards);
        double sum = getSumOfList(list);
        return list.size() == 3 && sum / list.size() == list.get(0);
    }

    public static boolean checkStraightFlush(List<Card> showdownCards) {
        CardColor handColor = showdownCards.get(0).getColor();
        boolean allMatchSameColor = showdownCards.stream()
                .allMatch(card -> card.getColor() == handColor);
        return checkStraight(showdownCards) && allMatchSameColor;
    }

    private static double getSumOfList(List<Integer> list) {
        return getIntegerOfList(list);
    }

    //redundant?
    private static int getIntegerOfList(List<Integer> list) {
        return list.stream()
                .reduce(0, Integer::sum);
    }


    private static List<Integer> getListWithDuplicates(List<Card> showdownCards) {
        HashSet<Integer> duplicates = new HashSet<>();
        List<Integer> list = showdownCards.stream()
                .map(card -> card.getStrength())
                .filter(n -> !duplicates.add(n))
                .toList();
        return list;
    }


    public static List<List<Card>> generateCombinations(List<Card> cards, List<Card> combination, int combinationLength, int startIndex) {
        List<List<Card>> combinations = new ArrayList<>();

        if (combination.size() == combinationLength) {
            combinations.add(new ArrayList<>(combination));
            return combinations;
        }

        for (int i = startIndex; i < cards.size(); i++) {
            combination.add(cards.get(i));
            combinations.addAll(generateCombinations(cards, combination, combinationLength, i + 1));
            combination.remove(combination.size() - 1);  // remove the last card for the next combination
        }

        return combinations;
    }
//    public static List<List<Card>> generateCombination(List<Card> cards, int combinationLength) {
//        Set<List<Card>> workSet = new HashSet<>();
//        Random random = new Random();
//        while (workSet.size() < 21) {
//            List<Card> workList = new ArrayList<>();
//            while (workList.size() < 5) {
//                int index = random.nextInt(7);
//                Card card = cards.get(index);
//                if (!workList.contains(card))
//                    workList.add(card);
//            }
//            workSet.add(new ArrayList<>(workList));
//        }
//
//        return workSet.stream().toList();
//    }
    /*return Player, who has the highest hand in showdown*/
    /*before this method check if list singleGamePlayers has only one player left, if yes - set player as singleGameWinner and avoid this method*/

    public static Player getSingleGameWinner(List<Player> singleGamePlayers) {
        Player singleWinner = null;
        int counter = 0;
        for (Player player : singleGamePlayers) {
            if (player.isAllIn()) {
                counter++;
                singleWinner = player;
            }
        }
        if (counter == 1)
            return singleWinner;
        for (int i = 0; i < singleGamePlayers.size(); i++) {
            Player player = singleGamePlayers.get(i);
            List<Card> playersCardsInSingleGame = new ArrayList<>(PokerTable.getCommunityCards());
            playersCardsInSingleGame.addAll(player.getCuffsCards());
            List<List<Card>> lists = generateCombinations(playersCardsInSingleGame, new ArrayList<>(), 5, 0);
            setPlayersBestHand(lists, player); //don't recognize full house?
        }
        List<Player> list = singleGamePlayers.stream()
                .sorted((player, t1) -> player.compare(player, t1)) //don't see second player to confirm
                .toList();
        return list.get(0);
    }

    private static void setPlayersBestHand(List<List<Card>> lists, Player player) {
        Hand hand = Hand.HIGH_CARD;
        hand.setSingleGameStrength(180);
        for (List<Card> list : lists) {
            Hand tempHand = checkWhichHand(list);
            int sum = calculateHandsStrength(tempHand, list);
            tempHand.setSingleGameStrength(sum);
            if(tempHand.getPower() > hand.getPower()) {
                assignPlayersStrongestHand(player, list, tempHand);
                hand = tempHand;
            }
            else if (tempHand.getPower() == hand.getPower() && tempHand.getSingleGameStrength() > hand.getSingleGameStrength()) {
                assignPlayersStrongestHand(player, list, tempHand);
                hand = tempHand;
            }
            else
                assignPlayersStrongestHand(player, list, hand);
        }
    }

    private static void assignPlayersStrongestHand(Player player, List<Card> list, Hand tempHand) {
        player.setStrongestHandMark(tempHand);
        player.setStrongestHandList(list);
    }

    private static int calculateHandsStrength(Hand hand, List<Card> handList) {
        int result = 0;
        switch (hand) {
            case ONE_PAIR -> result = calculateOnePairScore(handList);
            case TWO_PAIRS -> result = calculateTwoPairsScore(handList);
            case THREE_OF_A_KIND -> result = calculateThreeOfAKindScore(handList);
            case STRAIGHT -> result = calculateStraightScore(handList);
            case FLUSH -> result = calculateFlushScore(handList);
            case FULL_HOUSE -> result = calculateFullHouseScore(handList);
            case FOUR_OF_A_KIND -> result = calculateFourOfAKindScore(handList);
            case STRAIGHT_FLUSH -> result = straightFlushScore(handList);
            case HIGH_CARD -> result = calculateHighCard(handList);
        }
        return result;
    }

    public static int calculateHighCard(List<Card> handList) {
        List<Integer> list = handList.stream()
                .map(Card::getStrength)
                .sorted(Comparator.reverseOrder())
                .toList();
        return list.get(0)*13 + list.get(1)*12 + list.get(2)*11 + list.get(3)*10 + list.get(4)*9;
    }

    /*sums strength all cards and multiply it by power of enum STRAIGHT_FLUSH*/
    private static int straightFlushScore(List<Card> handList) {
        return calculateStraightScore(handList) * Hand.STRAIGHT_FLUSH.getPower();
    }

    /*multiply four of a kind base by power of enum FOUR_OF_A_KIND
     * and add last cards strength*/
    public static int calculateFourOfAKindScore(List<Card> handList) {
        List<Integer> listWithDuplicates = getListWithDuplicates(handList);
        Integer fourOfAKindBase = listWithDuplicates.get(0);
        List<Integer> listWithHighCard = handList.stream()
                .map(Card::getStrength)
                .filter(c -> !(c.equals(fourOfAKindBase)))
                .toList();
        return fourOfAKindBase * Hand.FOUR_OF_A_KIND.getPower() + listWithHighCard.get(0);
    }

    public static int calculateFullHouseScore(List<Card> handList) {
        List<Integer> listWithDuplicates = getListWithDuplicates(handList);
        HashSet<Integer> triple = new HashSet<>();
        int tripleFullHouseBase = listWithDuplicates.stream()
                .filter(n -> !triple.add(n))
                .mapToInt(Integer::intValue)
                .sum();
        int doubleBase = listWithDuplicates.stream()
                .filter(n -> n != tripleFullHouseBase)
                .mapToInt(Integer::intValue)
                .sum();
        return tripleFullHouseBase * 151 + doubleBase * 2;
    }

    /*sums strength all cards and multiply it by power of enum FLUSH*/
    private static int calculateFlushScore(List<Card> handList) {
        return calculateStraightScore(handList) * Hand.FLUSH.getPower();
    }

    /*sums strength all cards*/
    public static int calculateStraightScore(List<Card> handList) {
        return handList.stream()
                .map(Card::getStrength)
                .sorted(Integer::compareTo)
                .mapToInt(Integer::intValue).sum();
    }

    /*sums: bases strength multiply by 100 and other two cards strength*/
    public static int calculateThreeOfAKindScore(List<Card> handList) {
        List<Integer> listWithDuplicates = getListWithDuplicates(handList);
        Integer threeOfAKindBase = listWithDuplicates.get(0);
        List<Integer> listWithHighCards = handList.stream()
                .map(Card::getStrength)
                .filter(c -> !(c.equals(threeOfAKindBase)))
                .sorted(Comparator.reverseOrder())
                .toList();

        return threeOfAKindBase * 100 + listWithHighCards.get(0) + listWithHighCards.get(1);
    }

    /*sums: bigger pairs base and multiply it by 151, than takes smaller pairs base
     * and multiply it by 13 and strength of card, which doesn't belong to any pair*/
    public static int calculateTwoPairsScore(List<Card> handList) {
        List<Integer> listWithDuplicates = getListWithDuplicates(handList);
        List<Integer> listSorted = listWithDuplicates.stream()
                .sorted(Comparator.reverseOrder())
                .toList();
        Integer pairBaseBigger = listSorted.get(1); ///how to reverse sorting
        Integer pairBaseSmaller = listSorted.get(0);
        int lastCard = handList.stream()
                .map(Card::getStrength)
                .filter(c -> !listWithDuplicates.contains(c))
                .reduce(0, Integer::sum);
        return pairBaseBigger * 151 + pairBaseSmaller * 13 + lastCard;
    }

    /*method takes pair base, multiply by 38 and add to result every single cards strength, which doesn't belong to pair*/
    public static int calculateOnePairScore(List<Card> handList) {
        List<Integer> listWithDuplicates = getListWithDuplicates(handList);
        Integer pairBase = listWithDuplicates.get(0);
        List<Integer> list = handList.stream()
                .map(Card::getStrength)
                .filter(c -> !(c.equals(pairBase)))
                .sorted(Comparator.reverseOrder())
                .toList();

        return pairBase * 38 + list.get(0) * 26 + list.get(1) * 13 + list.get(2);
    }

    private static Hand checkWhichHand(List<Card> list) {
        if (checkStraightFlush(list))
            return Hand.STRAIGHT_FLUSH;
        if (checkFourOfAKind(list))
            return Hand.FOUR_OF_A_KIND;
        if (checkFullHouse(list))
            return Hand.FULL_HOUSE;
        if (checkFlush(list))
            return Hand.FLUSH;
        if (checkStraight(list))
            return Hand.STRAIGHT;
        if (checkThreeOfAKind(list))
            return Hand.THREE_OF_A_KIND;
        if (checkTwoPairs(list))
            return Hand.TWO_PAIRS;
        if (checkPair(list))
            return Hand.ONE_PAIR;
        else
            return Hand.HIGH_CARD;
    }
}