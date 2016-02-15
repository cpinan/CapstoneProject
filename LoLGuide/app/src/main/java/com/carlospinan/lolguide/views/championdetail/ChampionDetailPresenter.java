package com.carlospinan.lolguide.views.championdetail;

import android.app.Activity;
import android.content.Intent;

import com.carlospinan.lolguide.data.Globals;
import com.carlospinan.lolguide.helpers.StorageHelper;
import com.carlospinan.lolguide.services.ChampionRemovingService;
import com.carlospinan.lolguide.services.ChampionSavingService;

/**
 * @author Carlos Piñan
 */
public class ChampionDetailPresenter implements ChampionDetailContract.UserActionsListener {

    private ChampionDetailContract.View view;

    public ChampionDetailPresenter(ChampionDetailContract.View view) {
        this.view = view;
    }

    @Override
    public void saveChampion(Activity activity, int id) {
        StorageHelper.get().saveChampion(id);
        Intent intent = new Intent(activity, ChampionSavingService.class);
        intent.putExtra(Globals.INDEX_KEY, id);
        activity.startService(intent);
        view.onComplete();
    }

    @Override
    public void removeChampion(Activity activity, int id) {
        StorageHelper.get().saveChampion(id, false);
        Intent intent = new Intent(activity, ChampionRemovingService.class);
        intent.putExtra(Globals.INDEX_KEY, id);
        activity.startService(intent);
        view.onComplete();
    }
}
