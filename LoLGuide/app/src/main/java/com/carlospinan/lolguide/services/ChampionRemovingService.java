package com.carlospinan.lolguide.services;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.carlospinan.lolguide.ApplicationController;
import com.carlospinan.lolguide.R;
import com.carlospinan.lolguide.data.Globals;
import com.carlospinan.lolguide.data.models.realm.RealmChampion;
import com.carlospinan.lolguide.helpers.RealmHelper;
import com.carlospinan.lolguide.helpers.StorageHelper;

import io.realm.Realm;

/**
 * @author Carlos Piñan
 */
public class ChampionRemovingService extends IntentService {

    private static final int NOTIFICATION_ID = 77777;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public ChampionRemovingService() {
        super(ChampionRemovingService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        showNotification(getString(R.string.removing_champion));
        final Realm realm = ApplicationController.getRealm();
        Integer championId = intent.getIntExtra(Globals.INDEX_KEY, -1);
        if (championId != null && championId != -1 && !StorageHelper.get().isChampionSaving(championId)) {
            StorageHelper.get().championIsSaving(championId, true);
            RealmChampion realmChampion = realm.where(RealmChampion.class).equalTo("championId", championId).findFirst();
            if (realmChampion != null) {
                RealmHelper.get().removeChampion(realmChampion);
                showNotification(getString(R.string.champion_removed));
            } else {
                showNotification(getString(R.string.error_removing_champion));
            }
            StorageHelper.get().championIsSaving(championId, false);
        } else {
            showNotification(getString(R.string.error_removing_champion));
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