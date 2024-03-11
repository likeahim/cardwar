package com.likeahim.texas.logic;

import com.likeahim.cardwar.cards.Card;
import com.likeahim.cardwar.logic.Deck;
import com.likeahim.texas.ui.UserInput;

import java.util.*;

public class PokerTable {
    Deck deck;

    private static double THE_POT;
    private int playersNumber;
    private List<Card> cardsToDeal;
    private static List<Player> PLAYERS_AT_TABLE = new LinkedList<>();
    private static List<Card> communityCards = new LinkedList<>();
    private static double smallBlind = 20;
    private static double bigBlind = smallBlind * 2;
    private static double CURRENT_BET;
    private Player dealer;
    private Player playerOnSmallBlind;
    private Player playerOnBigBlind;
    private Player singleGameWinner;
    private static boolean SINGLE_WINNER = false;

    private static Player winner;
    private static List<Player> SINGLE_GAME_PLAYERS = new LinkedList<>();
    private static List<Player> PLAYER_OUT_OF_GAME = new LinkedList<>();

    public static void addPlayer(Player player) {
        PLAYERS_AT_TABLE.add(player);
    }

    UserInput ui = new UserInput();

    public boolean playThePoker() {
        playersNumber = 3;
//        playersNumber = ui.enterPlayersNumber();
        for (int i = 1; i <= playersNumber; i++) {
//            Player player = ui.enterNamesAndCredits(i);
            Player player = new Player("Mike" + i, 10000 * i);
            PLAYERS_AT_TABLE.add(player);
        }
        setPlayersOnBlinds();
        while (playersNumber > 1) {
            setSingleGamePlayers(new LinkedList<>(PLAYERS_AT_TABLE));
            shuffleAndDealCuffsCards();
            setCurrentBet(bigBlind); //CURRENT_BET already defined
            if (actionChoice()) {
                communityCards = dealFlopCards(); //or better as void?
                ui.showCommunityCards(communityCards);
                refreshPlayersBettingStatus();
                refreshThePot();
            }
            if (actionChoice()) {
                communityCards = dealOneCard();
                ui.showCommunityCards(communityCards);
                refreshPlayersBettingStatus();
                refreshThePot();
            }
            if (actionChoice()) {
                communityCards = dealOneCard();
                ui.showCommunityCards(communityCards);
                refreshPlayersBettingStatus();
                refreshThePot();
            }
            if (actionChoice()) {
                showdown();
                refreshThePot();
            }
            singleGameWinner = HandsCalculator.getSingleGameWinner(SINGLE_GAME_PLAYERS);
            releaseAndCleanThePot();
            if (checkIfThereIsTheWinner()) {
                return true;
            }
            setNextAndPrevForRemainingPlayers();
            //does not work correctly
            changeBlinds();
            resetTableCast();
            cleanCuffsForAllPlayers();
            cleanCommunityCards();
            resetAmountBetAlreadyForAll();
            CURRENT_BET = 0;
            ui.showSingleGameWinner(singleGameWinner);
            singleGameWinner = null;
            refreshPlayersBettingStatus();
            refreshPlayersPassAndAllInAction();
            setRoundStartingNumbers(PLAYERS_AT_TABLE);
        }
        return true;
    }

    private void refreshThePot() {
        THE_POT = 0;
        for (int i = 0; i < PLAYERS_AT_TABLE.size(); i++) {
            THE_POT += PLAYERS_AT_TABLE.get(i).getAmountBetAlready();
        }
    }

    private boolean checkIfThereIsTheWinner() {
        int counter = 0;
        for (Player player : PLAYERS_AT_TABLE) {
            if (player.getCredit() > 0)
                counter++;
        }
        return counter == 1;
    }

    private static void resetTableCast() {
        List<Player> list = PLAYERS_AT_TABLE.stream()
                .filter(player -> player.getCredit() > 0)
                .toList();
        PLAYERS_AT_TABLE = new LinkedList<>(list);
    }

    private static void setNextAndPrevForRemainingPlayers() {
        for (int i = 0; i < PLAYERS_AT_TABLE.size(); i++) {
            if (PLAYERS_AT_TABLE.get(i).getCredit() == 0) {
                PLAYERS_AT_TABLE.get(i).getPrev().setNext(PLAYERS_AT_TABLE.get(i).getNext());
                PLAYERS_AT_TABLE.get(i).getNext().setPrev(PLAYERS_AT_TABLE.get(i).getPrev());
            }
        }
    }

