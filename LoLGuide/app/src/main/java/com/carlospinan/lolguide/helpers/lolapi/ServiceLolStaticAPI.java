package com.carlospinan.lolguide.helpers.lolapi;

import com.carlospinan.lolguide.data.Globals;
import com.carlospinan.lolguide.data.enums.RegionEnum;
import com.carlospinan.lolguide.helpers.Helper;
import com.carlospinan.lolguide.helpers.OkHttpSingleton;
import com.carlospinan.lolguide.providers.LolStaticDataAPI;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import org.json.JSONObject;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * @author Carlos Piñan
 */
public class ServiceLolStaticAPI {

    private LolStaticDataAPI lolStaticDataAPI;

    private LolStaticDataAPI api() {
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
    public Call<JSONObject> getChampions(
            String... champData
    ) {
        return api().getChampions(
                RegionEnum.lan,
                Helper.get().getCodeLanguage(),
                null,
                null,
                Helper.get().arrayStringsToStringByComma(champData)
        );
    }
}
