package com.likeahim.texas.logic;

import com.likeahim.cardwar.cards.Card;
import com.likeahim.texas.ui.UserInput;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class Player implements Comparator<Player>, Comparable<Player> {
    private final String name;
    private double credit;
    private int startingNumber;

    private double amountBetAlready;

    private Player next;

    private Player prev;
    private boolean pass = false;
    private boolean allIn = false;
    private boolean checkBet = false;
    private boolean raisedBet = false;
    private List<Card> strongestHandList = new ArrayList<>();
    private List<Card> cuffsCards = new ArrayList<>();
    private Hand strongestHandMark;
    private int powerInHand;

    public int getStartingNumber() {
        return startingNumber;
    }

    public void setStartingNumber(int startingNumber) {
        this.startingNumber = startingNumber;
    }
    public void setStartingNrForNext() {
        if(getNext().getStartingNumber() != 1)
            this.getNext().setStartingNumber(this.startingNumber + 1);
    }

    public List<Card> getCuffsCards() {
        return cuffsCards;
    }

    public void addCuffsCard(Card card) {
        cuffsCards.add(card);
    }

    public void cleanCuffsCards() {
        cuffsCards = new ArrayList<>();
    }

    public Player(String name, double credit) {
        this.name = name;
        this.credit = credit;
    }

    public Hand getStrongestHandMark() {
        return strongestHandMark;
    }

    public void setStrongestHandMark(Hand strongestHandMark) {
        this.strongestHandMark = strongestHandMark;
    }

    public List<Card> getStrongestHandList() {
        return strongestHandList;
    }

    public void setStrongestHandList(List<Card> strongestHandList) {
        this.strongestHandList = new ArrayList<>(strongestHandList);
    }

    public String getName() {
        return name;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit += credit;
    }

    public Player getNext() {
        return next;
    }

    public void setNext(Player next) {
        this.next = next;
    }

    public Player getPrev() {
        return prev;
    }

    public void setPrev(Player prev) {
        this.prev = prev;
    }

    public boolean isPass() {
        return pass;
    }

    public void setPass(boolean pass) {
        this.pass = pass;
        PokerTable.getPlayerOutOfRound().add(this);
        PokerTable.getSingleGamePlayers().remove(this);
    }

    public boolean isAllIn() {
        return allIn;
    }

    public void setAllIn(boolean allIn) {
        this.allIn = allIn;
    }

    public boolean isCheckBet() {
        return checkBet;
    }

    public void setCheckBet(boolean checkBet) {
        this.checkBet = checkBet;
    }

    public boolean isRaisedBet() {
        return raisedBet;
    }

    public void setRaisedBet(boolean raisedBet) {
        this.raisedBet = raisedBet;
    }

    public double getAmountBetAlready() {
        return amountBetAlready;
    }

    public void setAmountBetAlready(double amountBetAlready) {
        this.amountBetAlready += amountBetAlready;
    }

    public void checkTheBet() {
        if (amountBetAlready < PokerTable.getCurrentBet()) {
            double toCheck = PokerTable.getCurrentBet() - amountBetAlready;
            setAmountBetAlready(toCheck);
            setCheckBet(true);
            credit -= toCheck;
        } else
            setCheckBet(true);
        if (!PokerTable.getSingleGamePlayers().contains(this))
            PokerTable.getSingleGamePlayers().add(this);
        setCheckBet(true);
    }

    public void raiseTheBet() {
        double raiseAmount = UserInput.putRaiseAmount();
        double toCheck = PokerTable.getCurrentBet() - amountBetAlready;
        setAmountBetAlready(raiseAmount + toCheck);
        PokerTable.setCurrentBet(raiseAmount);
        setCheckBet(true);
        PokerTable.getPlayersAtTable().stream()
                .filter(player -> player.getAmountBetAlready() < PokerTable.getCurrentBet())
                .forEach(player -> player.setCheckBet(false));
        credit -= (raiseAmount - toCheck);
        if (!PokerTable.getSingleGamePlayers().contains(this)) {
            PokerTable.getSingleGamePlayers().add(this);
        }
    }

    public void betAllIn() {
        setAllIn(true);
        setAmountBetAlready(credit);
        credit = 0;
        PokerTable.setCurrentBet(credit);
        if (!PokerTable.getSingleGamePlayers().contains(this))
            PokerTable.getSingleGamePlayers().add(this);
    }



    @Override
    public String toString() {
        if (!cuffsCards.isEmpty())
            return "player " + name + ", credit balance: " + credit + " " + cuffsCards + " || already bet: " + amountBetAlready;
        else
            return "player " + name + ", credit balance: " + credit;
    }
    //method helps sort players by strongest hand
    @Override
    public int compare(Player p1, Player p2) {
        return -Integer.compare(p1.getStrongestHandMark().getPower(), p2.getStrongestHandMark().getPower());
    }


    @Override
    public int compareTo(Player player) {
        return Integer.compare(this.startingNumber, player.startingNumber);
    }
}
