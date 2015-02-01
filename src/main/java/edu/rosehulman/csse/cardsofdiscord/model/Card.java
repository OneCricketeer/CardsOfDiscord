package edu.rosehulman.csse.cardsofdiscord.model;

public class Card {

	private int mId;
	private boolean mIsBlack;
	private boolean mIsMature;
	private String mContent;
	
	public Card(int id, boolean isBlack, boolean isMature, String content){
		this.mContent = content;
		this.mId = id;
		this.mIsBlack = isBlack;
		this.mIsMature = isMature;		
	}
	
	public int getId() {
		return mId;
	}

	public boolean isBlack() {
		return mIsBlack;
	}

	public boolean isMature() {
		return mIsMature;
	}

	public String getContent() {
		return mContent;
	}
}
