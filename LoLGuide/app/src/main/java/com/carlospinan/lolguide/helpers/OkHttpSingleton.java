package com.carlospinan.lolguide.helpers;

import android.os.Environment;

import java.io.File;

import okhttp3.Cache;
import okhttp3.OkHttpClient;

/**
 * @author Carlos Piñan
 */
public class OkHttpSingleton {

    private static OkHttpClient okHttpClient;

    private OkHttpSingleton() {
    }

    public static OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient();
            createCacheForOkHTTP();
        }
        return okHttpClient;
    }

    private static void createCacheForOkHTTP() {
        Cache cache;
        cache = new Cache(getDirectory(), 1024 * 1024 * 10);
//        okHttpClient.setCache(cache);
    }

    public static File getDirectory() {
        final File root = new File(Environment.getExternalStorageDirectory() + File.separator + "PM" + File.separator);
        root.mkdirs();
        final String filename = "PM.ok";
        final File sdImageMainDirectory = new File(root, filename);
        return sdImageMainDirectory;
    }

}
