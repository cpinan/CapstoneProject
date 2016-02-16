package com.carlospinan.lolguide.services;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * @author Carlos Piñan
 */
public class RemoteServiceDailyChampion extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RemoteViewDailyChampion(getApplicationContext());
    }
}
