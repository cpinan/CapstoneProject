package com.carlospinan.lolguide.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.carlospinan.lolguide.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Carlos Piñan
 */
public class SkinsActivity extends AppCompatActivity {

    @Bind(R.id.skinViewPager)
    ViewPager skinViewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skins_champion);
        ButterKnife.bind(this);
    }
}
