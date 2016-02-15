package com.carlospinan.lolguide.helpers;

import com.carlospinan.lolguide.ApplicationController;
import com.carlospinan.lolguide.data.Globals;
import com.carlospinan.lolguide.data.models.realm.RealmChampion;
import com.carlospinan.lolguide.data.models.realm.RealmChampionSkin;
import com.carlospinan.lolguide.data.models.realm.RealmChampionSpell;
import com.carlospinan.lolguide.data.models.realm.RealmChampionSpellVar;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * @author Carlos Piñan
 */
public class RealmHelper {

    private static RealmHelper instance;

    private RealmHelper() { /* UNUSED */ }

    public static RealmHelper get() {
        if (instance == null) {
            instance = new RealmHelper();
        }
        return instance;
    }

    // Champion side
    public void saveChampion(RealmChampion c) {
        if (c != null) {
            try {
                Realm realm = ApplicationController.getRealm();
                RealmResults<RealmChampion> champions = realm.where(RealmChampion.class).equalTo("championId", c.getChampionId()).findAll();
                if (champions == null || champions.isEmpty()) {
                    realm.beginTransaction();
                    realm.copyToRealm(c.getStats());
                    realm.copyToRealm(c.getImage());
                    realm.copyToRealm(c.getInfo());
                    realm.copyToRealm(c.getPassive().getImage());
                    realm.copyToRealm(c.getPassive());
                    realm.copyToRealm(c.getSkins());
                    realm.copyToRealm(c.getSpells());
                    realm.copyToRealm(c);
                    realm.commitTransaction();
                }
            } catch (Exception e) {
                Globals.testLog(e.getLocalizedMessage());
            }
        }
    }

    public void removeChampion(RealmChampion c) {
        if (c != null) {
            try {
                Realm realm = ApplicationController.getRealm();
                realm.beginTransaction();
                c.getStats().removeFromRealm();
                c.getImage().removeFromRealm();
                c.getInfo().removeFromRealm();
                c.getPassive().getImage().removeFromRealm();
                c.getPassive().removeFromRealm();
                for (RealmChampionSkin skin : c.getSkins()) {
                    skin.removeFromRealm();
                }
                for (RealmChampionSpell spell : c.getSpells()) {
                    for (RealmChampionSpellVar var : spell.getVars()) {
                        var.removeFromRealm();
                    }
                    spell.removeFromRealm();
                }
                c.removeFromRealm();
                realm.commitTransaction();
                realm.refresh();
            } catch (Exception e) {
                Globals.testLog(e.getLocalizedMessage());
            }
        }
    }

}
