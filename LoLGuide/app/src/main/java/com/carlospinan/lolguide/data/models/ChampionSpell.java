package com.carlospinan.lolguide.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.carlospinan.lolguide.data.models.realm.RealmChampionSpell;
import com.carlospinan.lolguide.data.models.realm.RealmChampionSpellVar;
import com.carlospinan.lolguide.helpers.ChampionHelper;

import java.util.List;

import io.realm.RealmList;

/**
 * @author Carlos Piñan
 */
public class ChampionSpell implements Parcelable {

    private Object range;
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

    public ChampionSpell() {

    }

    protected ChampionSpell(Parcel in) {
        leveltip = in.readParcelable(ChampionSpellTip.class.getClassLoader());
        resource = in.readString();
        maxrank = in.readInt();
        effectBurn = in.createStringArrayList();
        image = in.readParcelable(LOLImage.class.getClassLoader());
        vars = in.createTypedArrayList(ChampionSpellVar.CREATOR);
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

        String rangeString = in.readString();
        List<String> rangeStringList = ChampionHelper.getListStringFromString(rangeString);
        if (rangeStringList.size() == 1) {
            range = rangeStringList.get(0);
        } else {
            range = rangeStringList;
        }

        String effectString = in.readString();
        effect = ChampionHelper.toDoubleListFromString(effectString);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(leveltip, flags);
        dest.writeString(resource);
        dest.writeInt(maxrank);
        dest.writeStringList(effectBurn);
        dest.writeParcelable(image, flags);
        dest.writeTypedList(vars);
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
        if (range instanceof String) {
            dest.writeString(String.valueOf(range));
        } else if (range instanceof List) {
            List<String> rangeList = (List<String>) range;
            dest.writeString(ChampionHelper.getStringFromList(rangeList));
        }
        dest.writeString(ChampionHelper.stringFromDoubleList(effect));
    }

    @Override
    public int describeContents() {
        return 0;
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

    public Object getRange() {
        return range;
    }

    public void setRange(Object range) {
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

    public RealmChampionSpell getRealmSpell() {
        RealmChampionSpell s = new RealmChampionSpell();
        String rangeString = String.valueOf(getRange());
        if (getRange() instanceof List) {
            List<String> listRange = (List<String>) getRange();
            rangeString = ChampionHelper.getStringFromList(listRange);
        }
        s.setRange(rangeString);
        s.setLeveltip(getLeveltip().getRealmSpellTip());
        s.setResource(getResource());
        s.setMaxrank(getMaxrank());
        s.setEffectBurn(ChampionHelper.getStringFromList(getEffectBurn()));
        s.setImage(getImage().getRealmImage());
        s.setCooldown(ChampionHelper.getStringFromDoubleList(getCooldown()));
        s.setCost(ChampionHelper.getStringFromIntegerList(getCost()));

        RealmList<RealmChampionSpellVar> l1 = new RealmList<>();
        if (getVars() != null && !getVars().isEmpty()) {
            for (ChampionSpellVar var : getVars()) {
                l1.add(var.getRealmSpellVar());
            }
        }
        s.setVars(l1);

        s.setSanitizedDescription(getSanitizedDescription());
        s.setRangeBurn(getRangeBurn());
        s.setCostType(getCostType());
        s.setEffect(ChampionHelper.stringFromDoubleList(getEffect()));
        s.setCooldownBurn(getCooldownBurn());
        s.setDescription(getDescription());
        s.setName(getName());
        s.setSanitizedTooltip(getSanitizedTooltip());
        s.setKey(getKey());
        s.setCostBurn(getCostBurn());
        s.setTooltip(getTooltip());

        return s;
    }
}
