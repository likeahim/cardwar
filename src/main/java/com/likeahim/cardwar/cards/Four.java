package com.likeahim.cardwar.cards;

public class Four implements Card, Comparable<Card> {

    private final int strength = 3;
    private final char initial = '4';
    private CardColor color;

    public Four(CardColor color) {
        this.color = color;
    }
    @Override
    public CardColor getColor() {
        return this.color;
    }

    @Override
    public int getStrength() {
        return strength;
    }

    @Override
    public char getInitial() {
        return initial;
    }

    @Override
    public String toString() {
        String display = "";
        display += getInitial() + color.toString();
        return display;
    }

    @Override
    public int compareTo(Card card) {
        return Integer.compare(this.getStrength(), card.getStrength());
    }
}