package com.likeahim.cardwar.cards;

public class Ace implements Card {

    public Ace(CardColor color) {
        this.color = color;
    }

    private CardColor color;

    @Override
    public CardColor getColor() {
        return this.color;
    }

    @Override
    public int getStrength() {
        return 13;
    }

    @Override
    public char getInitial() {
        return 'A';
    }

    @Override
    public void setColor(CardColor color) {
        this.color = color;
    }

    @Override
    public String toString() {
        String display = "";
        display += getInitial() + color.toString();
        return display;
    }
}

