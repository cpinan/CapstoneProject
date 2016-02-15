package com.carlospinan.lolguide.activities;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.carlospinan.lolguide.R;
import com.carlospinan.lolguide.data.Globals;
import com.carlospinan.lolguide.data.models.Champion;
import com.carlospinan.lolguide.helpers.StorageHelper;
import com.carlospinan.lolguide.listeners.OnFragmentListener;
import com.carlospinan.lolguide.views.championdetail.ChampionDetailFragment;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Carlos Piñan
 */
public class ChampionDetailActivity extends BaseActivity implements OnFragmentListener {

    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

    @Bind(R.id.parallaxImageView)
    ImageView parallaxImageView;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.progressBar)
    ProgressBar progressBar;

    private Champion champion;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_champion);
        ButterKnife.bind(this);
        Context context = this;

        champion = getIntent().getParcelableExtra(Globals.PARCEABLE_CHAMPION_KEY);
        String transitionImageName = getIntent().getStringExtra(Globals.TRANSITION_IMAGE_KEY);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(champion.getName());
        String championImageUrl = StorageHelper.get().getChampionSplashUrl(champion.getKey(), champion.getSkins().get(0).getNum());
        if (champion.getSkinsUris() != null && new File(champion.getSkinsUris()).exists()) {
            championImageUrl = champion.getSkinsUris();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP &&
                transitionImageName != null && transitionImageName.length() > 0) {
            parallaxImageView.setTransitionName(transitionImageName);
        }
        progressBar.setVisibility(View.VISIBLE);
        Glide.with(context).
                load(championImageUrl).
                placeholder(R.color.colorPrimaryDark).
                error(R.drawable.not_available).
                listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
//                        supportStartPostponedEnterTransition();
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                }).
                into(parallaxImageView);
        parallaxImageView.setContentDescription(getSupportActionBar().getTitle());

        if (savedInstanceState == null) {
            FragmentManager supportFragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = supportFragmentManager.beginTransaction();
            transaction.replace(R.id.container, ChampionDetailFragment.newInstance(champion));
            transaction.commit();
            isTwoPane = false;
            // Being here means we are in animation mode
//            supportPostponeEnterTransition();
        }
    }

    @Override
    public boolean isTwoPane() {
        return false;
    }

    @Override
    public void onNotification(String message) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            supportFinishAfterTransition();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
