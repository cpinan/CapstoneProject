package com.carlospinan.lolguide.services;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

import com.carlospinan.lolguide.R;
import com.carlospinan.lolguide.data.Globals;
import com.carlospinan.lolguide.data.enums.ChampDataEnum;
import com.carlospinan.lolguide.data.models.Champion;
import com.carlospinan.lolguide.helpers.APIHelper;
import com.carlospinan.lolguide.helpers.ChampionHelper;
import com.carlospinan.lolguide.helpers.Helper;
import com.carlospinan.lolguide.helpers.RealmHelper;
import com.carlospinan.lolguide.helpers.StorageHelper;
import com.carlospinan.lolguide.providers.LolStaticDataAPI;

import java.io.IOException;
import java.net.URL;

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
        showNotification(getString(R.string.saving_champion));
        Integer championId = intent.getIntExtra(Globals.INDEX_KEY, -1);
        if (championId != null && championId != -1) {
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



                URL url = new URL(null);
                Bitmap passiveBitmap = BitmapFactory.decodeStream(url.openStream());
                /*
        o.setPassiveUri(c.getPassiveUri());
        o.setPortraitUri(c.getPortraitUri());
        o.setAbilitiesUris(c.getAbilitiesUris());
        o.setSkinsUris(c.getSkinsUris()); // Just default
                 */

                RealmHelper.get().saveChampion(ChampionHelper.fromChampionToRealm(response));

            } catch (IOException e) {
                e.printStackTrace();
                showNotification(getString(R.string.error_saving_champion));
            }
        } else {
            showNotification(getString(R.string.error_saving_champion));
        }
    }

    private void showNotification(String message) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(getString(R.string.app_name))
                        .setContentText(message);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }
}
