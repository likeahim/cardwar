package com.likeahim.cardwar.cards;

public class Nine implements Card {

    private final int strength = 8;
    private final char initial = '9';

    private CardColor color;

    public Nine(CardColor color) {
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
        return Integer.compare(card.getStrength(), this.getStrength());
    }
}