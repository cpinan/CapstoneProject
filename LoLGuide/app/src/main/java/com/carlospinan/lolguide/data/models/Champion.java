package com.carlospinan.lolguide.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.carlospinan.lolguide.data.models.realm.RealmChampion;
import com.carlospinan.lolguide.data.models.realm.RealmChampionInformation;
import com.carlospinan.lolguide.data.models.realm.RealmChampionPassive;
import com.carlospinan.lolguide.data.models.realm.RealmChampionSkin;
import com.carlospinan.lolguide.data.models.realm.RealmChampionStats;
import com.carlospinan.lolguide.helpers.ChampionHelper;
import com.google.gson.annotations.SerializedName;

import java.util.Comparator;
import java.util.List;

import io.realm.RealmList;

/**
 * @author Carlos Piñan
 */
public class Champion implements Parcelable, Comparator<Champion> {

    // TODO Add recommended build
    @SerializedName("id")
    private int championId;
    private String title;
    private String name;
    private String key;
    @SerializedName("tags")
    private List<String> championTags;
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
    private transient String portraitUri;
    private transient String skinsUris;
    private transient String passiveUri;
    private transient String abilitiesUris;

    public Champion() { /* UNUSED */ }

    protected Champion(Parcel in) {
        championId = in.readInt();
        title = in.readString();
        name = in.readString();
        key = in.readString();
        championTags = in.createStringArrayList();
        stats = in.readParcelable(ChampionStats.class.getClassLoader());
        enemyTips = in.createStringArrayList();
        allyTips = in.createStringArrayList();
        image = in.readParcelable(LOLImage.class.getClassLoader());
        blurb = in.readString();
        lore = in.readString();
        info = in.readParcelable(ChampionInformation.class.getClassLoader());
        partype = in.readString();
        passive = in.readParcelable(ChampionPassive.class.getClassLoader());
        skins = in.createTypedArrayList(ChampionSkin.CREATOR);
        spells = in.createTypedArrayList(ChampionSpell.CREATOR);
        portraitUri = in.readString();
        skinsUris = in.readString();
        passiveUri = in.readString();
        abilitiesUris = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(championId);
        dest.writeString(title);
        dest.writeString(name);
        dest.writeString(key);
        dest.writeStringList(championTags);
        dest.writeParcelable(stats, flags);
        dest.writeStringList(enemyTips);
        dest.writeStringList(allyTips);
        dest.writeParcelable(image, flags);
        dest.writeString(blurb);
        dest.writeString(lore);
        dest.writeParcelable(info, flags);
        dest.writeString(partype);
        dest.writeParcelable(passive, flags);
        dest.writeTypedList(skins);
        dest.writeTypedList(spells);
        dest.writeString(portraitUri);
        dest.writeString(skinsUris);
        dest.writeString(passiveUri);
        dest.writeString(abilitiesUris);
    }

    @Override
    public int describeContents() {
        return 0;
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

    public List<String> getChampionTags() {
        return championTags;
    }

    public void setChampionTags(List<String> championTags) {
        this.championTags = championTags;
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

    public List<String> getAllyTips() {
        return allyTips;
    }

    public void setAllyTips(List<String> allyTips) {
        this.allyTips = allyTips;
    }

    public LOLImage getImage() {
        return image;
    }

    public void setImage(LOLImage image) {
        this.image = image;
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

    public String getPortraitUri() {
        return portraitUri;
    }

    public void setPortraitUri(String portraitUri) {
        this.portraitUri = portraitUri;
    }

    public String getSkinsUris() {
        return skinsUris;
    }

    public void setSkinsUris(String skinsUris) {
        this.skinsUris = skinsUris;
    }

    public String getPassiveUri() {
        return passiveUri;
    }

    public void setPassiveUri(String passiveUri) {
        this.passiveUri = passiveUri;
    }

    public String getAbilitiesUris() {
        return abilitiesUris;
    }

    public void setAbilitiesUris(String abilitiesUris) {
        this.abilitiesUris = abilitiesUris;
    }

    @Override
    public int compare(Champion lhs, Champion rhs) {
        return lhs.getName().compareTo(rhs.getName());
    }

    // Utilities
    private RealmChampionStats getRealmStats() {
        ChampionStats s = getStats();
        RealmChampionStats o = new RealmChampionStats();
        o.setAttackrange(s.getAttackrange());
        o.setMpperlevel(s.getMpperlevel());
        o.setMp(s.getMp());
        o.setAttackdamage(s.getAttackdamage());
        o.setHp(s.getHp());
        o.setHpperlevel(s.getHpperlevel());
        o.setAttackdamageperlevel(s.getAttackdamageperlevel());
        o.setArmor(s.getArmor());
        o.setMpregenperlevel(s.getMpregenperlevel());
        o.setHpregen(s.getHpregen());
        o.setCritperlevel(s.getCritperlevel());
        o.setSpellblockperlevel(s.getSpellblockperlevel());
        o.setMpregen(s.getMpregen());
        o.setAttackspeedperlevel(s.getAttackspeedperlevel());
        o.setSpellblock(s.getSpellblock());
        o.setMovespeed(s.getMovespeed());
        o.setAttackspeedoffset(s.getAttackspeedoffset());
        o.setCrit(s.getCrit());
        o.setHpregenperlevel(s.getHpregenperlevel());
        o.setArmorperlevel(s.getArmorperlevel());
        return o;
    }

    private RealmChampionInformation getRealmInfo() {
        ChampionInformation c = getInfo();
        RealmChampionInformation r = new RealmChampionInformation();
        r.setAttack(c.getAttack());
        r.setDefense(c.getDefense());
        r.setDifficulty(c.getDifficulty());
        r.setMagic(c.getMagic());
        return r;
    }

    private RealmList<RealmChampionSkin> getRealmSkins() {
        List<ChampionSkin> skins = getSkins();
        RealmList<RealmChampionSkin> l = new RealmList<>();
        for (ChampionSkin s : skins) {
            l.add(s.getRealmSkin());
        }
        return l;
    }

    private RealmChampionPassive getRealmPassive() {
        ChampionPassive p = getPassive();
        RealmChampionPassive r = new RealmChampionPassive();
        r.setDescription(p.getDescription());
        r.setImage(p.getImage().getRealmImage());
        r.setName(p.getName());
        r.setSanitizedDescription(p.getSanitizedDescription());
        return r;
    }

    public RealmChampion getRealmChampion() {
        RealmChampion o = new RealmChampion();
        o.setChampionId(getChampionId());
        o.setTitle(getTitle());
        o.setName(getName());
        o.setKey(getKey());
        o.setChampionTags(ChampionHelper.getStringFromList(getChampionTags()));
        o.setStats(getRealmStats());
        o.setEnemyTips(ChampionHelper.getStringFromList(getEnemyTips()));
        o.setAllyTips(ChampionHelper.getStringFromList(getAllyTips()));
        o.setImage(getImage().getRealmImage());
        o.setBlurb(getBlurb());
        o.setLore(getLore());
        o.setInfo(getRealmInfo());
        o.setPartype(getPartype());
        o.setSkins(getRealmSkins());
        o.setPassive(getRealmPassive());
        o.setSpells(null);
        o.setPortraitUri(null);
        o.setSkinsUris(null);
        o.setAbilitiesUris(null);
        return o;
    }

}