package com.likeahim.cardwar.cards;

public class Eight implements Card {

    private CardColor color;

    public Eight(CardColor color) {
        this.color = color;
    }

    @Override
    public CardColor getColor() {
        return this.color;
    }

    @Override
    public int getStrength() {
        return 7;
    }

    @Override
    public char getInitial() {
        return '8';
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