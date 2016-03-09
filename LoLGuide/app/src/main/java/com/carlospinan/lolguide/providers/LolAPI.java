package com.carlospinan.lolguide.providers;

import com.carlospinan.lolguide.BuildConfig;
import com.carlospinan.lolguide.data.Globals;
import com.carlospinan.lolguide.data.enums.RegionEnum;
import com.carlospinan.lolguide.data.models.api.APIChampion;
import com.carlospinan.lolguide.data.responses.APIChampionsResponse;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * @author Carlos Piñan
 */
public interface LolAPI {

    @GET("api/lol/{region}/" + Globals.LOL_STATIC_DATA_API_VERSION + "/champion?api_key=" + BuildConfig.LOL_API_KEY)
    Call<APIChampionsResponse> getChampions(
            @Path("region") RegionEnum region,
            @Query("freeToPlay") Boolean freeToPlay
    );

    @GET("api/lol/{region}/" + Globals.LOL_STATIC_DATA_API_VERSION + "/champion/{championId}?api_key=" + BuildConfig.LOL_API_KEY)
    Call<APIChampion> getChampion(
            @Path("region") RegionEnum region,
            @Path("championId") Integer championId
    );
}
