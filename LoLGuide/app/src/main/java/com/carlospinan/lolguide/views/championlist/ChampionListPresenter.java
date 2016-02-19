package com.carlospinan.lolguide.views.championlist;

import android.app.Activity;
import android.os.NetworkOnMainThreadException;

import com.carlospinan.lolguide.ApplicationController;
import com.carlospinan.lolguide.data.enums.ChampDataEnum;
import com.carlospinan.lolguide.data.models.Champion;
import com.carlospinan.lolguide.data.models.realm.RealmChampion;
import com.carlospinan.lolguide.data.responses.ChampionsResponse;
import com.carlospinan.lolguide.helpers.APIHelper;
import com.carlospinan.lolguide.helpers.ChampionHelper;
import com.carlospinan.lolguide.helpers.Helper;
import com.carlospinan.lolguide.listeners.APICallback;
import com.carlospinan.lolguide.listeners.AsyncTaskCallback;
import com.carlospinan.lolguide.services.AsyncTaskService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit.Call;

/**
 * @author Carlos Piñan
 */
public class ChampionListPresenter implements ChampionListContract.UserActionsListener {

    private static List<Champion> temporalChampionCache;

    private ChampionListContract.View view;
    private Call<ChampionsResponse> championsResponseCall;
    private AsyncTaskService<List<Champion>> asyncTaskService;

    public ChampionListPresenter(ChampionListContract.View view) {
        this.view = view;
    }

    @Override
    public void refreshChampions(
            final Activity activity,
            boolean isFavorite
    ) {
        view.setProgressIndicator(true);
        if (isFavorite) {
            searchForChampions(true);
        } else {

            if (temporalChampionCache != null && !temporalChampionCache.isEmpty()) {
                view.onSuccess(temporalChampionCache);
            } else {

                if (Helper.get().hasInternetConnection(activity)) {
                    championsResponseCall = APIHelper.get().lolStaticAPI().getChampions(
                            new APICallback<ChampionsResponse>() {
                                @Override
                                public void onSuccess(ChampionsResponse response) {
                                    List<Champion> championList = new ArrayList<>();
                                    for (Map.Entry<String, Champion> entry : response.getData().entrySet()) {
                                        championList.add(entry.getValue());
                                    }
                                    Collections.sort(championList, new Champion());

//                                    if (championList != null && !championList.isEmpty()) {
//                                        Intent intent = new Intent(activity, SaveChampionsService.class);
//                                        SaveChampionsService.championList = championList;
//                                        activity.startService(intent);
//                                    }

                                    temporalChampionCache = championList;
                                    view.onSuccess(championList);
                                }

                                @Override
                                public void onFail(Throwable throwable) {
                                    view.onFail();
                                }
                            },
                            ChampDataEnum.image, ChampDataEnum.spells,
                            ChampDataEnum.allytips, ChampDataEnum.enemytips,
                            ChampDataEnum.info, ChampDataEnum.lore,
                            ChampDataEnum.passive, ChampDataEnum.skins,
                            ChampDataEnum.stats, ChampDataEnum.tags,
                            ChampDataEnum.blurb
                    );
                } else {
                    searchForChampions(false);
                }
            }
        }
    }

    private void searchForChampions(final boolean favorite) {
        asyncTaskService = new AsyncTaskService<>(
                new AsyncTaskCallback<List<Champion>>() {
                    @Override
                    public void onCancelled() {
                        view.onFail();
                    }

                    @Override
                    public List<Champion> onExecute() {
                        final Realm realm = ApplicationController.getRealm();
                        realm.refresh();
                        RealmResults<RealmChampion> realmChampions;
                        if (favorite) {
                            realmChampions = realm.where(RealmChampion.class).equalTo("favorite", true).findAll();
                        } else {
                            realmChampions = realm.where(RealmChampion.class).findAll();
                        }
                        List<Champion> champions = new ArrayList<>();
                        for (RealmChampion realmChampion : realmChampions) {
                            champions.add(ChampionHelper.fromRealmToChampion(realmChampion));
                        }
                        return champions;
                    }

                    @Override
                    public void onSuccess(List<Champion> response) {
                        view.onSuccess(response);
                    }

                    @Override
                    public void onFail(Throwable throwable) {
                        view.onFail();
                    }
                }
        );
    }

    @Override
    public void onPause() {
        if (championsResponseCall != null) {
            try {
                championsResponseCall.cancel();
            } catch (NetworkOnMainThreadException e) {

            }
        }
        if (asyncTaskService != null) {
            asyncTaskService.cancel(true);
        }
    }
}
