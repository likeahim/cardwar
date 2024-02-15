package com.likeahim.cardwar.cards;

public class Three implements Card {

    private CardColor color;

    public Three(CardColor color) {
        this.color = color;
    }

    @Override
    public CardColor getColor() {
        return this.color;
    }

    @Override
    public int getStrength() {
        return 2;
    }

    @Override
    public char getInitial() {
        return '3';
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