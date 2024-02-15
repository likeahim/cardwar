package com.likeahim.cardwar.cards;

public class Six implements Card {

    private CardColor color;

    public Six(CardColor color) {
        this.color = color;
    }

    @Override
    public CardColor getColor() {
        return this.color;
    }

    @Override
    public int getStrength() {
        return 5;
    }

    @Override
    public char getInitial() {
        return '6';
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