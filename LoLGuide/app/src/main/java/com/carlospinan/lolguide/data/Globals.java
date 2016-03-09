package com.carlospinan.lolguide.data;

import android.util.Log;

import com.carlospinan.lolguide.BuildConfig;

/**
 * @author Carlos Piñan
 */
public class Globals {

    // PATTERNS
    public static final String PATTERN_COST_TYPE_1 = "[{]{2}[\\s].*[\\s][}]{2}";
    public static final String PATTERN_COST_TYPE_2 = "[@]{1}.*[@]{1}";
    public static final String PATTERN_COST = "{{ cost }}";
    public static final String PATTERN_MAX_AMMO = "{{ maxammo }}";

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
    public static final String PARCEABLE_CHAMPION_SPELL = "parceableChampionSpell";
    public static final String TRANSITION_IMAGE_KEY = "transitionImageKey";
    public static final String FAVORITE_KEY = "favoriteModeKey";
    public static final String[] ABILITIES_KEYS = {"Q", "W", "E", "R"};
    public static final String INDEX_KEY = "index";
    public static final String CHAMPION_ROTATION_KEY = "championRotationKey";

    // LOL Values

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
