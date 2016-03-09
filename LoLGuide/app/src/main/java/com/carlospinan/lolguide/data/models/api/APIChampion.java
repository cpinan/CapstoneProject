package com.carlospinan.lolguide.data.models.api;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Carlos Piñan
 */
public class APIChampion implements Parcelable {

    private boolean botMmEnabled;
    private int id;
    private boolean rankedPlayEnabled;
    private boolean botEnabled;
    private boolean active;
    private boolean freeToPlay;

    protected APIChampion(Parcel in) {
        botMmEnabled = in.readByte() != 0;
        id = in.readInt();
        rankedPlayEnabled = in.readByte() != 0;
        botEnabled = in.readByte() != 0;
        active = in.readByte() != 0;
        freeToPlay = in.readByte() != 0;
    }

    public static final Creator<APIChampion> CREATOR = new Creator<APIChampion>() {
        @Override
        public APIChampion createFromParcel(Parcel in) {
            return new APIChampion(in);
        }

        @Override
        public APIChampion[] newArray(int size) {
            return new APIChampion[size];
        }
    };

    public boolean isBotMmEnabled() {
        return botMmEnabled;
    }

    public void setBotMmEnabled(boolean botMmEnabled) {
        this.botMmEnabled = botMmEnabled;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isRankedPlayEnabled() {
        return rankedPlayEnabled;
    }

    public void setRankedPlayEnabled(boolean rankedPlayEnabled) {
        this.rankedPlayEnabled = rankedPlayEnabled;
    }

    public boolean isBotEnabled() {
        return botEnabled;
    }

    public void setBotEnabled(boolean botEnabled) {
        this.botEnabled = botEnabled;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isFreeToPlay() {
        return freeToPlay;
    }

    public void setFreeToPlay(boolean freeToPlay) {
        this.freeToPlay = freeToPlay;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (botMmEnabled ? 1 : 0));
        dest.writeInt(id);
        dest.writeByte((byte) (rankedPlayEnabled ? 1 : 0));
        dest.writeByte((byte) (botEnabled ? 1 : 0));
        dest.writeByte((byte) (active ? 1 : 0));
        dest.writeByte((byte) (freeToPlay ? 1 : 0));
    }
}
