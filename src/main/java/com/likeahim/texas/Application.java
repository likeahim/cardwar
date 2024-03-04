package com.likeahim.texas;

import com.likeahim.cardwar.cards.*;
import com.likeahim.texas.logic.Hand;
import com.likeahim.texas.logic.HandsCalculator;
import com.likeahim.texas.logic.Player;
import com.likeahim.texas.logic.PokerTable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Application {
    public static void main(String[] args) {
        PokerTable table = new PokerTable();
        boolean end = false;
        while (!end) {
            end = table.playThePoker();
        }
    }
}
