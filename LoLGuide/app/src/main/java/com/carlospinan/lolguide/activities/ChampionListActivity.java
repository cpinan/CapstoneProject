package com.carlospinan.lolguide.activities;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.carlospinan.lolguide.R;
import com.carlospinan.lolguide.listeners.OnFragmentListener;
import com.carlospinan.lolguide.views.championlist.ChampionListFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ChampionListActivity extends AppCompatActivity implements OnFragmentListener {

    @Bind(R.id.rootLayout)
    CoordinatorLayout rootLayout;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private boolean isTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_champion);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        isTwoPane = true;
        if (savedInstanceState == null) {
            FragmentManager supportFragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = supportFragmentManager.beginTransaction();
            transaction.replace(R.id.container, ChampionListFragment.newInstance());
            transaction.commit();
            isTwoPane = false;
        }
    }

    @Override
    public boolean isTwoPane() {
        return isTwoPane;
    }

    @Override
    public void onNotification(String message) {
        Snackbar.make(rootLayout, message, Snackbar.LENGTH_LONG).show();
    }
}
