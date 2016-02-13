package com.carlospinan.lolguide.data.models.realm;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * @author Carlos Piñan
 */
public class RealmChampionSpell extends RealmObject {

    private String range; // String array
    private RealmChampionSpellTip leveltip;
    private String resource;
    private int maxrank;
    private String effectBurn; // String array
    private RealmLOLImage image;
    private String cooldown; // Double array
    private String cost; // Integer array
    private RealmList<RealmChampionSpellVar> vars;
    private String sanitizedDescription;
    private String rangeBurn;
    private String costType;
    private String effect; // Double array
    private String cooldownBurn;
    private String description;
    private String name;
    private String sanitizedTooltip;
    private String key;
    private String costBurn;
    private String tooltip;

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public RealmChampionSpellTip getLeveltip() {
        return leveltip;
    }

    public void setLeveltip(RealmChampionSpellTip leveltip) {
        this.leveltip = leveltip;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public int getMaxrank() {
        return maxrank;
    }

    public void setMaxrank(int maxrank) {
        this.maxrank = maxrank;
    }

    public String getEffectBurn() {
        return effectBurn;
    }

    public void setEffectBurn(String effectBurn) {
        this.effectBurn = effectBurn;
    }

    public RealmLOLImage getImage() {
        return image;
    }

    public void setImage(RealmLOLImage image) {
        this.image = image;
    }

    public String getCooldown() {
        return cooldown;
    }

    public void setCooldown(String cooldown) {
        this.cooldown = cooldown;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public RealmList<RealmChampionSpellVar> getVars() {
        return vars;
    }

    public void setVars(RealmList<RealmChampionSpellVar> vars) {
        this.vars = vars;
    }

    public String getSanitizedDescription() {
        return sanitizedDescription;
    }

    public void setSanitizedDescription(String sanitizedDescription) {
        this.sanitizedDescription = sanitizedDescription;
    }

    public String getRangeBurn() {
        return rangeBurn;
    }

    public void setRangeBurn(String rangeBurn) {
        this.rangeBurn = rangeBurn;
    }

    public String getCostType() {
        return costType;
    }

    public void setCostType(String costType) {
        this.costType = costType;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public String getCooldownBurn() {
        return cooldownBurn;
    }

    public void setCooldownBurn(String cooldownBurn) {
        this.cooldownBurn = cooldownBurn;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSanitizedTooltip() {
        return sanitizedTooltip;
    }

    public void setSanitizedTooltip(String sanitizedTooltip) {
        this.sanitizedTooltip = sanitizedTooltip;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCostBurn() {
        return costBurn;
    }

    public void setCostBurn(String costBurn) {
        this.costBurn = costBurn;
    }

    public String getTooltip() {
        return tooltip;
    }

    public void setTooltip(String tooltip) {
        this.tooltip = tooltip;
    }
}
