package com.carlospinan.lolguide.views.championlist;

import com.squareup.okhttp.ResponseBody;

import retrofit.Call;

/**
 * @author Carlos Piñan
 */
public class ChampionListPresenter implements ChampionListContract.UserActionsListener {

    private ChampionListContract.View view;
    private Call<ResponseBody> championsResponseCall;

    public ChampionListPresenter(ChampionListContract.View view) {
        this.view = view;
    }

    @Override
    public void refreshChampions() {
        view.setProgressIndicator(false);
    }
}
