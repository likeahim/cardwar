package com.likeahim.texas;

import com.likeahim.cardwar.cards.*;
import com.likeahim.texas.logic.Hands;
import com.likeahim.texas.logic.Player;
import com.likeahim.texas.logic.PokerTable;

import java.util.List;

public class Application {
    public static void main(String[] args) {
        PokerTable table = new PokerTable();
        boolean end = false;
        while (!end) {
            table.playThePoker();
        }
        Player winner = PokerTable.getWinner();
        System.out.println(winner);
        List<Card> cards = List.of(
                new Two(CardColor.CLUBS),
                new Four(CardColor.DIAMONDS),
                new Three(CardColor.DIAMONDS),
                new Six(CardColor.SPADES),
                new Five(CardColor.HEARTS));
        boolean b = Hands.checkPair(cards);
        boolean c = Hands.checkTwoPairs(cards);
        boolean d = Hands.checkThreeOfAKind(cards);
        boolean e = Hands.checkFourOfAKind(cards);
        boolean f = Hands.checkStraight(cards);
        if (b)
            System.out.println("you have a pair");
        else if (c)
            System.out.println("you have two pairs");
        else if (d)
            System.out.println("you have three of a kind");
        else if (e)
            System.out.println("FOUR of a kind");
        else if (f)
            System.out.println("STRAIGHT");
        else
            System.out.println("you have nthing");

        List<Card> list = cards.stream()
                .sorted(Comparable::compareTo)
                .toList();
        for(Card card : list) {
            System.out.print(card + " ");
        }
    }
}
