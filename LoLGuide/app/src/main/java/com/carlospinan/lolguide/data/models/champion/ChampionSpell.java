package com.carlospinan.lolguide.data.models.champion;

import android.os.Parcel;
import android.os.Parcelable;

import com.carlospinan.lolguide.data.models.LOLImage;

import java.util.List;

/**
 * @author Carlos Piñan
 */
public class ChampionSpell implements Parcelable {

    private List<Object> range;
    private ChampionSpellTip leveltip;
    private String resource;
    private int maxrank;
    private List<String> effectBurn;
    private LOLImage image;
    private List<Double> cooldown;
    private List<Integer> cost;
    private List<ChampionSpellVar> vars;
    private String sanitizedDescription;
    private String rangeBurn;
    private String costType;
    private List<List<Double>> effect;
    private String cooldownBurn;
    private String description;
    private String name;
    private String sanitizedTooltip;
    private String key;
    private String costBurn;
    private String tooltip;

    protected ChampionSpell(Parcel in) {
        resource = in.readString();
        maxrank = in.readInt();
        effectBurn = in.createStringArrayList();
        image = in.readParcelable(LOLImage.class.getClassLoader());
        sanitizedDescription = in.readString();
        rangeBurn = in.readString();
        costType = in.readString();
        cooldownBurn = in.readString();
        description = in.readString();
        name = in.readString();
        sanitizedTooltip = in.readString();
        key = in.readString();
        costBurn = in.readString();
        tooltip = in.readString();
    }

    public static final Creator<ChampionSpell> CREATOR = new Creator<ChampionSpell>() {
        @Override
        public ChampionSpell createFromParcel(Parcel in) {
            return new ChampionSpell(in);
        }

        @Override
        public ChampionSpell[] newArray(int size) {
            return new ChampionSpell[size];
        }
    };

    public List<Object> getRange() {
        return range;
    }

    public void setRange(List<Object> range) {
        this.range = range;
    }

    public ChampionSpellTip getLeveltip() {
        return leveltip;
    }

    public void setLeveltip(ChampionSpellTip leveltip) {
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

    public List<String> getEffectBurn() {
        return effectBurn;
    }

    public void setEffectBurn(List<String> effectBurn) {
        this.effectBurn = effectBurn;
    }

    public LOLImage getImage() {
        return image;
    }

    public void setImage(LOLImage image) {
        this.image = image;
    }

    public List<Double> getCooldown() {
        return cooldown;
    }

    public void setCooldown(List<Double> cooldown) {
        this.cooldown = cooldown;
    }

    public List<Integer> getCost() {
        return cost;
    }

    public void setCost(List<Integer> cost) {
        this.cost = cost;
    }

    public List<ChampionSpellVar> getVars() {
        return vars;
    }

    public void setVars(List<ChampionSpellVar> vars) {
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

    public List<List<Double>> getEffect() {
        return effect;
    }

    public void setEffect(List<List<Double>> effect) {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(resource);
        dest.writeInt(maxrank);
        dest.writeStringList(effectBurn);
        dest.writeParcelable(image, flags);
        dest.writeString(sanitizedDescription);
        dest.writeString(rangeBurn);
        dest.writeString(costType);
        dest.writeString(cooldownBurn);
        dest.writeString(description);
        dest.writeString(name);
        dest.writeString(sanitizedTooltip);
        dest.writeString(key);
        dest.writeString(costBurn);
        dest.writeString(tooltip);
    }
}
