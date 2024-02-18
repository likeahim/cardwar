package com.likeahim.cardwar.cards;

public interface Card extends Comparable<Card>{
    CardColor getColor();
    int getStrength();
    char getInitial();
}

