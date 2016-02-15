package com.carlospinan.lolguide.helpers;

import com.carlospinan.lolguide.data.enums.ChampDataEnum;
import com.carlospinan.lolguide.data.models.Champion;
import com.carlospinan.lolguide.data.models.ChampionInformation;
import com.carlospinan.lolguide.data.models.ChampionPassive;
import com.carlospinan.lolguide.data.models.ChampionSkin;
import com.carlospinan.lolguide.data.models.ChampionSpell;
import com.carlospinan.lolguide.data.models.ChampionSpellTip;
import com.carlospinan.lolguide.data.models.ChampionSpellVar;
import com.carlospinan.lolguide.data.models.ChampionStats;
import com.carlospinan.lolguide.data.models.LOLImage;
import com.carlospinan.lolguide.data.models.realm.RealmChampion;
import com.carlospinan.lolguide.data.models.realm.RealmChampionInformation;
import com.carlospinan.lolguide.data.models.realm.RealmChampionPassive;
import com.carlospinan.lolguide.data.models.realm.RealmChampionSkin;
import com.carlospinan.lolguide.data.models.realm.RealmChampionSpell;
import com.carlospinan.lolguide.data.models.realm.RealmChampionSpellTip;
import com.carlospinan.lolguide.data.models.realm.RealmChampionSpellVar;
import com.carlospinan.lolguide.data.models.realm.RealmChampionStats;
import com.carlospinan.lolguide.data.models.realm.RealmLOLImage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.realm.RealmList;

/**
 * @author Carlos Piñan
 */
public class ChampionHelper {

    public static final String DELIMITER = "@";
    public static final String DOUBLE_DELIMITER = "#";

    // From RealmChampion to Champion
    public static Champion fromRealmToChampion(RealmChampion r) {
        Champion c = new Champion();
        c.setChampionId(r.getChampionId());
        c.setTitle(r.getTitle());
        c.setName(r.getName());
        c.setKey(r.getKey());
        c.setChampionTags(getListStringFromString(r.getChampionTags()));
        c.setStats(getChampionStats(r.getStats()));
        c.setEnemyTips(getListStringFromString(r.getEnemyTips()));
        c.setAllyTips(getListStringFromString(r.getAllyTips()));
        c.setImage(getLOLImage(r.getImage()));
        c.setBlurb(r.getBlurb());
        c.setLore(r.getLore());
        c.setInfo(getChampInfo(r.getInfo()));
        c.setPartype(r.getPartype());
        c.setSkins(getChampSkins(r.getSkins()));
        c.setPassive(getChampPassive(r.getPassive()));
        c.setSpells(getChampSpells(r.getSpells()));
        c.setPortraitUri(r.getPortraitUri());
        c.setSkinsUris(r.getSkinsUris());
        c.setAbilitiesUris(r.getAbilitiesUris());
        return c;
    }

    private static ChampionSpell getChampSpell(RealmChampionSpell rs) {
        ChampionSpell s = new ChampionSpell();
        Object rangeObject;
        List<String> rangeList = getListStringFromString(rs.getRange());
        if (rangeList.size() == 1) {
            rangeObject = rangeList.get(0);
        } else {
            rangeObject = rangeList;
        }
        s.setRange(rangeObject);
        s.setLeveltip(getRealmSpellTip(rs.getLeveltip()));
        s.setResource(rs.getResource());
        s.setMaxrank(rs.getMaxrank());
        s.setEffectBurn(getListStringFromString(rs.getEffectBurn()));
        s.setImage(getLOLImage(rs.getImage()));
        s.setCooldown(getListDoubleFromString(rs.getCooldown()));
        s.setCost(getListIntegerFromString(rs.getCost()));

        List<ChampionSpellVar> l1 = new ArrayList<>();
        for (RealmChampionSpellVar var : rs.getVars()) {
            l1.add(getChampSpellVar(var));
        }
        s.setVars(l1);

        s.setSanitizedDescription(rs.getSanitizedDescription());
        s.setRangeBurn(rs.getRangeBurn());
        s.setCostType(rs.getCostType());
        s.setEffect(toDoubleListFromString(rs.getEffect()));
        s.setCooldownBurn(rs.getCooldownBurn());
        s.setDescription(rs.getDescription());
        s.setName(rs.getName());
        s.setSanitizedTooltip(rs.getSanitizedTooltip());
        s.setKey(rs.getKey());
        s.setCostBurn(rs.getCostBurn());
        s.setTooltip(rs.getTooltip());

        return s;
    }

