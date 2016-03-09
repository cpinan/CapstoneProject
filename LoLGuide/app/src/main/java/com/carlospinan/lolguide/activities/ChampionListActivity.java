package com.carlospinan.lolguide.activities;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;

import com.carlospinan.lolguide.R;
import com.carlospinan.lolguide.data.models.Champion;
import com.carlospinan.lolguide.dialogs.SettingsDialog;
import com.carlospinan.lolguide.helpers.Helper;
import com.carlospinan.lolguide.listeners.OnFragmentListener;
import com.carlospinan.lolguide.views.championdetail.ChampionDetailFragment;
import com.carlospinan.lolguide.views.championlist.ChampionListFragment;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

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

    private AdView mAdView;

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
                                if (!item.isChecked()) {
                                    setChampionListFragment(false, false);
                                }
                                break;
                            case R.id.favoriteChampionsAction:
                                if (!item.isChecked()) {
                                    setChampionListFragment(true, false);
                                }
                                break;
                            case R.id.championRotationAction:
                                if (!item.isChecked()) {
                                    setChampionListFragment(false, true);
                                }
                                break;
                            case R.id.rateThisAppAction:
                                rateApp();
                                break;
                            case R.id.settingAction:
                                SettingsDialog dialog = SettingsDialog.newInstance();
                                dialog.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AppDialogTheme);
                                dialog.show(getSupportFragmentManager(), "SETTINGS_DIALOG");
                                break;
                        }
                        item.setChecked(true);
                        drawer.closeDrawers();
                        return true;
                    }
                }
        );

        if (findViewById(R.id.secondaryContainer) == null) {
            isTwoPane = false;
        } else {
            if (savedInstanceState == null) {
                FragmentManager supportFragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = supportFragmentManager.beginTransaction();
                transaction.replace(R.id.secondaryContainer, ChampionDetailFragment.newInstance(null));
                transaction.commit();
            }
        }

        if (savedInstanceState == null) {
            setChampionListFragment(false, false);
        }

        mAdView = (AdView) findViewById(R.id.adView);
        mAdView.loadAd(Helper.get().getAdRequest());
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
    public Tracker getTracker() {
        return mTracker;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            drawer.openDrawer(GravityCompat.START);
            InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputManager != null && getCurrentFocus() != null) {
                inputManager.hideSoftInputFromInputMethod(getCurrentFocus().getWindowToken(), 0);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void setChampionListFragment(boolean isFavorite, boolean championRotation) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = supportFragmentManager.beginTransaction();
        transaction.replace(R.id.container, ChampionListFragment.newInstance(isFavorite, championRotation));
        transaction.commit();
    }

    private void rateApp() {
        Uri uri = Uri.parse("market://details?id=" + getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
        }
    }

    @Override
    protected void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        mTracker.setScreenName("Exit Dashboard Screen - " + String.valueOf(isTwoPane));
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
        mTracker.setScreenName("Enter Dashboard Screen - " + String.valueOf(isTwoPane));
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    public void updateChampionDetail(Champion champion) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        ChampionDetailFragment fragment = (ChampionDetailFragment) supportFragmentManager.findFragmentById(R.id.secondaryContainer);
        fragment.prepareUi(champion);
    }
}
