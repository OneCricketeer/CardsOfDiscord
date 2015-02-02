package edu.rosehulman.csse.cardsofdiscord.model;

import java.util.ArrayList;

/**
 * Created by moorejm on 1/25/2015.
 */
public class Player {
    private String name;
    private int score;
    private ArrayList<Card> hand;

    public Player() {
    	hand = new ArrayList<Card>();
    }

    public Player(String name) {
    	this();
        this.name = name;
    }
    
    public String getName() {
		return name;
	}
    
    public int getScore() {
		return score;
	}

    public ArrayList<Card> getHand() {
    	return hand;
    }
    
    public void incrementScore(){
    	score++;
    }
    
    public void addCard(Card card){
    	hand.add(0, card);
    }
    
    public void playCard(Card card){
    	hand.remove(card);
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((hand == null) ? 0 : hand.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + score;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		if (hand == null) {
			if (other.hand != null)
				return false;
		} else if (!hand.equals(other.hand))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (score != other.score)
			return false;
		return true;
	}
    
}