    private static ChampionSpellVar getChampSpellVar(RealmChampionSpellVar rsv) {
        ChampionSpellVar csv = new ChampionSpellVar();
        csv.setCoeff(getListDoubleFromString(rsv.getCoeff()));
        csv.setDyn(rsv.getDyn());
        csv.setKey(rsv.getKey());
        csv.setLink(rsv.getLink());
        csv.setRanksWith(rsv.getRanksWith());
        return csv;
    }

    private static ChampionSpellTip getRealmSpellTip(RealmChampionSpellTip rst) {
        ChampionSpellTip cst = new ChampionSpellTip();
        cst.setEffect(getListStringFromString(rst.getEffect()));
        cst.setLabel(getListStringFromString(rst.getLabel()));
        return cst;
    }

    private static List<ChampionSpell> getChampSpells(RealmList<RealmChampionSpell> spells) {
        List<ChampionSpell> l = new ArrayList<>();
        for (RealmChampionSpell s : spells) {
            l.add(getChampSpell(s));
        }
        return l;
    }

    private static ChampionPassive getChampPassive(RealmChampionPassive rp) {
        ChampionPassive cp = new ChampionPassive();
        cp.setDescription(rp.getDescription());
        cp.setImage(getLOLImage(rp.getImage()));
        cp.setName(rp.getName());
        cp.setSanitizedDescription(rp.getSanitizedDescription());
        return cp;
    }

    private static List<ChampionSkin> getChampSkins(RealmList<RealmChampionSkin> skins) {
        List<ChampionSkin> l = new ArrayList<>();
        for (RealmChampionSkin s : skins) {
            l.add(getRealmSkin(s));
        }
        return l;
    }

    private static ChampionSkin getRealmSkin(RealmChampionSkin r) {
        ChampionSkin s = new ChampionSkin();
        s.setId(r.getId());
        s.setNum(r.getNum());
        s.setName(r.getName());
        return s;
    }

    private static ChampionInformation getChampInfo(RealmChampionInformation ri) {
        ChampionInformation ci = new ChampionInformation();
        ci.setAttack(ri.getAttack());
        ci.setDefense(ri.getDefense());
        ci.setDifficulty(ri.getDifficulty());
        ci.setMagic(ri.getMagic());
        return ci;
    }

    private static LOLImage getLOLImage(RealmLOLImage r) {
        LOLImage i = new LOLImage();
        i.setFull(r.getFull());
        i.setGroup(r.getGroup());
        i.setH(r.getH());
        i.setSprite(r.getSprite());
        i.setW(r.getW());
        i.setX(r.getX());
        i.setY(r.getY());
        return i;
    }

    private static ChampionStats getChampionStats(RealmChampionStats r) {
        ChampionStats s = new ChampionStats();
        s.setAttackrange(r.getAttackrange());
        s.setMpperlevel(r.getMpperlevel());
        s.setMp(r.getMp());
        s.setAttackdamage(r.getAttackdamage());
        s.setHp(r.getHp());
        s.setHpperlevel(r.getHpperlevel());
        s.setAttackdamageperlevel(r.getAttackdamageperlevel());
        s.setArmor(r.getArmor());
        s.setMpregenperlevel(r.getMpregenperlevel());
        s.setHpregen(r.getHpregen());
        s.setCritperlevel(r.getCritperlevel());
        s.setSpellblockperlevel(r.getSpellblockperlevel());
        s.setMpregen(r.getMpregen());
        s.setAttackspeedperlevel(r.getAttackspeedperlevel());
        s.setSpellblock(r.getSpellblock());
        s.setMovespeed(r.getMovespeed());
        s.setAttackspeedoffset(r.getAttackspeedoffset());
        s.setCrit(r.getCrit());
        s.setHpregenperlevel(r.getHpregenperlevel());
        s.setArmorperlevel(r.getArmorperlevel());
        return s;
    }

    // From Champion to RealmChampion
    public static RealmChampion fromChampionToRealm(Champion c) {
        RealmChampion o = new RealmChampion();
        o.setChampionId(c.getChampionId());
        o.setTitle(c.getTitle());
        o.setName(c.getName());
        o.setKey(c.getKey());
        o.setChampionTags(getStringFromList(c.getChampionTags()));
        o.setStats(getRealmStats(c.getStats()));
        o.setEnemyTips(getStringFromList(c.getEnemyTips()));
        o.setAllyTips(getStringFromList(c.getAllyTips()));
        o.setImage(c.getImage().getRealmImage());
        o.setBlurb(c.getBlurb());
        o.setLore(c.getLore());
        o.setInfo(getRealmInfo(c.getInfo()));
        o.setPartype(c.getPartype());
        o.setSkins(getRealmSkins(c.getSkins()));
        o.setPassive(getRealmPassive(c.getPassive()));
        o.setSpells(getRealmSpell(c.getSpells()));
        o.setPassiveUri(c.getPassiveUri());
        o.setPortraitUri(c.getPortraitUri());
        o.setAbilitiesUris(c.getAbilitiesUris());
        o.setSkinsUris(c.getSkinsUris()); // Just default
        return o;
    }

