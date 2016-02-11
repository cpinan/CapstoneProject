package com.carlospinan.lolguide.helpers;

import com.carlospinan.lolguide.helpers.lolapi.ServiceLolStaticAPI;

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
}
