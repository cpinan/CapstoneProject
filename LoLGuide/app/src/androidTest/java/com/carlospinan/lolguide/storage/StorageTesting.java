package com.carlospinan.lolguide.storage;

import android.test.AndroidTestCase;

import com.carlospinan.lolguide.ApplicationController;
import com.carlospinan.lolguide.data.Globals;
import com.carlospinan.lolguide.data.enums.ChampDataEnum;
import com.carlospinan.lolguide.data.models.champion.Champion;
import com.carlospinan.lolguide.helpers.APIHelper;
import com.carlospinan.lolguide.helpers.lolapi.ServiceLolStaticAPI;

import java.io.IOException;
import java.util.List;

import retrofit.Call;
import retrofit.Response;

/**
 * @author Carlos Piñan
 */
public class StorageTesting extends AndroidTestCase {

    private static int CHAMPION_ID_TEST = 80; // Pantheon

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        ApplicationController.setContext(getContext());
    }

    public void testChampionByIdAndLocalStorage() throws IOException {
        Globals.testLog("testChampionByIdAndLocalStorage init");
        ServiceLolStaticAPI service = APIHelper.get().lolStaticAPI();
        Call<Champion> call = service.getChampion(CHAMPION_ID_TEST, ChampDataEnum.all);
        Response<Champion> response = call.execute();
        assertNotNull(response);
        assertNotNull(response.body());
        Champion champion = response.body();
        champion.save();

//        List<Champion> championList = Champion.find(
//                Champion.class,
//                "id = ?",
//                String.valueOf(champion.getId()));
//        Champion champion1 = Champion.findById(Champion.class, champion.getId());
//        assertNotNull(champion1);
//        Globals.testLog(champion1.getName());
        List<Champion> championList = Champion.listAll(Champion.class);
        assertNotNull(championList);
        assertTrue(championList.size() > 0);

        Globals.testLog("testChampionByIdAndLocalStorage init");
    }

}
