package edu.rosehulman.csse.cardsofdiscord.model;

import java.util.ArrayList;
import java.util.Random;

public class TurnManager {

	private int mCurrentPlayerIndex;
	private int mJudgePlayerIndex;
	private ArrayList<Player> mPlayers;
	
	public TurnManager( ArrayList<String> playerNames) {
		mPlayers = new ArrayList<Player>();
		for(String name : playerNames){
			mPlayers.add(new Player(name));
		}
		randomizePlayers();
		
	}

	private void randomizePlayers() {
		Random r = new Random();
		mJudgePlayerIndex = r.nextInt(mPlayers.size());
		mCurrentPlayerIndex = (mJudgePlayerIndex + 1) % mPlayers.size();
	}
	
	public Player getCurrentPlayer(){
		return mPlayers.get(mCurrentPlayerIndex);
	}
	
	public Player getCurrentJudge(){
		return mPlayers.get(mJudgePlayerIndex);
	}
	
	public void rotatePlayers(){
		mCurrentPlayerIndex = (mCurrentPlayerIndex + 1) % mPlayers.size();
	}
	
	public void rotateJudge(){
		mJudgePlayerIndex = (mJudgePlayerIndex + 1) % mPlayers.size(); 
	}

	public ArrayList<Player> getPlayers() {
		return mPlayers;
	}
	
	public void resetPlayers(){
		for(Player p : mPlayers){
			p.reset();
		}
		randomizePlayers();
	}
}
