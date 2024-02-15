package com.likeahim.cardwar.cards;

public class Ten implements Card {
    private CardColor color;

    public Ten(CardColor color) {
        this.color = color;
    }

    @Override
    public CardColor getColor() {
        return this.color;
    }

    @Override
    public int getStrength() {
        return 9;
    }

    @Override
    public char getInitial() {
        return 'T';
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