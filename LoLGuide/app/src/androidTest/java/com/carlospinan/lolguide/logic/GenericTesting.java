package com.carlospinan.lolguide.logic;

import android.content.Context;
import android.content.SharedPreferences;
import android.test.AndroidTestCase;

import com.carlospinan.lolguide.ApplicationController;
import com.carlospinan.lolguide.data.Globals;

/**
 * @author Carlos Piñan
 */
public class GenericTesting extends AndroidTestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        ApplicationController.setContext(getContext());
    }

    public void testApplicationPreferences() {
        Globals.testLog("testApplicationPreferences init");
        Context context = ApplicationController.getContext();
        assertNotNull(context);
        SharedPreferences preferences = context.getSharedPreferences("DEFAULT", Context.MODE_PRIVATE);
        assertNotNull(preferences);

        Globals.testLog("testApplicationPreferences end");
    }
}
