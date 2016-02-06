package com.carlospinan.lolguide.data.models.champion;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.carlospinan.lolguide.data.models.LOLImage;
import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

import java.util.List;

/**
 * @author Carlos Piñan
 */
public class Champion extends SugarRecord implements Parcelable {

    private int championId;
    private String title;
    private String name;
    private String key;
    private List<String> tags;
    private ChampionStats stats;
    @SerializedName("enemytips")
    private List<String> enemyTips;
    @SerializedName("allytips")
    private List<String> allyTips;
    private LOLImage image;
    private String blurb;
    private String lore;
    private ChampionInformation info;
    private String partype;
    private ChampionPassive passive;
    private List<ChampionSkin> skins;
    private List<ChampionSpell> spells;

    // Extra values for image data.
    private Uri portraitUri;
    private List<Uri> skinsUris;
    private Uri passiveUri;
    private List<Uri> abilitiesUris;

    // TODO Add recommended build

    protected Champion(Parcel in) {
        championId = in.readInt();
        title = in.readString();
        name = in.readString();
        key = in.readString();
        tags = in.createStringArrayList();
        enemyTips = in.createStringArrayList();
        allyTips = in.createStringArrayList();
        blurb = in.readString();
        lore = in.readString();
        partype = in.readString();
    }

    public static final Creator<Champion> CREATOR = new Creator<Champion>() {
        @Override
        public Champion createFromParcel(Parcel in) {
            return new Champion(in);
        }

        @Override
        public Champion[] newArray(int size) {
            return new Champion[size];
        }
    };

    public int getChampionId() {
        return championId;
    }

    public void setChampionId(int championId) {
        this.championId = championId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public ChampionStats getStats() {
        return stats;
    }

    public void setStats(ChampionStats stats) {
        this.stats = stats;
    }

    public List<String> getEnemyTips() {
        return enemyTips;
    }

    public void setEnemyTips(List<String> enemyTips) {
        this.enemyTips = enemyTips;
    }

    public LOLImage getImage() {
        return image;
    }

    public void setImage(LOLImage image) {
        this.image = image;
    }

    public List<String> getAllyTips() {
        return allyTips;
    }

    public void setAllyTips(List<String> allyTips) {
        this.allyTips = allyTips;
    }

    public String getBlurb() {
        return blurb;
    }

    public void setBlurb(String blurb) {
        this.blurb = blurb;
    }

    public String getLore() {
        return lore;
    }

    public void setLore(String lore) {
        this.lore = lore;
    }

    public ChampionInformation getInfo() {
        return info;
    }

    public void setInfo(ChampionInformation info) {
        this.info = info;
    }

    public String getPartype() {
        return partype;
    }

    public void setPartype(String partype) {
        this.partype = partype;
    }

    public ChampionPassive getPassive() {
        return passive;
    }

    public void setPassive(ChampionPassive passive) {
        this.passive = passive;
    }

    public List<ChampionSkin> getSkins() {
        return skins;
    }

    public void setSkins(List<ChampionSkin> skins) {
        this.skins = skins;
    }

    public List<ChampionSpell> getSpells() {
        return spells;
    }

    public void setSpells(List<ChampionSpell> spells) {
        this.spells = spells;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(championId);
        dest.writeString(title);
        dest.writeString(name);
        dest.writeString(key);
        dest.writeStringList(tags);
        dest.writeStringList(enemyTips);
        dest.writeStringList(allyTips);
        dest.writeString(blurb);
        dest.writeString(lore);
        dest.writeString(partype);
    }

    public Uri getPortraitUri() {
        return portraitUri;
    }

    public void setPortraitUri(Uri portraitUri) {
        this.portraitUri = portraitUri;
    }

    public List<Uri> getSkinsUris() {
        return skinsUris;
    }

    public void setSkinsUris(List<Uri> skinsUris) {
        this.skinsUris = skinsUris;
    }

    public List<Uri> getAbilitiesUris() {
        return abilitiesUris;
    }

    public void setAbilitiesUris(List<Uri> abilitiesUris) {
        this.abilitiesUris = abilitiesUris;
    }

    public Uri getPassiveUri() {
        return passiveUri;
    }

    public void setPassiveUri(Uri passiveUri) {
        this.passiveUri = passiveUri;
    }
}
