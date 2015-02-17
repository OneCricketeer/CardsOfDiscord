package edu.rosehulman.csse.cardsofdiscord.model;

import java.util.ArrayList;

import edu.rosehulman.csse.cardsofdiscord.model.Deck.DeckEmptyException;

public class GameController {

	private static final int WINNING_SCORE = 7;
	private CardManager mCm;
	private TurnManager mTm;
	private boolean isGameOver;

	public GameController() {
		mCm = new CardManager();
	}

	public void newGame(ArrayList<String> names) {
		mTm = new TurnManager(names);
		mCm.reset();
		dealCards();
	}

	private void dealCards() {
		try {
			for (Player p : mTm.getPlayers()) {
				p.getHand().clear();
				for (int i = 0; i < CardManager.HAND_SIZE; i++) {
					p.addCard(mCm.drawWhiteCard());
				}
			}
			mCm.drawBlackCard();
		} catch (DeckEmptyException e) {
			isGameOver = true;
		}
	}

	public Player getCurrentPlayer() {
		return mTm.getCurrentPlayer();
	}
	
	public boolean isJudging() {
		return mTm.getCurrentJudge().equals(getCurrentPlayer());
	}
	
	public boolean isJudging(Player p) {
		return mTm.getCurrentJudge().equals(p);
	}

	public ArrayList<Card> getCurrentHand() {
		if (mTm.getCurrentJudge().equals(mTm.getCurrentPlayer())) {
			return mCm.getCombinedJudgeOptions();
		}
		return mTm.getCurrentPlayer().getHand();
	}

	public ArrayList<Player> getPlayers() {
		return mTm.getPlayers();
	}

	public void playCard(Card card) {
		mCm.playCardToJudge(card);

		if (mTm.getCurrentJudge().equals(mTm.getCurrentPlayer())) {
			try {
				for (Player p : mTm.getPlayers()) {
					if (!mTm.getCurrentJudge().equals(p)) {
						for (Card c : mCm.getJudgeOptions()) {
							if (p.getHand().contains(c)) {
								if (card.equals(c)) {
									p.incrementScore();
								}
								p.playCard(c);
								p.addCard(mCm.drawWhiteCard());
							}
						}
					}
					isGameOver = isGameOver || p.getScore() >= WINNING_SCORE;
				}

				mCm.drawBlackCard();

			} catch (DeckEmptyException e) {
				isGameOver = true;
			}
			mTm.rotateJudge();
			mCm.clearJudgeOptions();
			mTm.rotatePlayers();
		}

        if (mCm.getJudgeOptions().size() % mCm.getCurrentBlackCard().getNumPicks() == 0) {
		    mTm.rotatePlayers();
        }
	}

	public boolean isGameOver() {
		return isGameOver;
	}
	
	public ArrayList<Player> getWinners(){
		int maxScore = Integer.MIN_VALUE;
		for(Player p : mTm.getPlayers()){
			maxScore = Math.max(maxScore, p.getScore());
		}
		ArrayList<Player> winners = new ArrayList<Player>();
		for(Player p : mTm.getPlayers()){
			if (p.getScore() == maxScore){
				winners.add(p);
			}
		}
		
		return winners;
	}

	public Card getBlackCard() {
		return mCm.getCurrentBlackCard();
	}

	public void playAgain() {
		isGameOver = false;
		mTm.resetPlayers();
		mCm.reset();
		dealCards();
	}
}
