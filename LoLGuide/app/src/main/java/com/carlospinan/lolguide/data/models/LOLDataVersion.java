package com.carlospinan.lolguide.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * @author Carlos Piñan
 */
public class LOLDataVersion implements Parcelable {

    private String champion;
    @SerializedName("profileicon")
    private String profileIcon;
    private String item;
    private String map;
    private String mastery;
    private String language;
    private String summoner;
    private String rune;

    protected LOLDataVersion(Parcel in) {
        champion = in.readString();
        profileIcon = in.readString();
        item = in.readString();
        map = in.readString();
        mastery = in.readString();
        language = in.readString();
        summoner = in.readString();
        rune = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(champion);
        dest.writeString(profileIcon);
        dest.writeString(item);
        dest.writeString(map);
        dest.writeString(mastery);
        dest.writeString(language);
        dest.writeString(summoner);
        dest.writeString(rune);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LOLDataVersion> CREATOR = new Creator<LOLDataVersion>() {
        @Override
        public LOLDataVersion createFromParcel(Parcel in) {
            return new LOLDataVersion(in);
        }

        @Override
        public LOLDataVersion[] newArray(int size) {
            return new LOLDataVersion[size];
        }
    };

    public String getChampion() {
        return champion;
    }

    public void setChampion(String champion) {
        this.champion = champion;
    }

    public String getProfileIcon() {
        return profileIcon;
    }

    public void setProfileIcon(String profileIcon) {
        this.profileIcon = profileIcon;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public String getMastery() {
        return mastery;
    }

    public void setMastery(String mastery) {
        this.mastery = mastery;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getSummoner() {
        return summoner;
    }

    public void setSummoner(String summoner) {
        this.summoner = summoner;
    }

    public String getRune() {
        return rune;
    }

    public void setRune(String rune) {
        this.rune = rune;
    }
}
