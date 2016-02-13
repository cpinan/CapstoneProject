package com.carlospinan.lolguide.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.carlospinan.lolguide.data.models.realm.RealmChampionSkin;

/**
 * @author Carlos Piñan
 */
public class ChampionSkin implements Parcelable {

    private int id;
    private int num;
    private String name;

    public ChampionSkin() {
    }

    protected ChampionSkin(Parcel in) {
        id = in.readInt();
        num = in.readInt();
        name = in.readString();
    }

    public static final Creator<ChampionSkin> CREATOR = new Creator<ChampionSkin>() {
        @Override
        public ChampionSkin createFromParcel(Parcel in) {
            return new ChampionSkin(in);
        }

        @Override
        public ChampionSkin[] newArray(int size) {
            return new ChampionSkin[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(num);
        dest.writeString(name);
    }

    public RealmChampionSkin getRealmSkin() {
        RealmChampionSkin s = new RealmChampionSkin();
        s.setId(getId());
        s.setNum(getNum());
        s.setName(getName());
        return s;
    }
}
