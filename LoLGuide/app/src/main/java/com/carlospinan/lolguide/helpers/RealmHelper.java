package com.carlospinan.lolguide.helpers;

import com.carlospinan.lolguide.ApplicationController;
import com.carlospinan.lolguide.data.Globals;
import com.carlospinan.lolguide.data.models.realm.RealmChampion;

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
                    realm.copyToRealm(c);
                    realm.commitTransaction();
                }
            } catch (Exception e) {
                Globals.testLog(e.getLocalizedMessage());
            }
        }
    }

}