    private void changeBlinds() {

        if (PLAYERS_AT_TABLE.size() > 2) {
            for (int i = 0; i < PLAYERS_AT_TABLE.size(); i++) {
                Player tempPlayer = PLAYERS_AT_TABLE.get(i);
                Player nextPlayer = tempPlayer.getNext();
                if (nextPlayer.getCredit() != 0) {
                    if (tempPlayer.equals(playerOnSmallBlind))
                        setPlayerOnSmallBlind(nextPlayer);
                    if (tempPlayer.equals(playerOnBigBlind))
                        setPlayerOnBigBlind(nextPlayer);
                    if (tempPlayer.equals(dealer))
                        setDealer(nextPlayer);
                }
            }
        } else {
            Player playerOne = PLAYERS_AT_TABLE.get(0);
            Player playerTwo = PLAYERS_AT_TABLE.get(1);
            playerOnSmallBlind = playerOnSmallBlind.equals(playerOne) ? playerTwo : playerOne;
            playerOnBigBlind = playerOnBigBlind.equals(playerOne) ? playerTwo : playerOne;
        }

    }


    private void cleanCommunityCards() {
        communityCards = new LinkedList<>();
    }

    private void refreshPlayersBettingStatus() {
        for (Player player : PLAYERS_AT_TABLE) {
            player.setCheckBet(false);
            player.setRaisedBet(false);
        }
    }

    private void refreshPlayersPassAndAllInAction() {
        for (Player player : PLAYERS_AT_TABLE) {
            player.setPass(false);
            player.setAllIn(false);
        }
    }

    private void showdown() {
        SINGLE_GAME_PLAYERS.stream()
                .forEach(System.out::println);
    }

    private List<Card> dealOneCard() {
        communityCards.add(cardsToDeal.get(0));
        cardsToDeal.remove(0);
        return communityCards;
    }

    private List<Card> dealFlopCards() {
        cardsToDeal.remove(0);
        for (int i = 0; i < 3; i++) {
            communityCards.add(cardsToDeal.get(0));
            cardsToDeal.remove(0);
        }
        return communityCards;
    }

    private void cleanCuffsForAllPlayers() {
        PLAYERS_AT_TABLE
                .forEach(Player::cleanCuffsCards);
    }

    private void resetAmountBetAlreadyForAll() {
        PLAYERS_AT_TABLE
                .forEach(Player::resetAmountBetAlready);
    }

    public static List<Card> getCommunityCards() {
        return communityCards;
    }

    private boolean actionChoice() {
        while (!bettingFinished())
            for (int i = 0; i < SINGLE_GAME_PLAYERS.size(); i++) {
                Player player = SINGLE_GAME_PLAYERS.get(i);
                if (!player.isCheckBet() && !player.isPass())
                    if (!player.isAllIn())
                        UserInput.choiceAction(player);
                    else
                        continue;
            }

        return SINGLE_GAME_PLAYERS.size() > 1;
    }
    /*check if all players already resigned or checked the bet*/

    private boolean bettingFinished() {
        long count = SINGLE_GAME_PLAYERS.stream()
                .filter(player -> player.isCheckBet())
                .count();
        long count1 = SINGLE_GAME_PLAYERS.stream()
                .filter(player -> player.isPass())
                .count();
        long count3 = SINGLE_GAME_PLAYERS.stream()
                .filter(player -> player.isAllIn())
                .count();
        return count + count1 + count3 == SINGLE_GAME_PLAYERS.size();

    }

    private void setRoundStartingNumbers(List<Player> playersAtTable) {
        for (Player player : playersAtTable) {
            player.setStartingNumber(20);
        }
        playerOnSmallBlind.setStartingNumber(1);
        for (Player player : PLAYERS_AT_TABLE) {
            player.setStartingNrForNext();
        }
        PLAYERS_AT_TABLE.sort(Player::compareTo);
    }

