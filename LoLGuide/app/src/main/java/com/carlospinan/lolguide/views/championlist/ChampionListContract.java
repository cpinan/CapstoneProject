package com.carlospinan.lolguide.views.championlist;

import android.app.Activity;

import com.carlospinan.lolguide.data.models.Champion;

import java.util.List;

/**
 * @author Carlos Piñan
 */
public interface ChampionListContract {

    interface View {

        void setProgressIndicator(boolean active);

        void onSuccess(List<Champion> championList);

        void onFail();

    }

    interface UserActionsListener {
        void refreshChampions(Activity activity, boolean isFavorite, boolean forceRefresh);

        void onPause();
    }
}
