package com.carlospinan.lolguide.services;

import android.app.IntentService;
import android.content.Intent;

/**
 * @author Carlos Piñan
 */
public class SaveChampionsService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public SaveChampionsService() {
        super(SaveChampionsService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }
}
