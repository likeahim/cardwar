package com.likeahim.texas;

import com.likeahim.cardwar.cards.*;
import com.likeahim.texas.logic.Hands;
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
        List<List<Card>> lists = Hands.generateCombination(cards, 5);
        int size = lists.size();

        //Then
        assertEquals(21, size);
    }
}
