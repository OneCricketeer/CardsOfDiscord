package edu.rosehulman.csse.cardsofdiscord.model;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {

	private ArrayList<Card> mWhiteCards;
	private ArrayList<Card> mBlackCards;
	
	public Deck(ArrayList<Card> cards){
		mWhiteCards = new ArrayList<Card>();
		mBlackCards = new ArrayList<Card>();
		for(Card c : cards){
			if (c.isBlack()){
				mBlackCards.add(c);
			}else{
				mWhiteCards.add(c);
			}
		}
	}
	
	public Deck(ArrayList<Card> whiteCards, ArrayList<Card> blackCards){
		this.mBlackCards = blackCards;
		this.mWhiteCards = whiteCards;
	}
	
	public ArrayList<Card> getBlackCards() {
		return mBlackCards;
	}
	
	public ArrayList<Card> getWhiteCards() {
		return mWhiteCards;
	}
	
	public Card drawWhiteCard() throws DeckEmptyException{
		if (mWhiteCards.isEmpty()){
			throw new DeckEmptyException();
		}
		return mWhiteCards.remove(0);
	}
	
	public Card drawBlackCard() throws DeckEmptyException{
		if (mBlackCards.isEmpty()){
			throw new DeckEmptyException();
		}
		return mBlackCards.remove(0);
	}
	
	public void shuffle(){
		if (mWhiteCards != null){
			Collections.shuffle(mWhiteCards);
		}
		if (mBlackCards != null){
			Collections.shuffle(mBlackCards);
		}
	}
	
	class DeckEmptyException extends Exception {
		/**
		 * 
		 */
		private static final long serialVersionUID = 480865636274926758L;

		public DeckEmptyException(){
			super();
		}
	}
}
