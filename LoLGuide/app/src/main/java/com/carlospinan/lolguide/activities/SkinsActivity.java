package com.carlospinan.lolguide.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.carlospinan.lolguide.R;
import com.carlospinan.lolguide.adapters.ChampionSkinsAdapter;
import com.carlospinan.lolguide.data.Globals;
import com.carlospinan.lolguide.data.models.Champion;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Carlos Piñan
 */
public class SkinsActivity extends BaseActivity {

    @Bind(R.id.skinViewPager)
    ViewPager skinViewPager;

    @Bind(R.id.rootLayout)
    CoordinatorLayout rootLayout;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skins_champion);
        ButterKnife.bind(this);
        Champion champion = getIntent().getParcelableExtra(Globals.PARCEABLE_CHAMPION_KEY);
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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            supportFinishAfterTransition();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
