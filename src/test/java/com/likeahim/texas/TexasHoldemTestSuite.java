package com.likeahim.texas;

import com.likeahim.cardwar.cards.*;
import com.likeahim.texas.logic.HandsCalculator;
import com.sun.java.accessibility.util.SwingEventMonitor;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
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
        List<List<Card>> lists = HandsCalculator.generateCombinations(cards, new ArrayList<>(), 5, 0);
        int size = lists.size();

        //Then
        assertEquals(21, size);
    }

    @Test
    void testOnePairScore() {
        //Given
        List<Card> cards = List.of(
                new Ace(CardColor.DIAMONDS),
                new Ace(CardColor.SPADES),
                new King(CardColor.DIAMONDS),
                new Queen(CardColor.CLUBS),
                new Jack(CardColor.HEARTS)
        );
        List<Card> cards2 = List.of(
                new Ace(CardColor.DIAMONDS),
                new Ace(CardColor.SPADES),
                new Two(CardColor.SPADES),
                new Three(CardColor.CLUBS),
                new Four(CardColor.HEARTS)
        );
        //When
        int result = HandsCalculator.calculateOnePairScore(cards);
        int result2 = HandsCalculator.calculateOnePairScore(cards2);

        //Then
        assertEquals(959, result);
        assertEquals(599, result2);
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
        int result1 = HandsCalculator.calculateTwoPairsScore(cards1);
        int result2 = HandsCalculator.calculateTwoPairsScore(cards2);
        //Then
        assertEquals(328, result1);
        assertEquals(1988, result2);
    }

    @Test
    void testcalculateThreeOfAKind() {
        //Given
        List<Card> cards1 = List.of(
                new Two(CardColor.HEARTS),
                new Two(CardColor.SPADES),
                new Two(CardColor.DIAMONDS),
                new Three(CardColor.CLUBS),
                new Four(CardColor.HEARTS)
        );
        List<Card> cards2 = List.of(
                new Ace(CardColor.DIAMONDS),
                new Ace(CardColor.SPADES),
                new Ace(CardColor.HEARTS),
                new King(CardColor.CLUBS),
                new Queen(CardColor.HEARTS)
        );

        //When
        int result1 = HandsCalculator.calculateThreeOfAKindScore(cards1);
        int result2 = HandsCalculator.calculateThreeOfAKindScore(cards2);

        //Then
        assertEquals(105, result1);
        assertEquals(1323, result2);
    }

    @Test
    void testCalculateStraightScore() {
        //Given
        List<Card> cards1 = List.of(
                new Two(CardColor.HEARTS),
                new Three(CardColor.SPADES),
                new Four(CardColor.DIAMONDS),
                new Five(CardColor.CLUBS),
                new Six(CardColor.HEARTS)
        );
        List<Card> cards2 = List.of(
                new King(CardColor.DIAMONDS),
                new Ace(CardColor.SPADES),
                new Queen(CardColor.HEARTS),
                new Jack(CardColor.CLUBS),
                new Ten(CardColor.HEARTS)
        );

        //When
        int result1 = HandsCalculator.calculateStraightScore(cards1);
        int result2 = HandsCalculator.calculateStraightScore(cards2);

        //Then
        assertEquals(15, result1);
        assertEquals(55, result2);
    }

    @Test
    void testCalculateFourOfAKindScore() {
        //Given
        List<Card> cards1 = List.of(
                new Two(CardColor.HEARTS),
                new Two(CardColor.SPADES),
                new Two(CardColor.DIAMONDS),
                new Two(CardColor.CLUBS),
                new Seven(CardColor.HEARTS)
        );
        List<Card> cards2 = List.of(
                new King(CardColor.DIAMONDS),
                new King(CardColor.SPADES),
                new King(CardColor.HEARTS),
                new King(CardColor.CLUBS),
                new Ten(CardColor.HEARTS)
        );

        //When
        int result1 = HandsCalculator.calculateFourOfAKindScore(cards1);
        int result2 = HandsCalculator.calculateFourOfAKindScore(cards2);

        //Then
        assertEquals(14, result1);
        assertEquals(105, result2);
    }

    @Test
    void testCalculateFullHouseScore() {
        //Given
        List<Card> cards1 = List.of(
                new Two(CardColor.HEARTS),
                new Two(CardColor.SPADES),
                new Two(CardColor.DIAMONDS),
                new Seven(CardColor.CLUBS),
                new Seven(CardColor.HEARTS)
        );
        List<Card> cards2 = List.of(
                new Ace(CardColor.DIAMONDS),
                new Ace(CardColor.SPADES),
                new Ace(CardColor.HEARTS),
                new King(CardColor.CLUBS),
                new King(CardColor.HEARTS)
        );

        //When
        int result1 = HandsCalculator.calculateFullHouseScore(cards1);
        int result2 = HandsCalculator.calculateFullHouseScore(cards2);

        //Then
        assertEquals(163, result1);
        assertEquals(1987, result2);
    }

    @Test
    void testHighCardScore() {
        //Given
        List<Card> cards1 = List.of(
                new Three(CardColor.HEARTS),
                new Two(CardColor.SPADES),
                new Five(CardColor.DIAMONDS),
                new Seven(CardColor.CLUBS),
                new Nine(CardColor.HEARTS)
        );
        List<Card> cards2 = List.of(
                new Ace(CardColor.DIAMONDS),
                new King(CardColor.SPADES),
                new Three(CardColor.HEARTS),
                new Jack(CardColor.CLUBS),
                new Seven(CardColor.HEARTS)
        );

        //When
        int result1 = HandsCalculator.calculateHighCard(cards1);
        int result2 = HandsCalculator.calculateHighCard(cards2);

        //Then
        assertEquals(249, result1);
        assertEquals(501, result2);
    }
}
