package com.carlospinan.lolguide.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.support.annotation.Nullable;

import com.carlospinan.lolguide.R;
import com.carlospinan.lolguide.data.models.LOLData;
import com.carlospinan.lolguide.helpers.APIHelper;
import com.carlospinan.lolguide.helpers.StorageHelper;
import com.carlospinan.lolguide.providers.LolStaticDataAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Carlos Piñan
 */
public class StarterActivity extends BaseActivity {

    private Call<LOLData> apiRealm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starter);
    }

    @Override
    protected void onPause() {
        if (apiRealm != null) {
            try {
                apiRealm.cancel();
            } catch (NetworkOnMainThreadException e) {

            }
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        LolStaticDataAPI api = APIHelper.get().lolStaticAPI().api();
        apiRealm = api.getRealm(StorageHelper.get().getRegion());
        apiRealm.enqueue(new Callback<LOLData>() {
            @Override
            public void onResponse(Call<LOLData> call, Response<LOLData> response) {
                LOLData body = response.body();
                StorageHelper.get().saveVersion(body.getCurrentRealVersion());
                runActivity();
            }

            @Override
            public void onFailure(Call<LOLData> call, Throwable t) {
                runActivity();
            }
        });
    }

    private void runActivity() {
        Intent intent = new Intent(StarterActivity.this, ChampionListActivity.class);
        startActivity(intent);
        StarterActivity.this.finish();
    }
}
