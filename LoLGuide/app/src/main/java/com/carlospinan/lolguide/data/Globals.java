package com.carlospinan.lolguide.data;

import android.util.Log;

/**
 * @author Carlos Piñan
 */
public class Globals {

    // LOG TAG
    public static final boolean SHOW_DEV_LOG = true;
    private static final String LOG_TAG = "DevelopmentTag";
    private static final String LOG_TEST_TAG = "TestTag";

    // LOL API
    public static final String LOL_STATIC_DATA_API_VERSION = "v1.2";
    // https://global.api.pvp.net/api/lol/static-data/las/v1.2/champion?api_key=6d995860-d254-4e79-889a-16ce3bd6abfe
    public static final String LOL_STATIC_DATA_ENDPOINT = "https://global.api.pvp.net/api/lol/static-data/";

    // Constants

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
