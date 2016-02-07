package com.carlospinan.lolguide.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @author Carlos Piñan
 */
public class ChampionLevelTip implements Parcelable {

    private List<String> effect;
    private List<String> label;

    protected ChampionLevelTip(Parcel in) {
        effect = in.createStringArrayList();
        label = in.createStringArrayList();
    }

    public static final Creator<ChampionLevelTip> CREATOR = new Creator<ChampionLevelTip>() {
        @Override
        public ChampionLevelTip createFromParcel(Parcel in) {
            return new ChampionLevelTip(in);
        }

        @Override
        public ChampionLevelTip[] newArray(int size) {
            return new ChampionLevelTip[size];
        }
    };

    public List<String> getEffect() {
        return effect;
    }

    public void setEffect(List<String> effect) {
        this.effect = effect;
    }

    public List<String> getLabel() {
        return label;
    }

    public void setLabel(List<String> label) {
        this.label = label;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(effect);
        dest.writeStringList(label);
    }
}
