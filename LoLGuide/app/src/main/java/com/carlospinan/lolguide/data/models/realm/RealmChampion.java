package com.carlospinan.lolguide.data.models.realm;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * @author Carlos Piñan
 */
public class RealmChampion extends RealmObject {

    @PrimaryKey
    private int championId;
    private String title;
    private String name;
    private String key;
    private String championTags; // String array
    private RealmChampionStats stats;
    private String enemyTips; // String array
    private String allyTips; // String array
    private RealmLOLImage image;
    private String blurb;
    private String lore;
    private RealmChampionInformation info;
    private String partype;
    private RealmChampionPassive passive;
    private RealmList<RealmChampionSkin> skins;
    private RealmList<RealmChampionSpell> spells;

    private String portraitUri;
    private String skinsUris;
    private String passiveUri;
    private String abilitiesUris;
    private Boolean favorite;

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

    public String getChampionTags() {
        return championTags;
    }

    public void setChampionTags(String championTags) {
        this.championTags = championTags;
    }

    public RealmChampionStats getStats() {
        return stats;
    }

    public void setStats(RealmChampionStats stats) {
        this.stats = stats;
    }

    public String getEnemyTips() {
        return enemyTips;
    }

    public void setEnemyTips(String enemyTips) {
        this.enemyTips = enemyTips;
    }

    public String getAllyTips() {
        return allyTips;
    }

    public void setAllyTips(String allyTips) {
        this.allyTips = allyTips;
    }

    public RealmLOLImage getImage() {
        return image;
    }

    public void setImage(RealmLOLImage image) {
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

    public RealmChampionInformation getInfo() {
        return info;
    }

    public void setInfo(RealmChampionInformation info) {
        this.info = info;
    }

    public String getPartype() {
        return partype;
    }

    public void setPartype(String partype) {
        this.partype = partype;
    }

    public RealmChampionPassive getPassive() {
        return passive;
    }

    public void setPassive(RealmChampionPassive passive) {
        this.passive = passive;
    }

    public RealmList<RealmChampionSkin> getSkins() {
        return skins;
    }

    public void setSkins(RealmList<RealmChampionSkin> skins) {
        this.skins = skins;
    }

    public RealmList<RealmChampionSpell> getSpells() {
        return spells;
    }

    public void setSpells(RealmList<RealmChampionSpell> spells) {
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

    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }
}
