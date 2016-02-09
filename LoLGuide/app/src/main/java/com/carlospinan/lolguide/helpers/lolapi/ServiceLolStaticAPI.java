package com.carlospinan.lolguide.helpers.lolapi;

import com.carlospinan.lolguide.data.Globals;
import com.carlospinan.lolguide.data.enums.ChampDataEnum;
import com.carlospinan.lolguide.data.responses.ChampionsResponse;
import com.carlospinan.lolguide.helpers.Helper;
import com.carlospinan.lolguide.helpers.OkHttpSingleton;
import com.carlospinan.lolguide.helpers.StorageHelper;
import com.carlospinan.lolguide.listeners.APICallback;
import com.carlospinan.lolguide.providers.LolStaticDataAPI;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * @author Carlos Piñan
 */
public class ServiceLolStaticAPI {

    private LolStaticDataAPI lolStaticDataAPI;

    public LolStaticDataAPI api() {
        if (lolStaticDataAPI == null) {
            OkHttpClient client = OkHttpSingleton.getOkHttpClient();
            if (Globals.SHOW_DEV_LOG) {
                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                client.interceptors().add(interceptor);
            }
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
            public void onResponse(final Response<ChampionsResponse> response) {
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Throwable throwable) {
                callback.onFail(throwable);
            }
        });
        return call;
    }


}
