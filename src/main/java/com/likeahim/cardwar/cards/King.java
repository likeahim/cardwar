package com.likeahim.cardwar.cards;

public class King implements Card {
    private CardColor color;

    public King(CardColor color) {
        this.color = color;
    }

    @Override
    public CardColor getColor() {
        return this.color;
    }

    @Override
    public int getStrength() {
        return 12;
    }

    @Override
    public char getInitial() {
        return 'K';
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

