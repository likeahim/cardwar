package com.likeahim.cardwar.cards;

public class Queen implements Card {
    private CardColor color;

    public Queen(CardColor color) {
        this.color = color;
    }

    @Override
    public CardColor getColor() {
        return this.color;
    }

    @Override
    public int getStrength() {
        return 11;
    }

    @Override
    public char getInitial() {
        return 'Q';
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