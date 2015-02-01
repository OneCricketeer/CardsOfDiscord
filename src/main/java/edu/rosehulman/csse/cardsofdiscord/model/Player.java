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
    	hand.add(card);
    }
    
    public void playCard(Card card){
    	hand.remove(card);
    }
    
}
