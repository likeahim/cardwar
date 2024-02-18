package com.likeahim.cardwar.cards;

import java.util.HashMap;
import java.util.Map;

public class MapOfCards {
    Map<Integer, Card> mapOfCards = new HashMap<>();
    public Map<Integer, Card> getMapOfCards() {
        mapOfCards.put(13, new Ace(CardColor.DIAMONDS));
        mapOfCards.put(12, new King(CardColor.DIAMONDS));
        mapOfCards.put(11, new Queen(CardColor.DIAMONDS));
        mapOfCards.put(10, new Jack(CardColor.DIAMONDS));
        mapOfCards.put(9, new Ten(CardColor.DIAMONDS));
        mapOfCards.put(8, new Nine(CardColor.DIAMONDS));
        mapOfCards.put(7, new Eight(CardColor.DIAMONDS));
        mapOfCards.put(6, new Seven(CardColor.DIAMONDS));
        mapOfCards.put(5, new Six(CardColor.DIAMONDS));
        mapOfCards.put(4, new Five(CardColor.DIAMONDS));
        mapOfCards.put(3, new Four(CardColor.DIAMONDS));
        mapOfCards.put(2, new Three(CardColor.DIAMONDS));
        mapOfCards.put(1, new Two(CardColor.DIAMONDS));
        return mapOfCards;
    }
}
