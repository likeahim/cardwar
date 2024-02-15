package com.likeahim.cardwar.cards;

public class Jack implements Card {
    private CardColor color;

    public Jack(CardColor color) {
        this.color = color;
    }

    @Override
    public CardColor getColor() {
        return this.color;
    }

    @Override
    public int getStrength() {
        return 10;
    }

    @Override
    public char getInitial() {
        return 'J';
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