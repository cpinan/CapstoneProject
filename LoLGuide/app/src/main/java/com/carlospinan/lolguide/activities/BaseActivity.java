package com.carlospinan.lolguide.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * @author Carlos Piñan
 */
public class BaseActivity extends AppCompatActivity {

    protected boolean isTwoPane;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isTwoPane = true;
    }
}
