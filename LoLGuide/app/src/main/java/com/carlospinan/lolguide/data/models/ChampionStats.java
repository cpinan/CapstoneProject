package com.carlospinan.lolguide.data.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Carlos Piñan
 */
public class ChampionStats implements Parcelable {

    private double attackrange;
    private double mpperlevel;
    private double mp;
    private double attackdamage;
    private double hp;
    private double hpperlevel;
    private double attackdamageperlevel;
    private double armor;
    private double mpregenperlevel;
    private double hpregen;
    private double critperlevel;
    private double spellblockperlevel;
    private double mpregen;
    private double attackspeedperlevel;
    private double spellblock;
    private double movespeed;
    private double attackspeedoffset;
    private double crit;
    private double hpregenperlevel;
    private double armorperlevel;

    protected ChampionStats(Parcel in) {
        attackrange = in.readDouble();
        mpperlevel = in.readDouble();
        mp = in.readDouble();
        attackdamage = in.readDouble();
        hp = in.readDouble();
        hpperlevel = in.readDouble();
        attackdamageperlevel = in.readDouble();
        armor = in.readDouble();
        mpregenperlevel = in.readDouble();
        hpregen = in.readDouble();
        critperlevel = in.readDouble();
        spellblockperlevel = in.readDouble();
        mpregen = in.readDouble();
        attackspeedperlevel = in.readDouble();
        spellblock = in.readDouble();
        movespeed = in.readDouble();
        attackspeedoffset = in.readDouble();
        crit = in.readDouble();
        hpregenperlevel = in.readDouble();
        armorperlevel = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(attackrange);
        dest.writeDouble(mpperlevel);
        dest.writeDouble(mp);
        dest.writeDouble(attackdamage);
        dest.writeDouble(hp);
        dest.writeDouble(hpperlevel);
        dest.writeDouble(attackdamageperlevel);
        dest.writeDouble(armor);
        dest.writeDouble(mpregenperlevel);
        dest.writeDouble(hpregen);
        dest.writeDouble(critperlevel);
        dest.writeDouble(spellblockperlevel);
        dest.writeDouble(mpregen);
        dest.writeDouble(attackspeedperlevel);
        dest.writeDouble(spellblock);
        dest.writeDouble(movespeed);
        dest.writeDouble(attackspeedoffset);
        dest.writeDouble(crit);
        dest.writeDouble(hpregenperlevel);
        dest.writeDouble(armorperlevel);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ChampionStats> CREATOR = new Creator<ChampionStats>() {
        @Override
        public ChampionStats createFromParcel(Parcel in) {
            return new ChampionStats(in);
        }

        @Override
        public ChampionStats[] newArray(int size) {
            return new ChampionStats[size];
        }
    };

    public double getAttackrange() {
        return attackrange;
    }

    public void setAttackrange(double attackrange) {
        this.attackrange = attackrange;
    }

    public double getMpperlevel() {
        return mpperlevel;
    }

    public void setMpperlevel(double mpperlevel) {
        this.mpperlevel = mpperlevel;
    }

    public double getMp() {
        return mp;
    }

    public void setMp(double mp) {
        this.mp = mp;
    }

    public double getAttackdamage() {
        return attackdamage;
    }

    public void setAttackdamage(double attackdamage) {
        this.attackdamage = attackdamage;
    }

    public double getHp() {
        return hp;
    }

    public void setHp(double hp) {
        this.hp = hp;
    }

    public double getHpperlevel() {
        return hpperlevel;
    }

    public void setHpperlevel(double hpperlevel) {
        this.hpperlevel = hpperlevel;
    }

    public double getAttackdamageperlevel() {
        return attackdamageperlevel;
    }

    public void setAttackdamageperlevel(double attackdamageperlevel) {
        this.attackdamageperlevel = attackdamageperlevel;
    }

    public double getArmor() {
        return armor;
    }

    public void setArmor(double armor) {
        this.armor = armor;
    }

    public double getMpregenperlevel() {
        return mpregenperlevel;
    }

    public void setMpregenperlevel(double mpregenperlevel) {
        this.mpregenperlevel = mpregenperlevel;
    }

    public double getHpregen() {
        return hpregen;
    }

    public void setHpregen(double hpregen) {
        this.hpregen = hpregen;
    }

    public double getCritperlevel() {
        return critperlevel;
    }

    public void setCritperlevel(double critperlevel) {
        this.critperlevel = critperlevel;
    }

    public double getSpellblockperlevel() {
        return spellblockperlevel;
    }

    public void setSpellblockperlevel(double spellblockperlevel) {
        this.spellblockperlevel = spellblockperlevel;
    }

    public double getMpregen() {
        return mpregen;
    }

    public void setMpregen(double mpregen) {
        this.mpregen = mpregen;
    }

    public double getAttackspeedperlevel() {
        return attackspeedperlevel;
    }

    public void setAttackspeedperlevel(double attackspeedperlevel) {
        this.attackspeedperlevel = attackspeedperlevel;
    }

    public double getSpellblock() {
        return spellblock;
    }

    public void setSpellblock(double spellblock) {
        this.spellblock = spellblock;
    }

    public double getMovespeed() {
        return movespeed;
    }

    public void setMovespeed(double movespeed) {
        this.movespeed = movespeed;
    }

    public double getAttackspeedoffset() {
        return attackspeedoffset;
    }

    public void setAttackspeedoffset(double attackspeedoffset) {
        this.attackspeedoffset = attackspeedoffset;
    }

    public double getCrit() {
        return crit;
    }

    public void setCrit(double crit) {
        this.crit = crit;
    }

    public double getHpregenperlevel() {
        return hpregenperlevel;
    }

    public void setHpregenperlevel(double hpregenperlevel) {
        this.hpregenperlevel = hpregenperlevel;
    }

    public double getArmorperlevel() {
        return armorperlevel;
    }

    public void setArmorperlevel(double armorperlevel) {
        this.armorperlevel = armorperlevel;
    }

}
