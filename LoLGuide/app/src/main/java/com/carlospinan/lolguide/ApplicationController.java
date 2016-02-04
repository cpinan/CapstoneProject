package com.carlospinan.lolguide;

import android.app.Application;
import android.content.Context;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

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
}
