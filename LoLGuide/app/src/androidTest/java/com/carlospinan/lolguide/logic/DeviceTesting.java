package com.carlospinan.lolguide.logic;

import android.test.AndroidTestCase;

import com.carlospinan.lolguide.data.Globals;

import java.util.Locale;

/**
 * @author Carlos Piñan
 */
public class DeviceTesting extends AndroidTestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    public void testDeviceLanguage() {
        Globals.testLog("testDeviceLanguage() init");

        String language = Locale.getDefault().getLanguage();
        String iso3Language = Locale.getDefault().getISO3Language();
        String country = Locale.getDefault().getCountry();
        String iso3Country = Locale.getDefault().getISO3Country();
        String displayCountry = Locale.getDefault().getDisplayCountry();
        String displayName = Locale.getDefault().getDisplayName();
        String languageCode = Locale.getDefault().toString(); // for example: en_US
        String displayLanguage = Locale.getDefault().getDisplayLanguage();

        assertNotNull(language);
        assertNotNull(iso3Language);
        assertNotNull(country);
        assertNotNull(iso3Country);
        assertNotNull(displayCountry);
        assertNotNull(displayName);
        assertNotNull(languageCode);
        assertNotNull(displayLanguage);

        Globals.testLog(language);
        Globals.testLog(iso3Language);
        Globals.testLog(country);
        Globals.testLog(iso3Country);
        Globals.testLog(displayCountry);
        Globals.testLog(displayName);
        Globals.testLog(languageCode);
        Globals.testLog(displayLanguage);

        assertEquals(language, iso3Language.substring(0, 2));
        assertEquals(country, iso3Country.substring(0, 2));
        assertTrue(languageCode.contains(language));
        assertTrue(displayName.contains(displayLanguage));

        Globals.testLog("testDeviceLanguage() end");
    }
}
