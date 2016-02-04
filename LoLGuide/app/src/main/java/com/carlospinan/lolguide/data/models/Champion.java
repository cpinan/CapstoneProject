package com.carlospinan.lolguide.data.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author Carlos Piñan
 */
public class Champion {

    private int id;
    private String title;
    private String name;
    private String key;
    private List<String> tags;
    private ChampionStats stats;
    @SerializedName("enemytips")
    private List<String> enemyTips;
    private LOLImage image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
