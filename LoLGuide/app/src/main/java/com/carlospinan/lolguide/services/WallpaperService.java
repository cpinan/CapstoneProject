package com.carlospinan.lolguide.services;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

import com.carlospinan.lolguide.ApplicationController;
import com.carlospinan.lolguide.R;
import com.carlospinan.lolguide.activities.SkinsActivity;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Carlos Piñan
 */
public class WallpaperService extends IntentService {

    private static final int NOTIFICATION_ID = 7777;
    public static final String IMAGE_PATH_KEY = "imagePathKey";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public WallpaperService() {
        super(WallpaperService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String settingWallpaper = getString(R.string.setting_wallpaper);
        String wallpaperReady = getString(R.string.wallpaper_ready);
        String errorWallpaper = getString(R.string.error_wallpaper);

        showNotification(settingWallpaper, null);
        SkinsActivity.launchReceiver(getApplicationContext(), settingWallpaper);

        String imagePath = intent.getExtras().getString(IMAGE_PATH_KEY);
        URL url;
        try {
            url = new URL(imagePath);
            Bitmap bitmap = BitmapFactory.decodeStream(url.openStream());

            WallpaperManager myWallpaperManager
                    = WallpaperManager.getInstance(ApplicationController.getContext());
            try {
                myWallpaperManager.setBitmap(bitmap);
                showNotification(wallpaperReady, bitmap);
                SkinsActivity.launchReceiver(getApplicationContext(), wallpaperReady);
            } catch (IOException e) {
                showNotification(errorWallpaper, null);
                SkinsActivity.launchReceiver(getApplicationContext(), errorWallpaper);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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
