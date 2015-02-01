package edu.rosehulman.csse.cardsofdiscord.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import android.util.Log;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.rosehulman.csse.cardsofdiscord.model.Deck.DeckEmptyException;
import edu.rosehulman.csse.cardsofdiscord.util.ApplicationController;
import edu.rosehulman.csse.cardsofdiscord.util.JsonUtils;
import edu.rosehulman.csse.cardsofdiscord.util.SessionManager;

public class CardManager {

	private static final String JSON = "JSON";
	private Deck mDeck;
	private ArrayList<Card> mJudgeOptions;
	private Card mBlackcard;

	public CardManager() {
		mJudgeOptions = new ArrayList<Card>();
		reset();
	}

	private void populateCards() {
		boolean isMature = SessionManager.getInstance().isDiscordMode();

		ObjectMapper mapper = JsonUtils.getObjectMapper();

		JsonNode root = null;
		try {
			File f = ApplicationController.getAssetFile("cards.json");
			root = mapper.readTree(f.getPath());
		} catch (IOException e) {
			Log.e(JSON, e.getLocalizedMessage());
		} catch (RuntimeException e) {
			Log.e("RuntimeException", e.getLocalizedMessage());
		}

		ArrayList<Card> bCardsList = new ArrayList<Card>();
		ArrayList<Card> wCardsList = new ArrayList<Card>();
		if (root != null) {
			JsonNode bCards = root.get("black_cards");
			JsonNode wCards = root.get("white_cards");

			if (bCards != null && bCards.isArray()) {
				for (final JsonNode bCard : bCards) {
					int id = bCard.get("id").asInt();
					boolean mature = (bCard.get("maturity").asInt() == 1);
					String content = "<html>" + bCard.get("content").asText()
							+ "</html>";
					if (mature != isMature) {
						bCardsList.add(new Card(id, true, mature, content));
					}
				}
			} else {
				Log.e(JSON, "Error: Unable to parse black card array");
			}

			if (wCards != null && wCards.isArray()) {
				for (final JsonNode wCard : wCards) {
					int id = wCard.get("id").asInt();
					boolean mature = (wCard.get("maturity").asInt() == 1);
					String content = "<html>" + wCard.get("content").asText()
							+ "</html>";
					if (mature != isMature) {
						wCardsList.add(new Card(id, false, mature, content));
					}
				}
			} else {
				Log.e(JSON, "Error: Unable to parse white card array");
			}
		}

		mDeck = new Deck(wCardsList, bCardsList);
	}

	public Deck getDeck() {
		return mDeck;
	}

	public Card drawWhiteCard() throws DeckEmptyException {
		return mDeck.drawWhiteCard();
	}

	public Card drawBlackCard() throws DeckEmptyException {
		Card bCard = mDeck.drawBlackCard();
		mBlackcard = bCard;
		return bCard;
	}

	public void playCardToJudge(Card whiteCard) {
		mJudgeOptions.add(whiteCard);
	}

	public ArrayList<Card> getJudgeOptions() {
		return mJudgeOptions;
	}

	public void clearJudgeOptions() {
		mJudgeOptions.clear();
	}

	public Card getCurrentBlackCard() {
		return mBlackcard;
	}
	
	public Card getCardById(int id){
		if (mDeck == null) {
			return null;
		}
		for(Card c : mDeck.getBlackCards()){
			if (c.getId() == id){
				return c;
			}
		}
		for(Card c : mDeck.getWhiteCards()){
			if (c.getId() == id){
				return c;
			}
		}
		return null;
	}

	public void reset() {
		populateCards();
		if (mDeck != null) {
			mDeck.shuffle();
		}

		clearJudgeOptions();
	}

}
