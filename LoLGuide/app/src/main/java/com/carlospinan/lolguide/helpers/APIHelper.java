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
        } else if (description.contains("e6")) {
            return 6;
        } else if (description.contains("e7")) {
            return 7;
        }
        return 1;
    }

    public String replaceLolVariables(
            String text,
            String costBurn,
            List<String> effectBurn,
            List<List<Double>> effects
    ) {
        if (text.contains(Globals.PATTERN_COST)) {
            text = text.replace(Globals.PATTERN_COST, costBurn);
        }
        if (text.contains(Globals.PATTERN_MAX_AMMO)) {
            text = text.replace(Globals.PATTERN_MAX_AMMO, "2");
        }
        final int MAX = 10;
        int i;
        String ePatternFormat = "{{ e%d }}";
        String fPatternFormat = "{{ f%d }}";
        for (i = 1; i <= MAX; i++) {
            String eKey = String.format(ePatternFormat, i);
            String fKey = String.format(fPatternFormat, i);
            text = replace(i, text, eKey, effectBurn, effects);
            text = replace(i, text, fKey, effectBurn, effects);
        }
        return text;
    }

    private String replace(
            int index,
            String text,
            String key,
            List<String> effectBurn,
            List<List<Double>> effects
    ) {
        if (text.contains(key)) {
            if (effectBurn == null || index > effectBurn.size() - 1) {
                if (effects == null || index > effects.size() - 1) {
                    text = text.replace(key, effectBurn.get(0));
                } else {
                    List<Double> list = effects.get(index);
                    String string = ChampionHelper.getStringFromDoubleList(list);
                    string = string.replace(ChampionHelper.DELIMITER, "/");
                    text = text.replace(key, string);
                }
            } else {
                text = text.replace(key, effectBurn.get(index));
            }
        }
        return text;
    }

}
