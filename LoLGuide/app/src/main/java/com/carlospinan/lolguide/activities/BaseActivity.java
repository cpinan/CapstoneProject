package com.carlospinan.lolguide.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.carlospinan.lolguide.ApplicationController;
import com.google.android.gms.analytics.Tracker;

/**
 * @author Carlos Piñan
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected boolean isTwoPane;
    protected Tracker mTracker;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isTwoPane = true;
        mTracker = ((ApplicationController) getApplication()).getDefaultTracker();
    }
}
