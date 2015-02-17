package edu.rosehulman.csse.cardsofdiscord.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class Card implements Parcelable{

    public static final String BLANK = "__________";
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
	
	public Card(Parcel in) {
		readFromParcel(in);
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

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(mId);
		dest.writeValue(mIsBlack);
		dest.writeValue(mIsMature);
		dest.writeString(mContent);
	}

	@SuppressWarnings("unchecked")
	private void readFromParcel(Parcel in) {
		this.mId = in.readInt();
		this.mIsBlack = (boolean) in.readValue(Boolean.class.getClassLoader());
		this.mIsMature = (boolean) in.readValue(Boolean.class.getClassLoader());
		this.mContent = in.readString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + mId;
		result = prime * result + (mIsBlack ? 1231 : 1237);
//		result = prime * result + (mIsMature ? 1231 : 1237);
		return result;
	}

    public boolean isPickTwo() {
        return mIsBlack && mContent.replaceAll(BLANK, "").length() == mContent.length() - 2*BLANK.length();
    }

    public int getNumPicks() {
        if (mIsBlack) {
            int val = Math.max(1, (mContent.length() - mContent.replaceAll(BLANK, "").length()) / BLANK.length());
            Log.d("getNumPicks", Integer.toString(val));
            return val;
        } else {
            Log.d("getNumPicks", ""+1);
            return 1;
        }
    }

	@Override
	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Card other = (Card) obj;
//		if (mContent == null) {
//			if (other.mContent != null)
//				return false;
//		} else if (!mContent.equals(other.mContent))
//			return false;
		if (mId != other.mId)
			return false;
		if (mIsBlack != other.mIsBlack)
			return false;
//		if (mIsMature != other.mIsMature)
//			return false;
		return true;
	}

    @Override
    public String toString() {
        return "Card{" +
                "mId=" + mId +
                ", mIsBlack=" + mIsBlack +
                ", mIsMature=" + mIsMature +
                ", mContent='" + mContent + '\'' +
                '}';
    }

    public static final Parcelable.Creator<Card> CREATOR = new Parcelable.Creator<Card>() {

		@Override
		public Card createFromParcel(Parcel in) {
			return new Card(in);
		}

		@Override
		public Card[] newArray(int size) {
			return new Card[size];
		}

	};

    public void setContent(String content) {
        this.mContent = content;
    }
}
