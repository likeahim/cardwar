package com.likeahim.cardwar.cards;

import java.util.Objects;

public class King implements Card, Comparable<Card> {

    private final int strength = 12;
    private final char initial = 'K';
    private CardColor color;

    public King(CardColor color) {
        this.color = color;
    }

    @Override
    public CardColor getColor() {
        return this.color;
    }

    @Override
    public int getStrength() {
        return strength;
    }

    @Override
    public char getInitial() {
        return initial;
    }

    @Override
    public String toString() {
        String display = "";
        display += getInitial() + color.toString();
        return display;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        King king = (King) o;
        return strength == king.strength;
    }

    @Override
    public int hashCode() {
        return Objects.hash(strength);
    }

    @Override
    public int compareTo(Card card) {
        return Integer.compare(this.getStrength(), card.getStrength());
    }
}

