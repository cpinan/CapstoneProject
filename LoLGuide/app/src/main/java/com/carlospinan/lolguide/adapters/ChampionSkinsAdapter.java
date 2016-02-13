package com.carlospinan.lolguide.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.carlospinan.lolguide.data.models.Champion;
import com.carlospinan.lolguide.views.ChampionSkinFragment;

/**
 * @author Carlos Piñan
 */
public class ChampionSkinsAdapter extends FragmentStatePagerAdapter {

    private Champion champion;

    public ChampionSkinsAdapter(FragmentManager fm, Champion champion) {
        super(fm);
        this.champion = champion;
    }

    @Override
    public Fragment getItem(int position) {
        return ChampionSkinFragment.newInstance(champion, position);
    }

    @Override
    public int getCount() {
        return champion.getSkins().size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return champion.getName() + " - " + champion.getSkins().get(position).getName();
    }
}
