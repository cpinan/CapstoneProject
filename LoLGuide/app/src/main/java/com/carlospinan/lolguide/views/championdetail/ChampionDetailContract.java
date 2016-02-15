package com.carlospinan.lolguide.views.championdetail;

import android.app.Activity;

/**
 * @author Carlos Piñan
 */
public interface ChampionDetailContract {

    interface View {

        void onComplete();

    }

    interface UserActionsListener {
        void saveChampion(Activity activity, int id);

        void removeChampion(Activity activity, int id);
    }
}
