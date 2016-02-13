package com.carlospinan.lolguide.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.carlospinan.lolguide.data.models.realm.RealmChampionSpellTip;
import com.carlospinan.lolguide.helpers.ChampionHelper;

import java.util.List;

/**
 * @author Carlos Piñan
 */
public class ChampionSpellTip implements Parcelable {

    private List<String> effect;
    private List<String> label;

    public ChampionSpellTip() {

    }

    public RealmChampionSpellTip getRealmSpellTip() {
        RealmChampionSpellTip r = new RealmChampionSpellTip();
        r.setEffect(ChampionHelper.getStringFromList(effect));
        r.setLabel(ChampionHelper.getStringFromList(label));
        return r;
    }

    protected ChampionSpellTip(Parcel in) {
        effect = in.createStringArrayList();
        label = in.createStringArrayList();
    }

    public static final Creator<ChampionSpellTip> CREATOR = new Creator<ChampionSpellTip>() {
        @Override
        public ChampionSpellTip createFromParcel(Parcel in) {
            return new ChampionSpellTip(in);
        }

        @Override
        public ChampionSpellTip[] newArray(int size) {
            return new ChampionSpellTip[size];
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
