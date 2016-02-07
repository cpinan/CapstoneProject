package com.carlospinan.lolguide.helpers.lolapi;

import com.carlospinan.lolguide.data.Globals;
import com.carlospinan.lolguide.data.enums.ChampDataEnum;
import com.carlospinan.lolguide.data.models.Champion;
import com.carlospinan.lolguide.data.responses.ChampionsResponse;
import com.carlospinan.lolguide.helpers.Helper;
import com.carlospinan.lolguide.helpers.OkHttpSingleton;
import com.carlospinan.lolguide.helpers.StorageHelper;
import com.carlospinan.lolguide.listeners.APICallback;
import com.carlospinan.lolguide.providers.LolStaticDataAPI;
import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.ResponseBody;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
    public Call<ResponseBody> getChampions(
            ChampDataEnum... champData
    ) {
        return api().getChampions(
                StorageHelper.get().getRegion(),
                Helper.get().getCodeLanguage(),
                null,
                null,
                Helper.get().arrayStringsToStringByComma(champData)
        );
    }

    public Call<Champion> getChampion(
            int championId,
            ChampDataEnum... champData
    ) {
        return api().getChampion(
                StorageHelper.get().getRegion(),
                championId,
                Helper.get().getCodeLanguage(),
                null,
                null,
                Helper.get().arrayStringsToStringByComma(champData)
        );
    }

    // Callbacks
    public Call<ResponseBody> getChampions(
            final APICallback<ChampionsResponse> callback,
            ChampDataEnum... champData
    ) {
        final Call<ResponseBody> call = api().getChampions(
                StorageHelper.get().getRegion(),
                Helper.get().getCodeLanguage(),
                null,
                null,
                Helper.get().arrayStringsToStringByComma(champData)
        );
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Response<ResponseBody> response) {
                try {
                    callback.onSuccess(getChampionsFromString(response.body().string()));
                } catch (IOException e) {
                    callback.onFail(e);
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                callback.onFail(throwable);
            }
        });
        return call;
    }

    // Utilities
    public ChampionsResponse getChampionsFromString(String responseString) {
        ChampionsResponse response = new ChampionsResponse();
        try {
            if (responseString != null && responseString.length() > 0) {
                JSONObject json = new JSONObject(responseString);
                String type = json.getString("type");
                String version = json.getString("version");
                List<Champion> champions = new ArrayList<>();
                JSONObject data = json.getJSONObject("data");
                Iterator keys = data.keys();
                while (keys.hasNext()) {
                    String currentDynamicKey = (String) keys.next();
                    JSONObject championData = data.getJSONObject(currentDynamicKey);
                    Champion champion = new Gson().fromJson(championData.toString(), Champion.class);
                    champions.add(champion);
                }
                response.setType(type);
                response.setVersion(version);
                response.setChampions(champions);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return response;
    }
}
