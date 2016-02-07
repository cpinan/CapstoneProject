package com.carlospinan.lolguide.providers;

import com.carlospinan.lolguide.BuildConfig;
import com.carlospinan.lolguide.data.Globals;
import com.carlospinan.lolguide.data.enums.RegionEnum;
import com.carlospinan.lolguide.data.models.LOLData;
import com.carlospinan.lolguide.data.models.Champion;
import com.squareup.okhttp.ResponseBody;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * @author Carlos Piñan
 */
public interface LolStaticDataAPI {

    @GET("{region}/" + Globals.LOL_STATIC_DATA_API_VERSION + "/champion?api_key=" + BuildConfig.LOL_API_KEY)
    Call<ResponseBody> getChampions(
            @Path("region") RegionEnum region,
            @Query("locale") String locale,
            @Query("version") String version,
            @Query("dataById") Boolean dataById,
            @Query("champData") String champData
    );

    @GET("{region}/" + Globals.LOL_STATIC_DATA_API_VERSION + "/champion/{id}?api_key=" + BuildConfig.LOL_API_KEY)
    Call<Champion> getChampion(
            @Path("region") RegionEnum region,
            @Path("id") Integer id,
            @Query("locale") String locale,
            @Query("version") String version,
            @Query("dataById") Boolean dataById,
            @Query("champData") String champData
    );

    @GET("{region}/" + Globals.LOL_STATIC_DATA_API_VERSION + "/item?api_key=" + BuildConfig.LOL_API_KEY)
    Call<ResponseBody> getItems(
            @Path("region") RegionEnum region,
            @Query("locale") String locale,
            @Query("version") String version,
            @Query("itemListData") String itemListData
    );

    @GET("{region}/" + Globals.LOL_STATIC_DATA_API_VERSION + "/languages?api_key=" + BuildConfig.LOL_API_KEY)
    Call<List<String>> getLanguages(
            @Path("region") RegionEnum region
    );

    @GET("{region}/" + Globals.LOL_STATIC_DATA_API_VERSION + "/realm?api_key=" + BuildConfig.LOL_API_KEY)
    Call<LOLData> getRealm(
            @Path("region") RegionEnum region
    );

    @GET("{region}/" + Globals.LOL_STATIC_DATA_API_VERSION + "/versions?api_key=" + BuildConfig.LOL_API_KEY)
    Call<List<String>> getVersions(
            @Path("region") RegionEnum region
    );

}
