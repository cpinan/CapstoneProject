package com.carlospinan.lolguide.dialogs;

import android.content.Intent;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.carlospinan.lolguide.R;
import com.carlospinan.lolguide.data.enums.RegionEnum;
import com.carlospinan.lolguide.helpers.APIHelper;
import com.carlospinan.lolguide.helpers.StorageHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Carlos Piñan
 */
public class SettingsDialog extends DialogFragment {

    public static final String BROADCAST_ID = "com.carlospinan.updateChampionsBroadcast";

    @Bind(R.id.regionsSpinner)
    Spinner regionsSpinner;

    @Bind(R.id.languagesSpinner)
    Spinner languagesSpinner;

    @Bind(R.id.progressBar)
    ProgressBar progressBar;

    @Bind(R.id.container)
    View container;

    private Call<List<String>> languagesCall;
    private RegionEnum defaultRegion;
    private String defaultLanguage;

    public static SettingsDialog newInstance() {
        return new SettingsDialog();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, view);
        defaultRegion = StorageHelper.get().getRegion();
        defaultLanguage = StorageHelper.get().getDefaultLanguage();
        List<String> languages = StorageHelper.get().getLanguages();
        if (languages != null && !languages.isEmpty()) {
            init();
        } else {
            languagesCall = APIHelper.get().lolStaticAPI().api().getLanguages(StorageHelper.get().getRegion());
            languagesCall.enqueue(new Callback<List<String>>() {
                @Override
                public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                    StorageHelper.get().saveLanguages(response.body());
                    init();
                }

                @Override
                public void onFailure(Call<List<String>> call, Throwable t) {
                    Toast.makeText(
                            getActivity(),
                            t.getLocalizedMessage(),
                            Toast.LENGTH_LONG
                    ).show();
                    SettingsDialog.this.dismiss();
                }
            });
        }
        return view;
    }


    private void init() {
        progressBar.setVisibility(View.GONE);
        container.setVisibility(View.VISIBLE);
        setRegionsSpinner();
        setLanguagesSpinner();
        regionsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (regionsSpinner.getTag() == null) {
                    regionsSpinner.setTag(true);
                    return;
                }
                String region = String.valueOf(parent.getAdapter().getItem(position));
                StorageHelper.get().saveRegion(region);
                showNotification(region + " " + getString(R.string.saved));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        languagesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (languagesSpinner.getTag() == null) {
                    languagesSpinner.setTag(true);
                    return;
                }
                String language = String.valueOf(parent.getAdapter().getItem(position));
                StorageHelper.get().saveDefaultLanguage(language);
                showNotification(language + " " + getString(R.string.saved));
                StorageHelper.get().setMustUpdateUi(true);
                updateChampionsList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void showNotification(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    private void setRegionsSpinner() {
        int i = 0;
        int index = -1;
        List<String> values = new ArrayList<>();
        for (RegionEnum region : RegionEnum.values()) {
            if (region == defaultRegion) {
                index = i;
            }
            values.add(region.toString());
            i++;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                values
        );
        regionsSpinner.setAdapter(adapter);
        if (index != -1) {
            regionsSpinner.setSelection(index);
        }
    }

    private void setLanguagesSpinner() {
        int i = 0;
        int index = -1;
        List<String> values = StorageHelper.get().getLanguages();
        for (String language : values) {
            if (language.equalsIgnoreCase(defaultLanguage)) {
                index = i;
                break;
            }
            i++;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                values
        );
        languagesSpinner.setAdapter(adapter);
        if (index != -1) {
            languagesSpinner.setSelection(index);
        }
    }

    @Override
    public void onPause() {
        if (languagesCall != null) {
            try {
                languagesCall.cancel();
            } catch (NetworkOnMainThreadException e) {

            }
        }
        super.onPause();
    }

    private void updateChampionsList() {
        Intent intent = new Intent(BROADCAST_ID);
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
    }
}
