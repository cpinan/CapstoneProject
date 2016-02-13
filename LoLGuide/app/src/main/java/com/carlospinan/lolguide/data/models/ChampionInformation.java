package com.carlospinan.lolguide.data.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Carlos Piñan
 */
public class ChampionInformation implements Parcelable {

    private int attack;
    private int defense;
    private int difficulty;
    private int magic;

    public ChampionInformation() { /* UNUSED */}

    protected ChampionInformation(Parcel in) {
        attack = in.readInt();
        defense = in.readInt();
        difficulty = in.readInt();
        magic = in.readInt();
    }

    public static final Creator<ChampionInformation> CREATOR = new Creator<ChampionInformation>() {
        @Override
        public ChampionInformation createFromParcel(Parcel in) {
            return new ChampionInformation(in);
        }

        @Override
        public ChampionInformation[] newArray(int size) {
            return new ChampionInformation[size];
        }
    };

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getMagic() {
        return magic;
    }

    public void setMagic(int magic) {
        this.magic = magic;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(attack);
        dest.writeInt(defense);
        dest.writeInt(difficulty);
        dest.writeInt(magic);
    }
}
