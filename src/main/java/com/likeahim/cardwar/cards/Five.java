package com.likeahim.cardwar.cards;

public class Five implements Card {

    private CardColor color;

    public Five(CardColor color) {
        this.color = color;
    }

    @Override
    public CardColor getColor() {
        return this.color;
    }

    @Override
    public int getStrength() {
        return 4;
    }

    @Override
    public char getInitial() {
        return '5';
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