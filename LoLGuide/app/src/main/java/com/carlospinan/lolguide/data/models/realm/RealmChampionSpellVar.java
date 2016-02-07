package com.carlospinan.lolguide.data.models.realm;

import io.realm.RealmObject;

/**
 * @author Carlos Piñan
 */
public class RealmChampionSpellVar extends RealmObject {

    private String coeff; // Double array
    private String dyn;
    private String key;
    private String link;
    private String ranksWith;

    public String getCoeff() {
        return coeff;
    }

    public void setCoeff(String coeff) {
        this.coeff = coeff;
    }

    public String getDyn() {
        return dyn;
    }

    public void setDyn(String dyn) {
        this.dyn = dyn;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getRanksWith() {
        return ranksWith;
    }

    public void setRanksWith(String ranksWith) {
        this.ranksWith = ranksWith;
    }
}

