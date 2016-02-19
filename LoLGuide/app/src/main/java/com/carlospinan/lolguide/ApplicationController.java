package com.carlospinan.lolguide;

import android.app.Application;
import android.content.Context;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import io.realm.Realm;

/**
 * @author Carlos Piñan
 */
public class ApplicationController extends Application {

    private static Context context;

    private Tracker mTracker;

    @Override
    public void onCreate() {
        super.onCreate();
//        Fabric.with(this, new Crashlytics());
        context = this;
    }

    public static Context getContext() {
        return context;
    }

    // For testing purposes.
    public static void setContext(Context mContext) {
        context = mContext;
    }

    public static Realm getRealm() {
        return Realm.getInstance(getContext());
    }

    /**
     * Gets the default {@link Tracker} for this {@link Application}.
     *
     * @return tracker
     */
    synchronized public Tracker getDefaultTracker() {
        if (mTracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
            mTracker = analytics.newTracker(getString(R.string.analytics_id));
        }
        return mTracker;
    }
}
