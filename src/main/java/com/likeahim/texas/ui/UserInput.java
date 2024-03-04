package com.likeahim.texas.ui;

import com.likeahim.cardwar.cards.Card;
import com.likeahim.texas.logic.Player;
import com.likeahim.texas.logic.PokerTable;

import java.util.List;
import java.util.Scanner;

public class UserInput {

    private static final Scanner SCANNER = new Scanner(System.in);

    public static double putRaiseAmount() {
        System.out.println("Enter raise amount: ");
        double raise = SCANNER.nextDouble();
        SCANNER.nextLine();
        return raise;
    }

    public int enterPlayersNumber() {
        boolean resultOk = false;
        int result = 0;
        while (!resultOk) {
            try {
                System.out.println("Enter number of players");
                result = SCANNER.nextInt();
                SCANNER.nextLine();
                resultOk = true;
            } catch (Exception e) {
                SCANNER.nextLine();
                System.out.println("something went wrong, try again");
            }
            if (result > 8) {
                resultOk = false;
                System.out.println("to many players, maximal 8 places at the table");
            }
        }
        return result;
    }

    public Player enterNamesAndCredits(int i) {
        System.out.println(i + ". player: \n" +
                "enter name...");
        String name = SCANNER.nextLine();
        System.out.println("... and credit:");
        double credit = SCANNER.nextDouble();
        SCANNER.nextLine();
        return new Player(name, credit);
    }

    public static void choiceAction(Player player) {
        currentGameInfo(player);
        int choice = SCANNER.nextInt();
        SCANNER.nextLine();
        switch (choice) {
            case 1 -> player.setPass(true);
            case 2 -> player.checkTheBet();
            case 3 -> player.raiseTheBet();
            case 4 -> player.betAllIn();
        }
    }

    //remove from this class - user input only
    private static void currentGameInfo(Player player) {
        if(!PokerTable.getCommunityCards().isEmpty()) {
            System.out.println(player.getName() + " " + player.getCuffsCards() + "\n" +
                               "credit: " + player.getCredit() +
                               "current bet: " + PokerTable.getCurrentBet() + "\n" +
                               "player bet already: " + player.getAmountBetAlready() +
                               ", community cards: " + PokerTable.getCommunityCards() + "\n" +
                               """
                                       has choice:
                                       1 -> pass
                                       2 -> check
                                       3 -> raise
                                       4 -> all in
                                       """);
            System.out.print("players choice: ");
        } else {
            System.out.println(player.getName() + " " + player.getCuffsCards() + "\n" +
                               "current bet: " + PokerTable.getCurrentBet() + "\n" +
                               "player bet already: " + player.getAmountBetAlready() + "\n" +
                               """
                                       has choice:
                                       1 -> pass
                                       2 -> check
                                       3 -> raise
                                       4 -> all in
                                       """);
        }
    }

    public void showCommunityCards(List<Card> communityCards) {
        System.out.println("community cards: " + communityCards);
    }

    public void showSingleGameWinner(Player singleGameWinner) {
        System.out.println(singleGameWinner + " wins this round");
    }
}

