package com.carlospinan.lolguide.providers;

import com.carlospinan.lolguide.BuildConfig;
import com.carlospinan.lolguide.data.Globals;
import com.carlospinan.lolguide.data.enums.RegionEnum;

import org.json.JSONObject;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * @author Carlos Piñan
 */
public interface LolStaticDataAPI {

    @GET("{region}/" + Globals.LOL_STATIC_DATA_API_VERSION + "/champion?api_key=" + BuildConfig.LOL_API_KEY)
    Call<JSONObject> getChampions(
            @Path("region") RegionEnum region,
            @Query("locale") String locale,
            @Query("version") String version,
            @Query("dataById") Boolean dataById,
            @Query("champData") String champData
    );

}
