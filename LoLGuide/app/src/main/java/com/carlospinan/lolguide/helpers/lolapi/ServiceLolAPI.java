package com.carlospinan.lolguide.helpers.lolapi;

import com.carlospinan.lolguide.helpers.OkHttpSingleton;
import com.carlospinan.lolguide.providers.LolAPI;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * @author Carlos Piñan
 */
public class ServiceLolAPI {

    private LolAPI lolAPI;

    public LolAPI api() {
        if (lolAPI == null) {
            OkHttpClient client = OkHttpSingleton.getOkHttpClient();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://las.api.pvp.net/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
            lolAPI = retrofit.create(LolAPI.class);
        }
        return lolAPI;
    }

}
