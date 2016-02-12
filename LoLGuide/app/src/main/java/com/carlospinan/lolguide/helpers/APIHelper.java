package com.carlospinan.lolguide.helpers;

import com.carlospinan.lolguide.data.Globals;
import com.carlospinan.lolguide.helpers.lolapi.ServiceLolStaticAPI;

import java.util.List;

/**
 * @author Carlos Piñan
 */
public class APIHelper {

    private static APIHelper instance;

    // LOL Providers
    private ServiceLolStaticAPI serviceLolStaticAPI;

    private APIHelper() {
        serviceLolStaticAPI = new ServiceLolStaticAPI();
    }

    public static APIHelper get() {
        if (instance == null) {
            instance = new APIHelper();
        }
        return instance;
    }

    public ServiceLolStaticAPI lolStaticAPI() {
        return serviceLolStaticAPI;
    }

    public int getIndexFromEffectBurn(String description) {
        if (description.contains("e1") || description.contains("@Effect1Amount")) {
            return 1;
        } else if (description.contains("e2")) {
            return 2;
        } else if (description.contains("e3")) {
            return 3;
        } else if (description.contains("e4")) {
            return 4;
        } else if (description.contains("e5")) {
            return 5;
        }
        return 1;
    }

    public String replaceLolVariables(String text, String costBurn, List<String> effectBurn) {
        if (text.contains(Globals.PATTERN_COST)) {
            text = text.replace(Globals.PATTERN_COST, costBurn);
        }
        // E1 Replacement
        if (text.contains(Globals.PATTERN_E1)) {
            text = text.replace(Globals.PATTERN_E1, effectBurn.get(1));
        }
        if (text.contains(Globals.PATTERN_E2)) {
            text = text.replace(Globals.PATTERN_E2, effectBurn.get(2));
        }
        if (text.contains(Globals.PATTERN_E3)) {
            text = text.replace(Globals.PATTERN_E3, effectBurn.get(3));
        }
        if (text.contains(Globals.PATTERN_E4)) {
            text = text.replace(Globals.PATTERN_E4, effectBurn.get(4));
        }
        if (text.contains(Globals.PATTERN_E5)) {
            text = text.replace(Globals.PATTERN_E5, effectBurn.get(5));
        }
        // F1 Replacement
        if (text.contains(Globals.PATTERN_F1)) {
            text = text.replace(Globals.PATTERN_F1, effectBurn.get(1));
        }
        if (text.contains(Globals.PATTERN_F2)) {
            text = text.replace(Globals.PATTERN_F2, effectBurn.get(2));
        }
        if (text.contains(Globals.PATTERN_F3)) {
            text = text.replace(Globals.PATTERN_F3, effectBurn.get(3));
        }
        if (text.contains(Globals.PATTERN_F4)) {
            text = text.replace(Globals.PATTERN_F4, effectBurn.get(4));
        }
        if (text.contains(Globals.PATTERN_F5)) {
            text = text.replace(Globals.PATTERN_F5, effectBurn.get(5));
        }
        // A1 Replacement
        if (text.contains(Globals.PATTERN_A1)) {
            text = text.replace(Globals.PATTERN_A1, effectBurn.get(1));
        }
        if (text.contains(Globals.PATTERN_A2)) {
            text = text.replace(Globals.PATTERN_A2, effectBurn.get(2));
        }
        if (text.contains(Globals.PATTERN_A3)) {
            text = text.replace(Globals.PATTERN_A3, effectBurn.get(3));
        }
        if (text.contains(Globals.PATTERN_A4)) {
            text = text.replace(Globals.PATTERN_A4, effectBurn.get(4));
        }
        if (text.contains(Globals.PATTERN_A5)) {
            text = text.replace(Globals.PATTERN_A5, effectBurn.get(5));
        }

        return text;
    }

}
