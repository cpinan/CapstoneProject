package com.carlospinan.lolguide.services;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.carlospinan.lolguide.R;
import com.carlospinan.lolguide.data.Globals;
import com.carlospinan.lolguide.data.models.Champion;

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
//        showNotification
        Champion champion = intent.getParcelableExtra(Globals.PARCEABLE_CHAMPION_KEY);
        if (champion != null) {

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
