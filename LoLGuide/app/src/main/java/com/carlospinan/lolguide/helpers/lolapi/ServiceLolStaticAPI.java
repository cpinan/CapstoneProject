package com.carlospinan.lolguide.helpers.lolapi;

import com.carlospinan.lolguide.data.Globals;
import com.carlospinan.lolguide.data.enums.ChampDataEnum;
import com.carlospinan.lolguide.data.responses.ChampionsResponse;
import com.carlospinan.lolguide.helpers.Helper;
import com.carlospinan.lolguide.helpers.OkHttpSingleton;
import com.carlospinan.lolguide.helpers.StorageHelper;
import com.carlospinan.lolguide.listeners.APICallback;
import com.carlospinan.lolguide.providers.LolStaticDataAPI;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Carlos Piñan
 */
public class ServiceLolStaticAPI {

    private LolStaticDataAPI lolStaticDataAPI;

    public LolStaticDataAPI api() {
        if (lolStaticDataAPI == null) {
            OkHttpClient client = OkHttpSingleton.getOkHttpClient();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Globals.LOL_STATIC_DATA_ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
            lolStaticDataAPI = retrofit.create(LolStaticDataAPI.class);
        }
        return lolStaticDataAPI;
    }

    // Service managers
    // Callbacks
    public Call<ChampionsResponse> getChampions(
            final APICallback<ChampionsResponse> callback,
            ChampDataEnum... champData
    ) {
        String stringChampData = Helper.get().arrayStringsToStringByComma(champData);
        final Call<ChampionsResponse> call = api().getChampions(
                StorageHelper.get().getRegion(),
                Helper.get().getCodeLanguage(),
                null,
                null,
                stringChampData
        );
        call.enqueue(new Callback<ChampionsResponse>() {
            @Override
            public void onResponse(Call<ChampionsResponse> call, Response<ChampionsResponse> response) {
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<ChampionsResponse> call, Throwable t) {
                callback.onFail(t);
            }
        });
        return call;
    }


}
