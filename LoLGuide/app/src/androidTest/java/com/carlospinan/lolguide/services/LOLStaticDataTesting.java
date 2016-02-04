package com.carlospinan.lolguide.services;

import android.test.AndroidTestCase;

import com.carlospinan.lolguide.data.Globals;
import com.carlospinan.lolguide.helpers.APIHelper;
import com.carlospinan.lolguide.helpers.lolapi.ServiceLolStaticAPI;

import org.json.JSONObject;

import java.io.IOException;

import retrofit.Call;
import retrofit.Response;

/**
 * @author Carlos Piñan
 */
public class LOLStaticDataTesting extends AndroidTestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    public void testGetChampions() throws IOException {
        Globals.testLog("testGetChampions init");

        ServiceLolStaticAPI api = APIHelper.get().lolStaticAPI();
        Call<JSONObject> call = api.getChampions("image");
        Response<JSONObject> response = call.execute();
        assertNotNull(response);
        assertNotNull(response.body());
        Globals.testLog(response.body().toString());

        Globals.testLog("testGetChampions end");
    }
}
