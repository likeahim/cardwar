package com.likeahim.cardwar.cards;

public interface Card {
    CardColor getColor();
    int getStrength();
    char getInitial();
    void setColor(CardColor color);
}