    private void setPlayersOnBlinds() {
        setPlayersOrder();
        if (PLAYERS_AT_TABLE.size() > 2) {
            Player player = PLAYERS_AT_TABLE.get(PLAYERS_AT_TABLE.size() - 1);
            Player smallBlindPlayer = player.getNext();
            Player bigBlindPlayer = smallBlindPlayer.getNext();
            setDealer(player);
            setPlayerOnSmallBlind(smallBlindPlayer);
            smallBlindPlayer.setAmountBetAlready(smallBlind);
            smallBlindPlayer.setCredit(-smallBlind);
            setPlayerOnBigBlind(bigBlindPlayer);
            bigBlindPlayer.setAmountBetAlready(bigBlind);
            bigBlindPlayer.setCredit(-bigBlind);
            SINGLE_GAME_PLAYERS.add(smallBlindPlayer);
            SINGLE_GAME_PLAYERS.add(bigBlindPlayer);
        } else {
            setPlayerOnSmallBlind(PLAYERS_AT_TABLE.get(0));
            setPlayerOnBigBlind(PLAYERS_AT_TABLE.get(1));
        }
    }

    private static void setPlayersOrder() {
        for (int i = 0; i < PLAYERS_AT_TABLE.size(); i++) {
            try {
                PLAYERS_AT_TABLE.get(i).setPrev(PLAYERS_AT_TABLE.get(i - 1));
            } catch (Exception e) {
                PLAYERS_AT_TABLE.get(i).setPrev(PLAYERS_AT_TABLE.get(PLAYERS_AT_TABLE.size() - 1));
            }
            try {
                PLAYERS_AT_TABLE.get(i).setNext(PLAYERS_AT_TABLE.get(i + 1));
            } catch (Exception e) {
                PLAYERS_AT_TABLE.get(i).setNext(PLAYERS_AT_TABLE.get(0));
            }
        }
    }

    private void shuffleAndDealCuffsCards() {
        deck = new Deck();
        cardsToDeal = deck.getGameDeck();
        Collections.shuffle(cardsToDeal);
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < PLAYERS_AT_TABLE.size(); j++) {
                PLAYERS_AT_TABLE.get(j).addCuffsCard(cardsToDeal.get(0));
                cardsToDeal.remove(0);
            }
        }
    }

    public static double getCurrentBet() {
        return CURRENT_BET;
    }

    public static void setCurrentBet(double currentBet) {
        CURRENT_BET += currentBet;
    }

    public int getPlayersNumber() {
        return playersNumber;
    }

    public void setPlayersNumber(int playersNumber) {
        this.playersNumber = playersNumber;
    }

    public static List<Player> getPlayersAtTable() {
        return PLAYERS_AT_TABLE;
    }

    public static void setPlayersAtTable(List<Player> playersAtTable) {
        PokerTable.PLAYERS_AT_TABLE = playersAtTable;
    }

    public static List<Player> getSingleGamePlayers() {
        return SINGLE_GAME_PLAYERS;
    }

    public static void setSingleGamePlayers(List<Player> singleGamePlayers) {
        SINGLE_GAME_PLAYERS = singleGamePlayers;
    }

    public static List<Player> getPlayerOutOfGame() {
        return PLAYER_OUT_OF_GAME;
    }

    public static void setPlayerOutOfGame(List<Player> playerOutOfGame) {
        PLAYER_OUT_OF_GAME = playerOutOfGame;
    }

    public double getSmallBlind() {
        return smallBlind;
    }

    public void setSmallBlind(double smallBlind) {
        this.smallBlind = smallBlind;
    }

    public double getBigBlind() {
        return bigBlind;
    }

    public void setBigBlind(double bigBlind) {
        this.bigBlind = bigBlind;
    }

    public Player getDealer() {
        return dealer;
    }

    public void setDealer(Player dealer) {
        this.dealer = dealer;
    }

    public Player getPlayerOnSmallBlind() {
        return playerOnSmallBlind;
    }

    public void setPlayerOnSmallBlind(Player playerOnSmallBlind) {
        this.playerOnSmallBlind = playerOnSmallBlind;
    }

    public Player getPlayerOnBigBlind() {
        return playerOnBigBlind;
    }

    public void setPlayerOnBigBlind(Player playerOnBigBlind) {
        this.playerOnBigBlind = playerOnBigBlind;
    }

    public static double getThePot() {
        return THE_POT;
    }

    public static void setThePot(double thePot) {
        THE_POT += thePot;
    }

    private void releaseAndCleanThePot() {
        singleGameWinner.setCredit(THE_POT);
        THE_POT = 0;
    }

    public static Player getWinner() {
        return winner;
    }
}
