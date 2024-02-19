package com.likeahim.texas;

import com.likeahim.cardwar.cards.*;
import com.likeahim.texas.logic.Hands;
import com.likeahim.texas.logic.Player;
import com.likeahim.texas.logic.PokerTable;

import java.util.List;

public class Application {
    public static void main(String[] args) {
        PokerTable table = new PokerTable();
        boolean end = false;
        while (!end) {
            end = table.playThePoker();
        }
    }
}
