package com.likeahim.texas.logic;

import com.likeahim.cardwar.cards.Card;
import com.likeahim.cardwar.logic.Deck;
import com.likeahim.texas.ui.UserInput;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.IntStream;

public class PokerTable {
    Deck deck;

    private static double THE_POT;
    private int playersNumber;
    private List<Card> cardsToDeal;
    private static List<Player> playersAtTable = new LinkedList<>();
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

    public static void addPlayer(Player player) {
        playersAtTable.add(player);
    }

    UserInput ui = new UserInput();

    public boolean playThePoker() {
        playersNumber = 3;
//        playersNumber = ui.enterPlayersNumber();
        for (int i = 1; i <= playersNumber; i++) {
//            Player player = ui.enterNamesAndCredits(i);
            Player player = new Player("Mike" + i, 10000 * i);
            playersAtTable.add(player);
        }
        setPlayersOnBlinds();
        setSingleGamePlayers(playersAtTable);
        while (playersNumber > 1) {
            shuffleAndDealCuffsCards();
            setCurrentBet(bigBlind);
            if (actionChoice()) {
                communityCards = dealFlopCards(); //or better as void?
                System.out.println("community cards: " + communityCards);
                refreshPlayersBettingStatus();
            }
            if(actionChoice()) {
                communityCards = dealOneCard();
                System.out.println("community cards: " + communityCards);
            }
            if (actionChoice()) {
                communityCards = dealOneCard();
                System.out.println("community cards: " + communityCards);
            }
            if (actionChoice()) {
                showdown();
//                calculateWinner();
            }

            releaseAndCleanThePot();
            changeBlinds();
            cleanCuffsForAllPlayers();
            showdown();
        }
        return true;
    }

    private void refreshPlayersBettingStatus() {
        for (Player player : SINGLE_GAME_PLAYERS) {
            player.setCheckBet(false);
            player.setRaisedBet(false);
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
        playersAtTable
                .forEach(Player::cleanCuffsCards);
    }

    private boolean actionChoice() {
        while (!bettingFinished())
            for (Player player : playersAtTable) {
                if(!player.isCheckBet())
                    UserInput.choiceAction(player);
            }
//        while(!SINGLE_WINNER && SINGLE_GAME_PLAYER.size() > 1) {
//            playersAtTable.stream()
//                    .filter(player -> player.isPass())
//                    .filter((player -> player.isCheckBet()))
//                    .filter(player -> player.isAllIn())
//                    .forEach(UserInput::choiceAction);
//        }
//        if(SINGLE_WINNER) {
//            List<Player> collect = playersAtTable.stream()
//                    .filter(player -> !player.isPass())
//                    .toList();
//            singleGameWinner = collect.get(0);
//        }
        return singleGameWinner == null;
    }

    private boolean bettingFinished() {
        long count = playersAtTable.stream()
                .filter(player -> player.isCheckBet())
                .count();
        long count1 = playersAtTable.stream()
                .filter(player -> player.isPass())
                .count();
        return count + count1 == playersAtTable.size();

    }

    private void changeBlinds() {
        setDealer(getPlayerOnSmallBlind());
        setPlayerOnSmallBlind(getPlayerOnBigBlind());
        setPlayerOnBigBlind(getPlayerOnBigBlind().getNext());
        SINGLE_GAME_PLAYERS.add(getPlayerOnSmallBlind());
        SINGLE_GAME_PLAYERS.add(getPlayerOnBigBlind());
        getPlayerOnSmallBlind().setCredit(-smallBlind);
        getPlayerOnBigBlind().setCredit(-bigBlind);
    }

    private void setPlayersOnBlinds() {
        setPlayersOrder();
        Player player = playersAtTable.get(playersAtTable.size() - 1);
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
    }

    private static void setPlayersOrder() {
        for (int i = 0; i < playersAtTable.size(); i++) {
            try {
                playersAtTable.get(i).setPrev(playersAtTable.get(i - 1));
            } catch (Exception e) {
                playersAtTable.get(i).setPrev(playersAtTable.get(playersAtTable.size() - 1));
            }
            try {
                playersAtTable.get(i).setNext(playersAtTable.get(i + 1));
            } catch (Exception e) {
                playersAtTable.get(i).setNext(playersAtTable.get(0));
            }
        }
    }

    private void shuffleAndDealCuffsCards() {
        deck = new Deck();
        cardsToDeal = deck.getGameDeck();
        Collections.shuffle(cardsToDeal);
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < playersNumber; j++) {
                playersAtTable.get(j).addCuffsCard(cardsToDeal.get(0));
                cardsToDeal.remove(0);
            }
        }
    }

    public static double getCurrentBet() {
        return CURRENT_BET;
    }

    public static void setCurrentBet(double currentBet) {
        List<Double> list = playersAtTable.stream()
                .filter(player -> player.isAllIn())
                .map(p -> p.getAmountBetAlready())
                .toList();
        if (list.isEmpty())
            CURRENT_BET = currentBet;
        else {
            OptionalInt min = IntStream.range(0, list.size())
                    .min();
            double newCurrentBet = min.getAsInt();
            CURRENT_BET = newCurrentBet;
        }
    }

    public int getPlayersNumber() {
        return playersNumber;
    }

    public void setPlayersNumber(int playersNumber) {
        this.playersNumber = playersNumber;
    }

    public static List<Player> getPlayersAtTable() {
        return playersAtTable;
    }

    public static void setPlayersAtTable(List<Player> playersAtTable) {
        PokerTable.playersAtTable = playersAtTable;
    }

    public static List<Player> getSingleGamePlayers() {
        return SINGLE_GAME_PLAYERS;
    }

    public static void setSingleGamePlayers(List<Player> singleGamePlayers) {
        SINGLE_GAME_PLAYERS = singleGamePlayers;
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
