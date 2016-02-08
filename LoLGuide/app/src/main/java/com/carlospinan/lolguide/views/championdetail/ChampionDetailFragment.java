package com.carlospinan.lolguide.views.championdetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.carlospinan.lolguide.R;

import butterknife.ButterKnife;

/**
 * @author Carlos Piñan
 */
public class ChampionDetailFragment extends Fragment {

    public static ChampionDetailFragment newInstance() {
        ChampionDetailFragment mChampionDetailFragment = new ChampionDetailFragment();
        return mChampionDetailFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_champion, container, false);
        ButterKnife.bind(this, view);
        return view;
    }
}
