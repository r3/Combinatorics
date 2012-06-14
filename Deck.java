package assignment9;

import java.util.ArrayList;
import java.util.List;

public class Deck {
	private ArrayList<Card> cards = new ArrayList<Card>();
	
	public Deck () {
		for (Suit suit : Suit.values()) {
			for (int rank=1; rank <= 13; rank++) {
				cards.add(new Card(suit, rank));
			}
		}
	}
	
	public List<Card> asList () {
		return cards;
	}
}
