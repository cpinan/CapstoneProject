package com.carlospinan.lolguide.services;

import android.app.IntentService;
import android.content.Intent;

import com.carlospinan.lolguide.ApplicationController;
import com.carlospinan.lolguide.data.models.Champion;
import com.carlospinan.lolguide.data.models.LOLData;
import com.carlospinan.lolguide.data.models.realm.RealmChampion;
import com.carlospinan.lolguide.helpers.APIHelper;
import com.carlospinan.lolguide.helpers.ChampionHelper;
import com.carlospinan.lolguide.helpers.RealmHelper;
import com.carlospinan.lolguide.helpers.StorageHelper;
import com.carlospinan.lolguide.providers.LolStaticDataAPI;

import java.io.IOException;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Response;

/**
 * @author Carlos Piñan
 */
public class SaveChampionsService extends IntentService {

    public static List<Champion> championList;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public SaveChampionsService() {
        super(SaveChampionsService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        LolStaticDataAPI api = APIHelper.get().lolStaticAPI().api();
        Call<LOLData> apiRealm = api.getRealm(StorageHelper.get().getRegion());
        try {
            final Realm realm = ApplicationController.getRealm();
            Response<LOLData> execute = apiRealm.execute();
            LOLData body = execute.body();
            RealmResults<RealmChampion> realmChampions = realm.where(RealmChampion.class).findAll();
            boolean isSameVersion = body.getCurrentRealVersion().equalsIgnoreCase(StorageHelper.get().getVersion());
            if (realmChampions == null || realmChampions.isEmpty() || !isSameVersion) {
                if (!isSameVersion) {
                    StorageHelper.get().saveVersion(body.getCurrentRealVersion());
                }
                if (championList != null && !championList.isEmpty()) {
                    for (Champion c : championList) {
                        int championId = c.getChampionId();
                        RealmChampion realmChampion = realm.where(RealmChampion.class).equalTo("championId", championId).findFirst();
                        if (realmChampion == null) {
                            RealmHelper.get().saveChampion(ChampionHelper.fromChampionToRealm(c));
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
