package com.carlospinan.lolguide.data.models;

import com.google.gson.annotations.SerializedName;

/**
 * @author Carlos Piñan
 */
public class LOLRealm {

    @SerializedName("v")
    private String currentRealVersion;
    @SerializedName("dd")
    private String latestChangedVersionDragonMagic;
    private String cdn;
    @SerializedName("lg")
    private String legacyScriptModeForUE6OrOlder;
    @SerializedName("n")
    private LOLRealmVersion latestChangedLOLRealmVersion;
    private int profileiconmax;
    @SerializedName("l")
    private String defaultLanguageForThisRealm;
    @SerializedName("css")
    private String latestChangedVersionOfDragonMagicCSSFile;

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

    public LOLRealmVersion getLatestChangedLOLRealmVersion() {
        return latestChangedLOLRealmVersion;
    }

    public void setLatestChangedLOLRealmVersion(LOLRealmVersion latestChangedLOLRealmVersion) {
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
