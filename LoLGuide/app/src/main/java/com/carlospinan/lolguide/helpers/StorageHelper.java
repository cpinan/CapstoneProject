package com.carlospinan.lolguide.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import com.carlospinan.lolguide.ApplicationController;
import com.carlospinan.lolguide.data.Globals;
import com.carlospinan.lolguide.data.enums.RegionEnum;

/**
 * @author Carlos Piñan
 */
public class StorageHelper {

    // Storage Key
    private static final String STORAGE_KEY = "com.carlospinan.lolguide.storage";

    // Keys
    public static final String LOL_CDN_URL_KEY = "com.carlospinan.lolguide.lolcdnurlkey";
    public static final String LOL_API_VERSION_KEY = "com.carlospinan.lolguide.lolapiversionkey";
    public static final String LOL_REGION_KEY = "com.carlospinan.lolguide.lolregionkey";

    private static StorageHelper instance;

    private StorageHelper() { /* UNUSED */ }

    public static StorageHelper get() {
        if (instance == null) {
            instance = new StorageHelper();
        }
        return instance;
    }

    // Manager
    private SharedPreferences getStorage() {
        Context context = ApplicationController.getContext();
        return context.getSharedPreferences(STORAGE_KEY, Context.MODE_PRIVATE);
    }

    public void saveString(String key, String value) {
        SharedPreferences.Editor editor = getStorage().edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String getString(String key, String defaultValue) {
        return getStorage().getString(key, defaultValue);
    }

    // Standard keys
    public String getCDNUrl() {
        String cdn = Globals.LOL_DEFAULT_CDN_URL;
        if (getStorage() != null) {
            cdn = getStorage().getString(LOL_CDN_URL_KEY, Globals.LOL_DEFAULT_CDN_URL);
        }
        if (cdn.endsWith("/")) {
            cdn = cdn.substring(0, cdn.length() - 1);
        }
        return cdn;
    }

    public String getVersion() {
        String version = getStorage().getString(LOL_API_VERSION_KEY, Globals.LOL_DEFAULT_VERSION);
        return version;
    }

    public RegionEnum getRegion() {
        String region = getStorage().getString(LOL_REGION_KEY, RegionEnum.lan.toString());
        return RegionEnum.valueOf(region);
    }

    // http://ddragon.leagueoflegends.com/cdn/img/champion/splash/Aatrox_0.jpg
    public String getChampionSplashUrl(String championName, int championSkinID) {
        String cdn = getCDNUrl();
        return cdn + "/img/champion/splash/" + championName + "_" + championSkinID + ".jpg";
    }

    public String getChampionLoadingUrl(String championName, int championSkinID) {
        String cdn = getCDNUrl();
        return cdn + "/img/champion/loading/" + championName + "_" + championSkinID + ".jpg";
    }

    // http://ddragon.leagueoflegends.com/cdn/6.2.1/img/champion/Aatrox.png
    public String getChampionPortraitUrl(String championName) {
        String cdn = getCDNUrl();
        return cdn + "/" + getVersion() + "/champion/" + championName + ".png";
    }

    // http://ddragon.leagueoflegends.com/cdn/6.2.1/img/passive/Cryophoenix_Rebirth.png
    public String getPassiveAbilityUrl(String value) {
        String cdn = getCDNUrl();
        return cdn + "/" + getVersion() + "/img/passive/" + value;
    }

    // http://ddragon.leagueoflegends.com/cdn/6.2.1/img/spell/FlashFrost.png
    public String getChampionAbilityUrl(String value) {
        String cdn = getCDNUrl();
        return cdn + "/" + getVersion() + "/img/spell/" + value;
    }

    // http://ddragon.leagueoflegends.com/cdn/6.2.1/img/profileicon/588.png
    public String getProfileIconUrl(int iconId) {
        String cdn = getCDNUrl();
        return cdn + "/" + getVersion() + "/img/profileicon/" + iconId + ".png";
    }

}
