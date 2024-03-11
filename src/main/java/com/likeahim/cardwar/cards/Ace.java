package com.likeahim.cardwar.cards;

import java.util.Comparator;
import java.util.Objects;

public class Ace implements Card, Comparable<Card> {

    private static final int strength = 13;
    private final char initial = 'A';
    private final CardColor color;

    public Ace(CardColor color) {
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
        display += initial + color.toString();
        return display;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ace ace = (Ace) o;
        return strength == ace.strength;
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

