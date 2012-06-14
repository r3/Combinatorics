package assignment9;

public class Card {
	private Suit suit;
	private int rank;

	/*
	 * This is just a playing card
	 * There are many like it,
	 * but this one is mine.
	 * 
	 * It has a suit (from the Suit enum) and
	 * an integer rank. Nothing interesting.
	 */
	public Card (Suit suit, int rank) {
		this.setSuit(suit);
		this.setRank(rank);
	}

	public Suit getSuit () {
		return suit;
	}

	public void setSuit (Suit suit) {
		this.suit = suit;
	}

	public int getRank () {
		return rank;
	}

	public void setRank (int rank) {
		this.rank = rank;
	}
	
	public String toString () {
		return String.format("%d", rank);
	}
}