package com.carlospinan.lolguide.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.carlospinan.lolguide.R;
import com.carlospinan.lolguide.adapters.ChampionSkinsAdapter;
import com.carlospinan.lolguide.data.Globals;
import com.carlospinan.lolguide.data.models.Champion;
import com.carlospinan.lolguide.data.models.ChampionSkin;
import com.carlospinan.lolguide.helpers.Helper;
import com.carlospinan.lolguide.helpers.StorageHelper;
import com.google.android.gms.ads.AdView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Carlos Piñan
 */
public class SkinsActivity extends BaseActivity {

    public static final String BROADCAST_ID = "com.carlospinan.notificationBroadcast";
    public static final String MESSAGE_KEY = "messageKey";

    @Bind(R.id.skinViewPager)
    ViewPager skinViewPager;

    @Bind(R.id.rootLayout)
    CoordinatorLayout rootLayout;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private AdView mAdView;
    private Champion champion;
    private ShareActionProvider mShareActionProvider;

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra(MESSAGE_KEY);
            Snackbar.make(
                    rootLayout,
                    message,
                    Snackbar.LENGTH_LONG
            ).show();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skins_champion);
        ButterKnife.bind(this);
        champion = getIntent().getParcelableExtra(Globals.PARCEABLE_CHAMPION_KEY);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(champion.getName());

        int padding = getResources().getDimensionPixelSize(R.dimen.viewpagerPadding);
        int margin = getResources().getDimensionPixelSize(R.dimen.viewpagerMargin);

        skinViewPager.setClipToPadding(false);
        skinViewPager.setPadding(padding, 0, padding, 0);
        skinViewPager.setPageMargin(margin);

        ChampionSkinsAdapter adapter = new ChampionSkinsAdapter(
                getSupportFragmentManager(),
                champion
        );
        skinViewPager.setAdapter(adapter);

        skinViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { /* UNUSED */ }

            @Override
            public void onPageSelected(int position) {
                setShareIntent();
                supportInvalidateOptionsMenu();
            }

            @Override
            public void onPageScrollStateChanged(int state) { /* UNUSED */ }
        });

        mAdView = (AdView) findViewById(R.id.adView);
        mAdView.loadAd(Helper.get().getAdRequest());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            supportFinishAfterTransition();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
        LocalBroadcastManager.getInstance(this).registerReceiver(
                receiver,
                new IntentFilter(BROADCAST_ID)
        );
    }

    @Override
    protected void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        LocalBroadcastManager.getInstance(this).unregisterReceiver(
                receiver
        );
        super.onPause();
    }

    public static void launchReceiver(Context context, String message) {
        Intent intent = new Intent(BROADCAST_ID);
        intent.putExtra(MESSAGE_KEY, message);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_skins, menu);
        MenuItem item = menu.findItem(R.id.shareAction);
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        setShareIntent();
        return true;
    }

    private Intent getShareIntent() {
        if (skinViewPager != null) {
            int position = skinViewPager.getCurrentItem();
            ChampionSkin skin = champion.getSkins().get(position);
            String imageUrl = StorageHelper.get().getChampionLoadingUrl(
                    champion.getKey(),
                    skin.getNum()
            );
            String title = champion.getName() + " - " + skin.getName();
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, title);
            shareIntent.putExtra(Intent.EXTRA_TEXT, champion.getTitle() + " - " + imageUrl);
            return shareIntent;
        }
        return null;
    }

    private void setShareIntent() {
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(getShareIntent());
        }
    }

}
