package com.carlospinan.lolguide.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import com.carlospinan.lolguide.ApplicationController;

/**
 * @author Carlos Piñan
 */
public class StorageHelper {

    // Storage Key
    private static final String STORAGE_KEY = "com.carlospinan.lolguide.storage";

    // Keys

    // Manager
    private SharedPreferences getStorage() {
        Context context = ApplicationController.getContext();
        return context.getSharedPreferences(STORAGE_KEY, Context.MODE_PRIVATE);
    }

    public void saveString(String key, String value) {
        SharedPreferences.Editor editor = getStorage().edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String getString(String key, String defaultValue) {
        return getStorage().getString(key, defaultValue);
    }


}
