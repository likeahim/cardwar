package com.likeahim.cardwar.logic;

import com.likeahim.cardwar.cards.*;

import java.util.*;

public class Deck {
    private final int numberOfCards = 52;
    private List<Card> gameDeck = new LinkedList<>();

    public int getNumberOfCards() {
        return numberOfCards;
    }

    public List<Card> getGameDeck() {
        return gameDeck;
    }

    public void setGameDeck(HashSet<Card> startDeck) {

    }

    public Deck() {
        gameDeck.add(new Ace(CardColor.SPADES));
        gameDeck.add(new Ace(CardColor.HEARTS));
        gameDeck.add(new Ace(CardColor.CLUBS));
        gameDeck.add(new Ace(CardColor.DIAMONDS));
        gameDeck.add(new King(CardColor.SPADES));
        gameDeck.add(new King(CardColor.HEARTS));
        gameDeck.add(new King(CardColor.CLUBS));
        gameDeck.add(new King(CardColor.DIAMONDS));
        gameDeck.add(new Queen(CardColor.SPADES));
        gameDeck.add(new Queen(CardColor.HEARTS));
        gameDeck.add(new Queen(CardColor.CLUBS));
        gameDeck.add(new Queen(CardColor.DIAMONDS));
        gameDeck.add(new Jack(CardColor.SPADES));
        gameDeck.add(new Jack(CardColor.HEARTS));
        gameDeck.add(new Jack(CardColor.CLUBS));
        gameDeck.add(new Jack(CardColor.DIAMONDS));
        gameDeck.add(new Ten(CardColor.SPADES));
        gameDeck.add(new Ten(CardColor.HEARTS));
        gameDeck.add(new Ten(CardColor.CLUBS));
        gameDeck.add(new Ten(CardColor.DIAMONDS));
        gameDeck.add(new Nine(CardColor.SPADES));
        gameDeck.add(new Nine(CardColor.HEARTS));
        gameDeck.add(new Nine(CardColor.CLUBS));
        gameDeck.add(new Nine(CardColor.DIAMONDS));
        gameDeck.add(new Eight(CardColor.SPADES));
        gameDeck.add(new Eight(CardColor.HEARTS));
        gameDeck.add(new Eight(CardColor.CLUBS));
        gameDeck.add(new Eight(CardColor.DIAMONDS));
        gameDeck.add(new Seven(CardColor.SPADES));
        gameDeck.add(new Seven(CardColor.HEARTS));
        gameDeck.add(new Seven(CardColor.CLUBS));
        gameDeck.add(new Seven(CardColor.DIAMONDS));
        gameDeck.add(new Six(CardColor.SPADES));
        gameDeck.add(new Six(CardColor.HEARTS));
        gameDeck.add(new Six(CardColor.CLUBS));
        gameDeck.add(new Six(CardColor.DIAMONDS));
        gameDeck.add(new Five(CardColor.SPADES));
        gameDeck.add(new Five(CardColor.HEARTS));
        gameDeck.add(new Five(CardColor.CLUBS));
        gameDeck.add(new Five(CardColor.DIAMONDS));
        gameDeck.add(new Four(CardColor.SPADES));
        gameDeck.add(new Four(CardColor.HEARTS));
        gameDeck.add(new Four(CardColor.CLUBS));
        gameDeck.add(new Four(CardColor.DIAMONDS));
        gameDeck.add(new Three(CardColor.SPADES));
        gameDeck.add(new Three(CardColor.HEARTS));
        gameDeck.add(new Three(CardColor.CLUBS));
        gameDeck.add(new Three(CardColor.DIAMONDS));
        gameDeck.add(new Two(CardColor.SPADES));
        gameDeck.add(new Two(CardColor.HEARTS));
        gameDeck.add(new Two(CardColor.CLUBS));
        gameDeck.add(new Two(CardColor.DIAMONDS));

    }
}

