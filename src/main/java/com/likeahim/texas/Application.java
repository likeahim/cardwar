package com.likeahim.texas;

import com.likeahim.cardwar.cards.*;
import com.likeahim.texas.logic.Hand;
import com.likeahim.texas.logic.HandsCalculator;
import com.likeahim.texas.logic.Player;
import com.likeahim.texas.logic.PokerTable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Application {
    public static void main(String[] args) {
        PokerTable table = new PokerTable();
        boolean end = false;
        while (!end) {
            end = table.playThePoker();
        }
//        List<Card> cards = List.of(
//                new Ace(CardColor.SPADES),
//                new Three(CardColor.SPADES),
//                new Two(CardColor.SPADES),
//                new King(CardColor.SPADES),
//                new Queen(CardColor.SPADES));
//        boolean a = HandsCalculator.checkFlush(cards);
//        List<Card> cardsNoFull = List.of(
//                new Ace(CardColor.SPADES),
//                new Two(CardColor.SPADES),
//                new Two(CardColor.DIAMONDS),
//                new King(CardColor.CLUBS),
//                new Ace(CardColor.DIAMONDS));
//        boolean b = HandsCalculator.checkFlush(cardsNoFull);
//
//        System.out.println("Test for cards with full = " + a);
//        System.out.println("Test for cards with NO full = " + b);
//        List<Integer> numbers = new ArrayList<>();
//        numbers.add(5);
//        numbers.add(6);
//        numbers.add(5);
//        numbers.add(8);
//        numbers.add(6);
//
//        Set<Integer> uniqueNumbers = new HashSet<>();
//        Set<Integer> duplicates = new HashSet<>();
//
//        for (Integer number : numbers) {
//            if (!uniqueNumbers.contains(number)) {
//                uniqueNumbers.add(number);
//            } else {
//                duplicates.add(number);
//            }
//        }
//
//        uniqueNumbers.removeAll(duplicates);
//
//        System.out.println("Lista przed usunięciem duplikatów i oryginałów: " + numbers);
//        System.out.println("Unikalne elementy: " + uniqueNumbers);
//
//        System.out.println("PLAYERS SORTING");
//        List<Player> players = new ArrayList<>();
//        Player player1 = new Player("Alice", 10);
//        Player player2 = new Player("Beatrice", 10);
//        Player player3 = new Player("Celine", 10);
//        Player player4 = new Player("Dundee", 10);
//        Hand hand1 = Hand.STRAIGHT;
//        Hand hand2 = Hand.FULL_HOUSE;
//        Hand hand3 = Hand.FLUSH;
//        Hand hand4 = Hand.STRAIGHT_FLUSH;
//        player1.setStrongestHandMark(hand1);
//        player2.setStrongestHandMark(hand2);
//        player3.setStrongestHandMark(hand3);
//        player4.setStrongestHandMark(hand4);
//        players.add(player1);
//        players.add(player2);
//        players.add(player3);
//        players.add(player4);
//        Player singleGameWinner = HandsCalculator.getSingleGameWinner(players);
//        System.out.println("the winner ist: " + singleGameWinner + " with score: " + singleGameWinner.getStrongestHandMark().getSingleGameStrength());
    }
}
