package com.likeahim.cardwar.cards;

public enum CardColor {
    SPADES('\u2660'),
    HEARTS('\u2665'),
    DIAMONDS('\u2666'),
    CLUBS('\u2663');

    private char colorDisplay;

    CardColor(char colorDisplay) {
        this.colorDisplay = colorDisplay;
    }


    @Override
    public String toString() {
        String name = "";
        name += colorDisplay;
        return name;
    }
}

