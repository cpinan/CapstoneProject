package com.carlospinan.lolguide.activities;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.carlospinan.lolguide.R;
import com.carlospinan.lolguide.listeners.OnFragmentListener;
import com.carlospinan.lolguide.views.championlist.ChampionListFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ChampionListActivity extends BaseActivity implements OnFragmentListener {

    @Bind(R.id.rootLayout)
    CoordinatorLayout rootLayout;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.drawer)
    DrawerLayout drawer;

    @Bind(R.id.navView)
    NavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_champion);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        navView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem item) {
                        int id = item.getItemId();
                        switch (id) {
                            case R.id.championsAction:
                                break;
                            case R.id.favoriteChampionsAction:
                                break;
                            case R.id.aboutAction:
                                break;
                            case R.id.rateThisAppAction:
                                break;
                        }
                        item.setChecked(true);
                        // TODO: handle navigation
                        // Closing drawer on item click
                        drawer.closeDrawers();
                        return true;
                    }
                }
        );

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list_champion, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            drawer.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }

}
