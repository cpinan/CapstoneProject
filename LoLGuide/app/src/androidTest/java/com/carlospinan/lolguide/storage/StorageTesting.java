package com.carlospinan.lolguide.storage;

import android.test.AndroidTestCase;

import com.carlospinan.lolguide.ApplicationController;
import com.carlospinan.lolguide.data.Globals;
import com.carlospinan.lolguide.data.enums.ChampDataEnum;
import com.carlospinan.lolguide.data.models.Champion;
import com.carlospinan.lolguide.data.models.realm.RealmChampion;
import com.carlospinan.lolguide.helpers.APIHelper;
import com.carlospinan.lolguide.helpers.ChampionHelper;
import com.carlospinan.lolguide.helpers.Helper;
import com.carlospinan.lolguide.helpers.RealmHelper;
import com.carlospinan.lolguide.helpers.StorageHelper;
import com.carlospinan.lolguide.helpers.lolapi.ServiceLolStaticAPI;

import java.io.IOException;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Response;

/**
 * @author Carlos Piñan
 */
public class StorageTesting extends AndroidTestCase {

    private static int CHAMPION_ID_TEST = 80; // Pantheon

    private Realm realm;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        ApplicationController.setContext(getContext());
        realm = Realm.getInstance(getContext());
    }

    public void testChampionByIdAndLocalStorage() {
        Globals.testLog("testChampionByIdAndLocalStorage init");
        ServiceLolStaticAPI service = APIHelper.get().lolStaticAPI();
        Call<Champion> call = service.api().getChampion(
                StorageHelper.get().getRegion(),
                CHAMPION_ID_TEST,
                Helper.get().getCodeLanguage(),
                null,
                null,
                ChampDataEnum.all.toString()
        );
        Response<Champion> response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertNotNull(response);
        assertNotNull(response.body());
        Champion champion = response.body();
        assertNotNull(champion.getChampionId());
        Globals.testLog("getChampionId: " + String.valueOf(champion.getChampionId()));

        RealmHelper.get().saveChampion(ChampionHelper.fromChampionToRealm(champion));

        RealmResults<RealmChampion> champions = realm.where(RealmChampion.class).findAll();
        assertTrue(champions.size() > 0);

        RealmChampion realmChampion = realm.where(RealmChampion.class).equalTo("championId", CHAMPION_ID_TEST).findFirst();
        assertNotNull(realmChampion);

        Champion localChampion = ChampionHelper.fromRealmToChampion(realmChampion);
        assertNotNull(localChampion);

        Globals.testLog("testChampionByIdAndLocalStorage end");
    }


}
