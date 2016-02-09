package com.carlospinan.lolguide.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @author Carlos Piñan
 */
public class ChampionSpellVar implements Parcelable {

    private List<Double> coeff;
    private String dyn;
    private String key;
    private String link;
    private String ranksWith;

    protected ChampionSpellVar(Parcel in) {
        dyn = in.readString();
        key = in.readString();
        link = in.readString();
        ranksWith = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(dyn);
        dest.writeString(key);
        dest.writeString(link);
        dest.writeString(ranksWith);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ChampionSpellVar> CREATOR = new Creator<ChampionSpellVar>() {
        @Override
        public ChampionSpellVar createFromParcel(Parcel in) {
            return new ChampionSpellVar(in);
        }

        @Override
        public ChampionSpellVar[] newArray(int size) {
            return new ChampionSpellVar[size];
        }
    };

    public List<Double> getCoeff() {
        return coeff;
    }

    public void setCoeff(List<Double> coeff) {
        this.coeff = coeff;
    }

    public String getDyn() {
        return dyn;
    }

    public void setDyn(String dyn) {
        this.dyn = dyn;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getRanksWith() {
        return ranksWith;
    }

    public void setRanksWith(String ranksWith) {
        this.ranksWith = ranksWith;
    }

}
