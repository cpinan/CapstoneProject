package com.carlospinan.lolguide.services;

import android.test.AndroidTestCase;
import android.util.Patterns;

import com.carlospinan.lolguide.ApplicationController;
import com.carlospinan.lolguide.data.Globals;
import com.carlospinan.lolguide.data.enums.ChampDataEnum;
import com.carlospinan.lolguide.data.enums.RegionEnum;
import com.carlospinan.lolguide.data.models.Champion;
import com.carlospinan.lolguide.data.models.ChampionSpell;
import com.carlospinan.lolguide.data.models.LOLData;
import com.carlospinan.lolguide.data.responses.ChampionsResponse;
import com.carlospinan.lolguide.helpers.APIHelper;
import com.carlospinan.lolguide.helpers.Helper;
import com.carlospinan.lolguide.helpers.StorageHelper;
import com.carlospinan.lolguide.helpers.lolapi.ServiceLolStaticAPI;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;


/**
 * @author Carlos Piñan
 */
public class LOLStaticDataTesting extends AndroidTestCase {

    private static int CHAMPION_ID_TEST = 126; // Pantheon

    private RegionEnum defaultRegion;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        ApplicationController.setContext(getContext());
        defaultRegion = StorageHelper.get().getRegion();
    }

    public void testGetChampionsImage() throws IOException {
        Globals.testLog("testGetChampionsImage init");
        ServiceLolStaticAPI service = APIHelper.get().lolStaticAPI();
        Call<ChampionsResponse> call = service.api().getChampions(
                StorageHelper.get().getRegion(),
                Helper.get().getCodeLanguage(),
                null,
                null,
                Helper.get().arrayStringsToStringByComma(
                        ChampDataEnum.image,
                        ChampDataEnum.skins,
                        ChampDataEnum.info,
                        ChampDataEnum.passive,
                        ChampDataEnum.spells
                )
        );
        Response<ChampionsResponse> response = call.execute();
        assertNotNull(response);
        assertNotNull(response.body());
        ChampionsResponse championsResponse = response.body();
        assertNotNull(championsResponse.getType());
        assertNotNull(championsResponse.getVersion());
        assertNotNull(championsResponse.getData());
        assertFalse(championsResponse.getData().isEmpty());

        Globals.testLog("testGetChampionsImage end");
    }

    public void testGetChampionById() throws IOException {
        Globals.testLog("testGetChampionById init");
        ServiceLolStaticAPI service = APIHelper.get().lolStaticAPI();
        Call<Champion> call = service.api().getChampion(
                StorageHelper.get().getRegion(),
                CHAMPION_ID_TEST,
                Helper.get().getCodeLanguage(),
                null,
                null,
                Helper.get().arrayStringsToStringByComma(
                        ChampDataEnum.image, ChampDataEnum.spells,
                        ChampDataEnum.allytips, ChampDataEnum.enemytips,
                        ChampDataEnum.info, ChampDataEnum.lore,
                        ChampDataEnum.passive, ChampDataEnum.skins,
                        ChampDataEnum.stats, ChampDataEnum.tags,
                        ChampDataEnum.blurb
                )

        );
        Response<Champion> response = call.execute();
        assertNotNull(response);
        assertNotNull(response.body());
        Champion champion = response.body();
        assertNotNull(champion.getStats());

        String passiveImage = champion.getPassive().getImage().getFull();
        assertNotNull(passiveImage);
        Globals.testLog(passiveImage);

        String passiveImageUrl = StorageHelper.get().getPassiveAbilityUrl(passiveImage);
        assertNotNull(passiveImageUrl);
        Globals.testLog(passiveImageUrl);
        assertTrue(passiveImageUrl.matches(Patterns.WEB_URL.pattern()));

        List<ChampionSpell> spells = champion.getSpells();
        assertNotNull(spells);
        assertTrue(spells.size() > 0);
        for (ChampionSpell spell : spells) {
            String spellImage = spell.getImage().getFull();
            assertNotNull(spellImage);
            String spellImageUrl = StorageHelper.get().getChampionAbilityUrl(spellImage);
            assertNotNull(spellImageUrl);
            Globals.testLog(spellImageUrl);
            assertTrue(spellImageUrl.matches(Patterns.WEB_URL.pattern()));
        }

        Globals.testLog("testGetChampionById init");
    }

    public void testGetLanguages() throws IOException {
        Globals.testLog("testGetLanguages init");

        ServiceLolStaticAPI service = APIHelper.get().lolStaticAPI();
        Call<List<String>> languages = service.api().getLanguages(defaultRegion);
        Response<List<String>> response = languages.execute();
        assertNotNull(response);
        assertNotNull(response.body());
        assertTrue(response.body().size() > 0);
        Globals.testLog(response.body().toString());

        Globals.testLog("testGetLanguages end");
    }

    public void testGetRealm() throws IOException {
        Globals.testLog("testGetRealm init");

        ServiceLolStaticAPI service = APIHelper.get().lolStaticAPI();
        Call<LOLData> call = service.api().getRealm(defaultRegion);
        Response<LOLData> response = call.execute();
        assertNotNull(response);
        assertNotNull(response.body());
        LOLData realm = response.body();
        assertNotNull(realm.getCurrentRealVersion());
        assertNotNull(realm.getLatestChangedLOLRealmVersion());

        Globals.testLog("testGetRealm end");
    }

    public void testGetVersions() throws IOException {
        Globals.testLog("testGetVersions init");

        ServiceLolStaticAPI service = APIHelper.get().lolStaticAPI();
        Call<List<String>> call = service.api().getVersions(defaultRegion);
        Response<List<String>> response = call.execute();
        assertNotNull(response);
        assertNotNull(response.body());
        assertTrue(response.body().size() > 0);
        Globals.testLog(response.body().toString());

        Globals.testLog("testGetVersions end");
    }
}
