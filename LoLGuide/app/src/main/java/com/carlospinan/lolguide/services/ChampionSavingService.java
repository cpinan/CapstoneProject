package com.carlospinan.lolguide.services;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

import com.carlospinan.lolguide.ApplicationController;
import com.carlospinan.lolguide.R;
import com.carlospinan.lolguide.data.Globals;
import com.carlospinan.lolguide.data.enums.ChampDataEnum;
import com.carlospinan.lolguide.data.models.Champion;
import com.carlospinan.lolguide.data.models.ChampionSpell;
import com.carlospinan.lolguide.data.models.realm.RealmChampion;
import com.carlospinan.lolguide.helpers.APIHelper;
import com.carlospinan.lolguide.helpers.ChampionHelper;
import com.carlospinan.lolguide.helpers.Helper;
import com.carlospinan.lolguide.helpers.RealmHelper;
import com.carlospinan.lolguide.helpers.StorageHelper;
import com.carlospinan.lolguide.providers.LolStaticDataAPI;

import java.io.IOException;
import java.net.URL;

import io.realm.Realm;
import retrofit.Call;
import retrofit.Response;

/**
 * @author Carlos Piñan
 */
public class ChampionSavingService extends IntentService {

    private static final int NOTIFICATION_ID = 77777;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public ChampionSavingService() {
        super(ChampionSavingService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        final Realm realm = ApplicationController.getRealm();
        showNotification(getString(R.string.saving_champion), null);
        Integer championId = intent.getIntExtra(Globals.INDEX_KEY, -1);
        if (championId != null && championId != -1 && !StorageHelper.get().isChampionSaving(championId)) {
            StorageHelper.get().championIsSaving(championId, true);
            LolStaticDataAPI api = APIHelper.get().lolStaticAPI().api();
            Call<Champion> call = api.getChampion(
                    StorageHelper.get().getRegion(),
                    championId,
                    Helper.get().getCodeLanguage(),
                    null,
                    null,
                    ChampDataEnum.all.toString()
            );
            try {
                Response<Champion> execute = call.execute();
                Champion response = execute.body();

                // Passive Image
                String passiveImage = response.getPassive().getImage().getFull();
                String passiveImageUrl = StorageHelper.get().getPassiveAbilityUrl(passiveImage);
                URL passiveUrl = new URL(passiveImageUrl);
                Bitmap passiveBitmap = BitmapFactory.decodeStream(passiveUrl.openStream());
                response.setPassiveUri(Helper.get().saveBitmap(passiveBitmap, passiveImage));

                // Portrait Uri
                String portraitImage = response.getImage().getFull();
                String portraitImageUrl = StorageHelper.get().getChampionPortraitUrl(portraitImage);
                URL portraitUrl = new URL(portraitImageUrl);
                Bitmap portraitBitmap = BitmapFactory.decodeStream(portraitUrl.openStream());
                response.setPortraitUri(Helper.get().saveBitmap(portraitBitmap, portraitImage));

                String abilitiesUris = "";
                for (ChampionSpell spell : response.getSpells()) {
                    String spellImage = spell.getImage().getFull();
                    String spellImageUrl = StorageHelper.get().getChampionAbilityUrl(
                            spellImage
                    );
                    URL spellUrl = new URL(spellImageUrl);
                    Bitmap spellBitmap = BitmapFactory.decodeStream(spellUrl.openStream());
                    String spellPathFile = Helper.get().saveBitmap(spellBitmap, spellImage);
                    abilitiesUris += spellPathFile + ChampionHelper.DELIMITER;
                }
                abilitiesUris = abilitiesUris.substring(0, abilitiesUris.length() - ChampionHelper.DELIMITER.length());
                response.setAbilitiesUris(abilitiesUris);
                response.setFavorite(true);

                RealmChampion realmChampion = realm.where(RealmChampion.class).equalTo("championId", championId).findFirst();
                if (realmChampion == null) {
                    RealmHelper.get().saveChampion(ChampionHelper.fromChampionToRealm(response));
                } else {
                    realm.beginTransaction();
                    realmChampion.setPassiveUri(response.getPassiveUri());
                    realmChampion.setPortraitUri(response.getPortraitUri());
//                    realmChampion.setSkinsUris(response.getSkinsUris());
                    realmChampion.setAbilitiesUris(response.getAbilitiesUris());
                    realmChampion.setFavorite(true);
                    realm.commitTransaction();

                    Champion c = ChampionHelper.fromRealmToChampion(realmChampion);
                    if (c != null) {

                    }
                }
                showNotification(getString(R.string.champion_saved), portraitBitmap);
                StorageHelper.get().championIsSaving(championId, false);
            } catch (IOException e) {
                e.printStackTrace();
                showNotification(getString(R.string.error_saving_champion), null);
                StorageHelper.get().championIsSaving(championId, false);
            }
        } else {
            showNotification(getString(R.string.error_saving_champion), null);
        }
    }

    private void showNotification(String message, Bitmap bitmap) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(getString(R.string.app_name))
                        .setContentText(message);
        if (bitmap != null) {
            mBuilder.setLargeIcon(bitmap);
        }
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }
}
