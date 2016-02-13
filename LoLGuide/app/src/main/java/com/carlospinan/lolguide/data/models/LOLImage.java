package com.carlospinan.lolguide.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.carlospinan.lolguide.data.models.realm.RealmLOLImage;

/**
 * @author Carlos Piñan
 */
public class LOLImage implements Parcelable {

    private String full;
    private String group;
    private int h;
    private String sprite;
    private int w;
    private int x;
    private int y;

    public LOLImage() { /* UNUSED */ }

    protected LOLImage(Parcel in) {
        full = in.readString();
        group = in.readString();
        h = in.readInt();
        sprite = in.readString();
        w = in.readInt();
        x = in.readInt();
        y = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(full);
        dest.writeString(group);
        dest.writeInt(h);
        dest.writeString(sprite);
        dest.writeInt(w);
        dest.writeInt(x);
        dest.writeInt(y);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LOLImage> CREATOR = new Creator<LOLImage>() {
        @Override
        public LOLImage createFromParcel(Parcel in) {
            return new LOLImage(in);
        }

        @Override
        public LOLImage[] newArray(int size) {
            return new LOLImage[size];
        }
    };

    public String getFull() {
        return full;
    }

    public void setFull(String full) {
        this.full = full;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public String getSprite() {
        return sprite;
    }

    public void setSprite(String sprite) {
        this.sprite = sprite;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public RealmLOLImage getRealmImage() {
        RealmLOLImage i = new RealmLOLImage();
        i.setFull(getFull());
        i.setGroup(getGroup());
        i.setH(getH());
        i.setSprite(getSprite());
        i.setW(getW());
        i.setX(getX());
        i.setY(getY());
        return i;
    }


}
