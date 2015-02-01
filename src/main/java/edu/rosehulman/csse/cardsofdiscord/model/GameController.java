package edu.rosehulman.csse.cardsofdiscord.model;

import java.util.ArrayList;

import edu.rosehulman.csse.cardsofdiscord.model.Deck.DeckEmptyException;

public class GameController {

	private CardManager mCm;
	private TurnManager mTm;
	private boolean isGameOver;

	public GameController() {
		mCm = new CardManager();
	}

	public void newGame(ArrayList<String> names) {
		mTm = new TurnManager(names);
	}

	public Player getCurrentPlayer() {
		return mTm.getCurrentPlayer();
	}
	
	public ArrayList<Card> getCurrentHand() {
		if (mTm.getCurrentJudge().equals(mTm.getCurrentPlayer())){
			return mCm.getJudgeOptions();
		}
		return mTm.getCurrentPlayer().getHand();
	}

	public ArrayList<Player> getPlayers() {
		return mTm.getPlayers();
	}

	public void playCard(Card card) {
		mCm.playCardToJudge(card);

		if (mTm.getCurrentJudge().equals(mTm.getCurrentPlayer())) {
			mTm.rotateJudge();
			try {
				for (Player p : mTm.getPlayers()) {
					if (p.getHand().contains(card)) {
						p.incrementScore();
					}
					p.playCard(card);
					p.addCard(mCm.drawWhiteCard());
				}

				mCm.drawBlackCard();

			} catch (DeckEmptyException e) {
				isGameOver = true;
			}
		}

		mTm.rotatePlayers();
	}

	public boolean isGameOver(){
		return isGameOver;
	}

	public Card getBlackCard() {
		return mCm.getCurrentBlackCard();
	}
}
