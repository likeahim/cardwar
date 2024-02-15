package com.likeahim.cardwar.logic;

import com.likeahim.cardwar.cards.Card;

import java.util.*;

public class Table {
    private int numberOfPlayers;
    private Player playerOne;
    private Player playerTwo;
    private Deck deck;

    private Player winner;
    private List<Card> cardsAtStake = new ArrayList<>();
    private int warIndex = 1;
    private int battleIndex = 1;

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }


    public Player getPlayerOne() {
        return playerOne;
    }

    public void setPlayerOne(Player playerOne) {
        this.playerOne = playerOne;
    }

    public Player getPlayerTwo() {
        return playerTwo;
    }

    public void setPlayerTwo(Player playerTwo) {
        this.playerTwo = playerTwo;
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public Table(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;

    }

    public void shuffleAndDealCards(Player playerOne, Player playerTwo) {
        setPlayerOne(playerOne);
        setPlayerTwo(playerTwo);
        deck = new Deck();
        List<Card> cards = deck.getGameDeck();
        Collections.shuffle(cards);
        for (int i = 0; i < deck.getNumberOfCards(); i+=2) {
            playerOne.getCardsInHand().add(cards.get(i));
            playerTwo.getCardsInHand().add(cards.get(i+1));
        }
        playerOne.setNumberOfCards(playerOne.getCardsInHand().size());
        playerTwo.setNumberOfCards(playerTwo.getCardsInHand().size());
    }

    public boolean battle() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter to battle");
        scanner.nextLine();
        Card card1 = playerOne.playTheCard(0);
        playerOne.setCardOnTop(card1);
        playerOne.getCardsInHand().remove(card1);
        Card card2 = playerTwo.playTheCard(0);
        playerTwo.setCardOnTop(card2);
        playerTwo.getCardsInHand().remove(card2);
        cardsAtStake.add(playerOne.getCardOnTop());
        cardsAtStake.add(playerTwo.getCardOnTop());
        showCardOnTop(card1, playerOne);
        showCardOnTop(card2, playerTwo);
        if(card1.getStrength() == card2.getStrength()) {
            warInfo();
            cardsSupply(playerOne);
            cardsSupply(playerTwo);
            warIndex++;
            return false;
        } else if (card1.getStrength() > card2.getStrength()) {
            showWinner(playerOne);
            updateCards(playerOne);
        } else {
            showWinner(playerTwo);
            updateCards(playerTwo);
        }
        cardsSupply(playerOne);
        cardsSupply(playerTwo);
        System.out.println("    " + playerOne + " cards score " + playerOne.getNumberOfCards());
        System.out.println("    " + playerTwo + " cards score " + playerTwo.getNumberOfCards());
        if(playerOne.getNumberOfCards() == 0)
            setWinner(playerTwo);
        if(playerTwo.getNumberOfCards() == 0)
            setWinner(playerOne);
        battleIndex++;
        return true;
    }

    private void showCardOnTop(Card card1, Player player) {
        System.out.println(player.getName() + " plays " + card1);
    }

    private void cardsSupply(Player player) {
        if(player.getCardsInHand().size() == 0 && player.getCardsWon().size() != 0) {
            Collections.shuffle(player.getCardsWon());
            player.setCardsInHand(player.getCardsWon());
            player.clearCardsWon();
        }
        player.setNumberOfCards(player.getCardsInHand().size() + player.getCardsWon().size());
    }

    private void updateCards(Player playerWon) {
        for (Card card : cardsAtStake) {
            playerWon.addCardsWon(card);
        }
        cardsAtStake = new ArrayList<>();

    }

    private void showWinner(Player player) {
        System.out.println("-> " + player + " won this battle (" + battleIndex + ")");
    }

    private void warInfo() {
        System.out.println("WAR! (" + warIndex + ")");
    }
}

