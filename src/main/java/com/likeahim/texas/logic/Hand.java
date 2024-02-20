package com.likeahim.texas.logic;

public enum Hand {
    STRAIGHT_FLUSH(100000, "straight flush"),
    FOUR_OF_A_KIND(10000, "four of a kind"),
    FULL_HOUSE(5000, "full house"),
    FLUSH(1200, "flush"),
    STRAIGHT(450, "straight"),
    THREE_OF_A_KIND(120, "three of a kind"),
    TWO_PAIRS(52, "two pairs"),
    ONE_PAIR(5, "one pair"),
    HIGH_CARD(1, "high card");
    ;

    private final int power;
    private final String name;
    private int singleGameStrength;

    Hand(int power, String name) {
        this.power = power;
        this.name = name;
    }

    public double getPower() {
        return power;
    }

    public String getName() {
        return name;
    }

    public int getSingleGameStrength() {
        return singleGameStrength;
    }

    public void setSingleGameStrength(int playersCardsValue) {
        this.singleGameStrength = power * playersCardsValue;
    }
}
