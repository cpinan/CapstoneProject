package com.carlospinan.lolguide.helpers.lolapi;

import com.carlospinan.lolguide.data.Globals;
import com.carlospinan.lolguide.helpers.OkHttpSingleton;
import com.carlospinan.lolguide.providers.LolAPI;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * @author Carlos Piñan
 */
public class ServiceLolAPI {

    private LolAPI lolAPI;

    public LolAPI api() {
        if (lolAPI == null) {
            OkHttpClient client = OkHttpSingleton.getOkHttpClient();
            if (Globals.SHOW_DEV_LOG) {
                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                client.interceptors().add(interceptor);
            }
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