    private static RealmChampionStats getRealmStats(ChampionStats s) {
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

    private static RealmChampionInformation getRealmInfo(ChampionInformation i) {
        RealmChampionInformation r = new RealmChampionInformation();
        r.setAttack(i.getAttack());
        r.setDefense(i.getDefense());
        r.setDifficulty(i.getDifficulty());
        r.setMagic(i.getMagic());
        return r;
    }

    private static RealmList<RealmChampionSkin> getRealmSkins(List<ChampionSkin> skins) {
        RealmList<RealmChampionSkin> l = new RealmList<>();
        for (ChampionSkin s : skins) {
            l.add(s.getRealmSkin());
        }
        return l;
    }

    private static RealmChampionPassive getRealmPassive(ChampionPassive p) {
        RealmChampionPassive r = new RealmChampionPassive();
        r.setDescription(p.getDescription());
        r.setImage(p.getImage().getRealmImage());
        r.setName(p.getName());
        r.setSanitizedDescription(p.getSanitizedDescription());
        return r;
    }

    private static RealmList<RealmChampionSpell> getRealmSpell(List<ChampionSpell> spells) {
        RealmList<RealmChampionSpell> l = new RealmList<>();
        for (ChampionSpell s : spells) {
            l.add(s.getRealmSpell());
        }
        return l;
    }

    // Utilities
    public static ChampDataEnum getChampDataFromString(String value) {
        try {
            return ChampDataEnum.valueOf(value);
        } catch (Exception e) {
            return null;
        }
    }

    // String array utility
    public static String getStringFromList(List<String> list) {
        String response = "";
        if (list != null && !list.isEmpty()) {
            if (list.size() == 1) {
                response = list.get(0);
            } else {
                for (Object object : list) {
                    response += object + DELIMITER;
                }
                response = response.substring(0, response.length() - DELIMITER.length());
            }
        }
        return response;
    }

    public static String getStringFromDoubleList(List<Double> list) {
        List<String> newList = new ArrayList<>();
        for (Double d : list) {
            newList.add(String.valueOf(d));
        }
        return getStringFromList(newList);
    }

    public static String getStringFromIntegerList(List<Integer> list) {
        List<String> newList = new ArrayList<>();
        for (Integer i : list) {
            newList.add(String.valueOf(i));
        }
        return getStringFromList(newList);
    }

    public static String stringFromDoubleList(List<List<Double>> list) {
        String response = "";
        if (list != null && !list.isEmpty()) {
            for (List<Double> l : list) {
                if (l == null) {
                    response += "" + DOUBLE_DELIMITER;
                } else {
                    response += getStringFromDoubleList(l) + DOUBLE_DELIMITER;
                }
            }
            response = response.substring(0, response.length() - DOUBLE_DELIMITER.length());
        }
        return response;
    }

    public static List<List<Double>> toDoubleListFromString(String string) {
        List<List<Double>> list = new ArrayList<>();
        if (string != null) {
            String[] split = string.split(DOUBLE_DELIMITER);
            List<String> stringList = Arrays.asList(split);
            for (String s : stringList) {
                List<Double> singleList = new ArrayList<>();
                List<String> stringList1 = getListStringFromString(s);
                for (String s1 : stringList1) {
                    if (s1 == null || s1.isEmpty() || s1.equalsIgnoreCase("null")) {
                        singleList.add(null);
                    } else {
                        singleList.add(Double.parseDouble(s1));
                    }
                }
                list.add(singleList);
            }
        }
        return list;
    }

    public static List<String> getListStringFromString(String string) {
        String[] split = string.split(DELIMITER);
        return Arrays.asList(split);
    }

    public static List<Double> getListDoubleFromString(String string) {
        String[] split = string.split(DELIMITER);
        List<Double> list = new ArrayList<>();
        for (String s : split) {
            list.add(Double.valueOf(s));
        }
        return list;
    }

    public static List<Integer> getListIntegerFromString(String string) {
        String[] split = string.split(DELIMITER);
        List<Integer> list = new ArrayList<>();
        for (String s : split) {
            list.add(Integer.valueOf(s));
        }
        return list;
    }
}
