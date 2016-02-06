package com.carlospinan.lolguide.data.models.champion;

import android.os.Parcel;
import android.os.Parcelable;

import com.carlospinan.lolguide.data.models.LOLImage;

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
        image = in.readParcelable(LOLImage.class.getClassLoader());
        name = in.readString();
        sanitizedDescription = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(description);
        dest.writeParcelable(image, flags);
        dest.writeString(name);
        dest.writeString(sanitizedDescription);
    }
}
