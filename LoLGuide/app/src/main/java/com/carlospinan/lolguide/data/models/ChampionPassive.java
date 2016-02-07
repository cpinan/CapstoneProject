package com.carlospinan.lolguide.data.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Carlos Piñan
 */
public class ChampionPassive implements Parcelable {

    private String description;
    private LOLImage image;
    private String name;
    private String sanitizedDescription;

    protected ChampionPassive(Parcel in) {
        description = in.readString();
        name = in.readString();
        sanitizedDescription = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(description);
        dest.writeString(name);
        dest.writeString(sanitizedDescription);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ChampionPassive> CREATOR = new Creator<ChampionPassive>() {
        @Override
        public ChampionPassive createFromParcel(Parcel in) {
            return new ChampionPassive(in);
        }

        @Override
        public ChampionPassive[] newArray(int size) {
            return new ChampionPassive[size];
        }
    };

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LOLImage getImage() {
        return image;
    }

    public void setImage(LOLImage image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSanitizedDescription() {
        return sanitizedDescription;
    }

    public void setSanitizedDescription(String sanitizedDescription) {
        this.sanitizedDescription = sanitizedDescription;
    }

}
