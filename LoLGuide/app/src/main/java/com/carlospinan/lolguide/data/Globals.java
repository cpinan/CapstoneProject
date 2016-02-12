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
    public static final String PATTERN_E1 = "{{ e1 }}";
    public static final String PATTERN_E2 = "{{ e2 }}";
    public static final String PATTERN_E3 = "{{ e3 }}";
    public static final String PATTERN_E4 = "{{ e4 }}";
    public static final String PATTERN_E5 = "{{ e5 }}";
    public static final String PATTERN_F1 = "{{ f1 }}";
    public static final String PATTERN_F2 = "{{ f2 }}";
    public static final String PATTERN_F3 = "{{ f3 }}";
    public static final String PATTERN_F4 = "{{ f4 }}";
    public static final String PATTERN_F5 = "{{ f5 }}";
    public static final String PATTERN_A1 = "{{ a1 }}";
    public static final String PATTERN_A2 = "{{ a2 }}";
    public static final String PATTERN_A3 = "{{ a3 }}";
    public static final String PATTERN_A4 = "{{ a4 }}";
    public static final String PATTERN_A5 = "{{ a5 }}";
    public static final String PATTERN_COST = "{{ cost }}";
    public static final String PATTERN_HTML = "<!DOCTYPE html><html><body>%s</body></html>";

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
    public static final String[] ABILITIES_KEYS = {"Q", "W", "E", "R"};

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
