package com.likeahim.texas.logic;

import com.likeahim.cardwar.cards.Card;
import com.likeahim.texas.ui.UserInput;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Player {
    private final String name;
    private double credit;

    private double amountBetAlready;

    private Player next;

    private Player prev;
    private boolean pass = false;
    private boolean allIn = false;
    private boolean checkBet = false;
    private boolean raisedBet = false;
    private List<Card> cuffsCards = new ArrayList<>();
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
//        PokerTable.getSingleGamePlayers().remove(this);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Double.compare(credit, player.credit) == 0 && Objects.equals(name, player.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, credit);
    }

    @Override
    public String toString() {
        if(!cuffsCards.isEmpty())
            return "player " + name + ", credit balance: " + credit + " " + cuffsCards + " || already bet: " + amountBetAlready;
        else
            return "player " + name + ", credit balance: " + credit;
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
        credit -= raiseAmount;
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
}
