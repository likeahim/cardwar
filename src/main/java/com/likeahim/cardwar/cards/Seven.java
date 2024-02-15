package com.likeahim.cardwar.cards;

public class Seven implements Card {

    private CardColor color;

    public Seven(CardColor color) {
        this.color = color;
    }

    @Override
    public CardColor getColor() {
        return this.color;
    }

    @Override
    public int getStrength() {
        return 6;
    }

    @Override
    public char getInitial() {
        return '7';
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