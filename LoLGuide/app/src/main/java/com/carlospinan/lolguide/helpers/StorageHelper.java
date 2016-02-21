package com.carlospinan.lolguide.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import com.carlospinan.lolguide.ApplicationController;
import com.carlospinan.lolguide.data.Globals;
import com.carlospinan.lolguide.data.enums.RegionEnum;

import java.util.List;
import java.util.Locale;

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
    public static final String LOL_LANGUAGES_KEY = "com.carlospinan.lolguide.lollanguageskey";
    public static final String LOL_CHAMPION_STORAGE_KEY = "com.carlospinan.lolguide.championstoragekey";
    public static final String LOL_CHAMPION_STATE_SAVE_KEY = "com.carlospinan.lolguide.championstatesavekey";
    public static final String LOL_DEFAULT_LANGUAGE = "com.carlospinan.lolguide.loldefaultlanguage";
    public static final String LOL_MUST_UPDATE_UI = "com.carlospinan.lolguide.lolmostupdateui";

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

    public boolean isChampionSaving(int championId) {
        return getStorage().getBoolean(LOL_CHAMPION_STATE_SAVE_KEY + "_" + championId, false);
    }

    public void championIsSaving(int championId, boolean state) {
        getStorage().edit().putBoolean(LOL_CHAMPION_STATE_SAVE_KEY + "_" + championId, state).commit();
    }

    public void saveChampion(int championId, boolean state) {
        getStorage().edit().putBoolean(LOL_CHAMPION_STORAGE_KEY + "_" + championId, state).commit();
    }

    public void saveChampion(int championId) {
        saveChampion(championId, true);
    }

    public boolean getChampion(int championId) {
        return getStorage().getBoolean(LOL_CHAMPION_STORAGE_KEY + "_" + championId, false);
    }

    public String getDefaultLanguage() {
        String defaultLanguage = getStorage().getString(LOL_DEFAULT_LANGUAGE, null);
        if (defaultLanguage == null) {
            defaultLanguage = "en_US";
            List<String> languages = StorageHelper.get().getLanguages();
            if (languages != null && !languages.isEmpty()) {
                String language = Locale.getDefault().getLanguage();
                for (String l : languages) {
                    if (l.contains(language)) {
                        defaultLanguage = l;
                        break;
                    }
                }
                saveDefaultLanguage(defaultLanguage);
            }
        }
        return defaultLanguage;
    }

    public void saveDefaultLanguage(String language) {
        getStorage().edit().putString(LOL_DEFAULT_LANGUAGE, language).commit();
    }

    // Set language
    public void saveLanguages(List<String> languages) {
        saveString(LOL_LANGUAGES_KEY, ChampionHelper.getStringFromList(languages));
    }

    public void saveVersion(String version) {
        saveString(LOL_API_VERSION_KEY, version);
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

    public void setVersion(String version) {
        saveString(LOL_API_VERSION_KEY, version);
    }

    public String getVersion() {
        String version = getStorage().getString(LOL_API_VERSION_KEY, Globals.LOL_DEFAULT_VERSION);
        return version;
    }

    public boolean mustUpdateUi() {
        return getStorage().getBoolean(LOL_MUST_UPDATE_UI, false);
    }

    public void setMustUpdateUi(boolean state) {
        getStorage().edit().putBoolean(LOL_MUST_UPDATE_UI, state).commit();
    }

    public void saveRegion(String region) {
        getStorage().edit().putString(LOL_REGION_KEY, region).commit();
    }

    public RegionEnum getRegion() {
        String region = getStorage().getString(LOL_REGION_KEY, RegionEnum.na.toString());
        return RegionEnum.valueOf(region);
    }

    public List<String> getLanguages() {
        String languages = getStorage().getString(LOL_LANGUAGES_KEY, null);
        if (languages != null) {
            return ChampionHelper.getListStringFromString(languages);
        } else {
            return null;
        }
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
        return cdn + "/" + getVersion() + "/img/champion/" + championName;
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

    // http://cdn.leagueoflegends.com/champion-abilities/videos/mp4/0113_05.mp4
    public String getVideoAbilityUrl(int championId, int ability) {
        String url = "http://cdn.leagueoflegends.com/champion-abilities/videos/mp4/%04d_%02d.mp4";
        return String.format(url, championId, ability);
    }

    // http://www.lolking.net/models?champion=103&skin=0
    public String getWebWith3DModel(int championId, int numSkin) {
        return String.format("http://www.lolking.net/models?champion=%d&skin=%d", championId, numSkin);
    }

}
