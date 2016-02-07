package com.carlospinan.lolguide;

import android.app.Application;
import android.content.Context;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;
import io.realm.Realm;

/**
 * @author Carlos Piñan
 */
public class ApplicationController extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        context = this;
    }

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context mContext) {
        context = mContext;
    }

    public static Realm getRealm() {
        return Realm.getInstance(getContext());
    }
}
