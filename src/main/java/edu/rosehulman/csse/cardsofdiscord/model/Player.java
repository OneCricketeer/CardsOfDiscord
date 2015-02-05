package edu.rosehulman.csse.cardsofdiscord.model;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by moorejm on 1/25/2015.
 */
public class Player implements Parcelable {
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
    
	public Player(Parcel in) {
		readFromParcel(in);
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
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(name);
		dest.writeInt(score);
		dest.writeList(hand);
	}

	@SuppressWarnings("unchecked")
	private void readFromParcel(Parcel in) {
		this.name = in.readString();
		this.score = in.readInt();
		this.hand = (ArrayList<Card>) in.readArrayList(Card.class.getClassLoader());
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
	
	public static final Parcelable.Creator<Player> CREATOR = new Parcelable.Creator<Player>() {

		@Override
		public Player createFromParcel(Parcel in) {
			return new Player(in);
		}

		@Override
		public Player[] newArray(int size) {
			return new Player[size];
		}

	};

	public void reset() {
		this.score = 0;
		this.hand.clear();
	}
    
}
