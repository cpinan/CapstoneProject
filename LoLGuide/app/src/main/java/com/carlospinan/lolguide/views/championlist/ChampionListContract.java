package com.carlospinan.lolguide.views.championlist;

/**
 * @author Carlos Piñan
 */
public interface ChampionListContract {

    interface View {

        void setProgressIndicator(boolean active);

    }

    interface UserActionsListener {
        void refreshChampions();
    }
}
