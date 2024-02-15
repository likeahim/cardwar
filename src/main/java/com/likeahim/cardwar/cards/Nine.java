package com.likeahim.cardwar.cards;

public class Nine implements Card {

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
        return 8;
    }

    @Override
    public char getInitial() {
        return '9';
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