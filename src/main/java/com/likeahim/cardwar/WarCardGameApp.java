package com.likeahim.cardwar;


import com.likeahim.cardwar.logic.Player;
import com.likeahim.cardwar.logic.Table;

public class WarCardGameApp {
	public static void main(String[] args) {
		Table table = new Table(2);
		Player playerOne = new Player("Michał");
		Player playerTwo = new Player("Madzia");
		table.shuffleAndDealCards(playerOne, playerTwo);
		while (playerOne.getNumberOfCards() != 0 && playerTwo.getNumberOfCards() != 0) {
			boolean result = false;
			while(!result) {
				result = table.battle();
			}
		}
		System.out.println("Game over, the winner is " + table.getWinner().getName());

	}
}
