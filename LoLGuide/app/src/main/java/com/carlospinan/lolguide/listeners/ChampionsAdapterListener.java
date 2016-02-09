package com.carlospinan.lolguide.listeners;

import android.view.View;

import com.carlospinan.lolguide.data.models.Champion;

/**
 * @author Carlos Piñan
 */
public interface ChampionsAdapterListener {

    void onClickChampion(View view, Champion champion);
}
