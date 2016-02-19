package com.carlospinan.lolguide.listeners;

import com.google.android.gms.analytics.Tracker;

/**
 * @author Carlos Piñan
 */
public interface OnFragmentListener {

    boolean isTwoPane();

    void onNotification(String message);

    Tracker getTracker();
}
