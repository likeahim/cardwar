package com.likeahim.cardwar.logic;

import com.likeahim.cardwar.cards.Card;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Player {

    private String name;
    private Card cardOnTop;

    private List<Card> cardsInHand = new ArrayList<>();
    private List<Card> cardsWon = new ArrayList<>();
    private int numberOfCards;

    public Player(String name) {
        this.name = name;
    }

    public Card playTheCard(int n) {
        return cardsInHand.get(n);
    }

    public int getNumberOfCards() {
        return numberOfCards;
    }

    public void setNumberOfCards(int numberOfCards) {
        this.numberOfCards = numberOfCards;
    }

    public Card getCardOnTop() {
        return cardOnTop;
    }

    public void setCardOnTop(Card cardOnTop) {
        this.cardOnTop = cardOnTop;
    }

    public List<Card> getCardsInHand() {
        return cardsInHand;
    }

    public void setCardsInHand(List<Card> cardsInHand) {
        this.cardsInHand = cardsInHand;
    }

    public List<Card> getCardsWon() {
        return cardsWon;
    }

    public void addCardsWon(Card card) {
        cardsWon.add(card);
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return numberOfCards == player.numberOfCards && Objects.equals(cardsInHand, player.cardsInHand) && Objects.equals(cardsWon, player.cardsWon);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numberOfCards, cardsInHand, cardsWon);
    }

    @Override
    public String toString() {
        return name;
    }

    public void clearCardsWon() {
        cardsWon = new ArrayList<>();
    }
}
