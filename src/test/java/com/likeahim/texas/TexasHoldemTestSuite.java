package com.likeahim.texas;

import com.likeahim.cardwar.cards.*;
import com.likeahim.texas.logic.HandsCalculator;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TexasHoldemTestSuite {

    @Test
    void testCombinationWithoutDuplicates() {
        //Given
        List<Card> cards = List.of(
                new Ace(CardColor.SPADES),
                new Two(CardColor.SPADES),
                new Two(CardColor.DIAMONDS),
                new Three(CardColor.CLUBS),
                new Five(CardColor.HEARTS),
                new Seven(CardColor.HEARTS),
                new Jack(CardColor.DIAMONDS));
        //When
        List<List<Card>> lists = HandsCalculator.generateCombination(cards, 5);
        int size = lists.size();

        //Then
        assertEquals(21, size);
    }

    @Test
    void testOnePairScore() {
        //Given
        List<Card> cards = List.of(
                new Ace(CardColor.DIAMONDS),
                new Two(CardColor.SPADES),
                new Two(CardColor.DIAMONDS),
                new Three(CardColor.CLUBS),
                new Five(CardColor.HEARTS)
        );
        List<Card> cards2 = List.of(
                new Ace(CardColor.DIAMONDS),
                new Ace(CardColor.SPADES),
                new Two(CardColor.SPADES),
                new Three(CardColor.CLUBS),
                new Four(CardColor.HEARTS)
        );
        //When
        int result = HandsCalculator.onePairScore(cards);
        int result2 = HandsCalculator.onePairScore(cards2);

        //Then
        assertEquals(57, result);
        assertEquals(500, result2);
    }

    @Test
    void testTwoPairsScore() {
        //Given
        List<Card> cards1 = List.of(
                new Two(CardColor.DIAMONDS),
                new Two(CardColor.HEARTS),
                new Three(CardColor.HEARTS),
                new Three(CardColor.DIAMONDS),
                new Ace(CardColor.CLUBS)
        );
        List<Card> cards2 = List.of(
                new Ace(CardColor.DIAMONDS),
                new Ace(CardColor.HEARTS),
                new Two(CardColor.HEARTS),
                new Two(CardColor.DIAMONDS),
                new King(CardColor.CLUBS)
        );
        //When
        int result1 = HandsCalculator.twoPairsScore(cards1);
        int result2 = HandsCalculator.twoPairsScore(cards2);
        //Then
        assertEquals(328, result1);
        assertEquals(1988, result2);
    }
}
