package com.carlospinan.lolguide.data;

import android.util.Log;

import com.carlospinan.lolguide.BuildConfig;

/**
 * @author Carlos Piñan
 */
public class Globals {

    // LOG TAG
    public static final boolean SHOW_DEV_LOG = true;
    private static final String LOG_TAG = "DevelopmentTag";
    private static final String LOG_TEST_TAG = "TestTag";

    // LOL API
    public static final String LOL_DEFAULT_CDN_URL = "http://ddragon.leagueoflegends.com/cdn";
    public static final String LOL_DEFAULT_VERSION = "6.2.1";
    public static final String LOL_STATIC_DATA_API_VERSION = "v1.2";
    public static final String LOL_STATIC_DATA_ENDPOINT = BuildConfig.LOL_STATIC_DATA_ENDPOINT;

    // Constants
    public static final String PARCEABLE_CHAMPION_SEARCH_QUERY_KEY = "parceableChampionSearchQuery";
    public static final String PARCEABLE_CHAMPION_KEY = "parceableChampion";

    // Log Utils
    public static void l(String message) {
        if (SHOW_DEV_LOG) {
            Log.i(LOG_TAG, message);
        }
    }

    public static void testLog(String message) {
        Log.i(LOG_TEST_TAG, message);
    }

}
