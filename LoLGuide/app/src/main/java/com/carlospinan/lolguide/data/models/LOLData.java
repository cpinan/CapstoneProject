package com.carlospinan.lolguide.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * @author Carlos Piñan
 */
public class LOLData implements Parcelable {

    @SerializedName("v")
    private String currentRealVersion;
    @SerializedName("dd")
    private String latestChangedVersionDragonMagic;
    private String cdn;
    @SerializedName("lg")
    private String legacyScriptModeForUE6OrOlder;
    @SerializedName("n")
    private LOLDataVersion latestChangedLOLRealmVersion;
    private int profileiconmax;
    @SerializedName("l")
    private String defaultLanguageForThisRealm;
    @SerializedName("css")
    private String latestChangedVersionOfDragonMagicCSSFile;

    protected LOLData(Parcel in) {
        currentRealVersion = in.readString();
        latestChangedVersionDragonMagic = in.readString();
        cdn = in.readString();
        legacyScriptModeForUE6OrOlder = in.readString();
        profileiconmax = in.readInt();
        defaultLanguageForThisRealm = in.readString();
        latestChangedVersionOfDragonMagicCSSFile = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(currentRealVersion);
        dest.writeString(latestChangedVersionDragonMagic);
        dest.writeString(cdn);
        dest.writeString(legacyScriptModeForUE6OrOlder);
        dest.writeInt(profileiconmax);
        dest.writeString(defaultLanguageForThisRealm);
        dest.writeString(latestChangedVersionOfDragonMagicCSSFile);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LOLData> CREATOR = new Creator<LOLData>() {
        @Override
        public LOLData createFromParcel(Parcel in) {
            return new LOLData(in);
        }

        @Override
        public LOLData[] newArray(int size) {
            return new LOLData[size];
        }
    };

    public String getCurrentRealVersion() {
        return currentRealVersion;
    }

    public void setCurrentRealVersion(String currentRealVersion) {
        this.currentRealVersion = currentRealVersion;
    }

    public String getLatestChangedVersionDragonMagic() {
        return latestChangedVersionDragonMagic;
    }

    public void setLatestChangedVersionDragonMagic(String latestChangedVersionDragonMagic) {
        this.latestChangedVersionDragonMagic = latestChangedVersionDragonMagic;
    }

    public String getLegacyScriptModeForUE6OrOlder() {
        return legacyScriptModeForUE6OrOlder;
    }

    public void setLegacyScriptModeForUE6OrOlder(String legacyScriptModeForUE6OrOlder) {
        this.legacyScriptModeForUE6OrOlder = legacyScriptModeForUE6OrOlder;
    }

    public LOLDataVersion getLatestChangedLOLRealmVersion() {
        return latestChangedLOLRealmVersion;
    }

    public void setLatestChangedLOLRealmVersion(LOLDataVersion latestChangedLOLRealmVersion) {
        this.latestChangedLOLRealmVersion = latestChangedLOLRealmVersion;
    }

    public int getProfileiconmax() {
        return profileiconmax;
    }

    public void setProfileiconmax(int profileiconmax) {
        this.profileiconmax = profileiconmax;
    }

    public String getDefaultLanguageForThisRealm() {
        return defaultLanguageForThisRealm;
    }

    public void setDefaultLanguageForThisRealm(String defaultLanguageForThisRealm) {
        this.defaultLanguageForThisRealm = defaultLanguageForThisRealm;
    }

    public String getLatestChangedVersionOfDragonMagicCSSFile() {
        return latestChangedVersionOfDragonMagicCSSFile;
    }

    public void setLatestChangedVersionOfDragonMagicCSSFile(String latestChangedVersionOfDragonMagicCSSFile) {
        this.latestChangedVersionOfDragonMagicCSSFile = latestChangedVersionOfDragonMagicCSSFile;
    }

    public String getCdn() {
        return cdn;
    }

    public void setCdn(String cdn) {
        this.cdn = cdn;
    }
}
