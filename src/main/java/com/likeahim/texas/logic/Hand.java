package com.likeahim.texas.logic;

import java.util.Comparator;

public enum Hand implements Comparator<Hand> {
    STRAIGHT_FLUSH(9, "straight flush"),
    FOUR_OF_A_KIND(8, "four of a kind"),
    FULL_HOUSE(7, "full house"),
    FLUSH(6, "flush"),
    STRAIGHT(5, "straight"),
    THREE_OF_A_KIND(4, "three of a kind"),
    TWO_PAIRS(3, "two pairs"),
    ONE_PAIR(2, "one pair"),
    HIGH_CARD(1, "high card");
    ;

    private final int power;
    private final String name;
    private int singleGameStrength;

    Hand(int power, String name) {
        this.power = power;
        this.name = name;
    }

    public int getPower() {
        return power;
    }

    public String getName() {
        return name;
    }

    public int getSingleGameStrength() {
        return singleGameStrength;
    }

    public void setSingleGameStrength(int playersCardsValue) {
        this.singleGameStrength = playersCardsValue;
    }

    @Override
    public int compare(Hand h1, Hand h2) {
        return -Integer.compare(h1.getPower(), h2.getPower());
    }
}
