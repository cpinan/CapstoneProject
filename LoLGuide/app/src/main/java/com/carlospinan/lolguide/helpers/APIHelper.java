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
}
