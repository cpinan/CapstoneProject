package com.carlospinan.lolguide.views.championlist;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.carlospinan.lolguide.R;
import com.carlospinan.lolguide.activities.ChampionDetailActivity;
import com.carlospinan.lolguide.activities.ChampionListActivity;
import com.carlospinan.lolguide.adapters.ChampionsAdapter;
import com.carlospinan.lolguide.data.Globals;
import com.carlospinan.lolguide.data.models.Champion;
import com.carlospinan.lolguide.dialogs.SettingsDialog;
import com.carlospinan.lolguide.helpers.APIHelper;
import com.carlospinan.lolguide.helpers.StorageHelper;
import com.carlospinan.lolguide.listeners.ChampionsAdapterListener;
import com.carlospinan.lolguide.listeners.OnFragmentListener;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;

/**
 * @author Carlos Piñan
 */
public class ChampionListFragment extends Fragment
        implements ChampionsAdapterListener, ChampionListContract.View {

    private static final int COLS_PORTRAIT = 3;
    private static final int COLS_LANDSCAPE = 5;

    private SwipeRefreshLayout swipeRefreshView;

    @Bind(R.id.championRecyclerView)
    RecyclerView championRecyclerView;

    @Bind(R.id.errorTextView)
    TextView errorTextView;

    private String championQuery;
    private SearchView searchView;
    private ChampionListPresenter presenter;
    private OnFragmentListener onFragmentListener;
    private ChampionsAdapter championsAdapter;
    private Call<List<String>> languagesCall;
    private Boolean inFavoriteMode;
    private Boolean championRotation;

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            refreshWithSettings();
        }
    };

    public static ChampionListFragment newInstance(boolean inFavoriteMode, boolean championRotation) {
        ChampionListFragment mChampionListFragment = new ChampionListFragment();
        Bundle arguments = new Bundle();
        arguments.putBoolean(Globals.FAVORITE_KEY, inFavoriteMode);
        arguments.putBoolean(Globals.CHAMPION_ROTATION_KEY, championRotation);
        mChampionListFragment.setArguments(arguments);
        return mChampionListFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_champion, container, false);
        ButterKnife.bind(this, view);
        presenter = new ChampionListPresenter(this);
        if (getArguments() != null) {
            inFavoriteMode = getArguments().getBoolean(Globals.FAVORITE_KEY, false);
            championRotation = getArguments().getBoolean(Globals.CHAMPION_ROTATION_KEY, false);
        }
        swipeRefreshView = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshView);
        int orientation = getResources().getConfiguration().orientation;
        int columns = orientation == Configuration.ORIENTATION_LANDSCAPE ? COLS_LANDSCAPE : COLS_PORTRAIT;
        List<Champion> champions = new ArrayList<>();
        championQuery = "";
        if (savedInstanceState != null) {
            champions = savedInstanceState.getParcelableArrayList(Globals.PARCEABLE_CHAMPION_KEY);
            championQuery = savedInstanceState.getString(Globals.PARCEABLE_CHAMPION_SEARCH_QUERY_KEY);
        }

        championsAdapter = new ChampionsAdapter(getActivity(), champions, this);
        championRecyclerView.setHasFixedSize(true);
        championRecyclerView.setItemAnimator(new DefaultItemAnimator());
        championRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), columns));
        championRecyclerView.setAdapter(championsAdapter);

        swipeRefreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshView.setRefreshing(true);
                loadLanguages(true);
            }
        });

        errorTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!swipeRefreshView.isRefreshing()) {
                    refreshChampionsAndShowLoading(false);
                }
            }
        });

        if (savedInstanceState == null) {
            refreshChampionsAndShowLoading(false);
        }
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (championsAdapter != null) {
            outState.putParcelableArrayList(Globals.PARCEABLE_CHAMPION_KEY, (ArrayList<? extends Parcelable>) championsAdapter.getChampions());
        }
        if (searchView != null) {
            outState.putString(Globals.PARCEABLE_CHAMPION_SEARCH_QUERY_KEY, searchView.getQuery().toString());
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentListener) {
            onFragmentListener = (OnFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentListener");
        }
    }

    private void refreshChampionsAndShowLoading(boolean forceToRefresh) {
        setProgressIndicator(true);
        loadLanguages(forceToRefresh);
    }

    private void loadLanguages(final boolean forceRefresh) {
        if (StorageHelper.get().getLanguages() == null) {
            languagesCall = APIHelper.get().lolStaticAPI().api().getLanguages(StorageHelper.get().getRegion());
            languagesCall.enqueue(new Callback<List<String>>() {
                @Override
                public void onResponse(Response<List<String>> response) {
                    StorageHelper.get().saveLanguages(response.body());
                    refreshChampions(forceRefresh);
                }

                @Override
                public void onFailure(Throwable t) {
                    refreshChampions(forceRefresh);
                }
            });
        } else {
            refreshChampions(forceRefresh);
        }
    }

    private void refreshChampions(boolean forceRefresh) {
        errorTextView.setVisibility(View.GONE);
        presenter.refreshChampions(
                getActivity(),
                inFavoriteMode,
                championRotation,
                forceRefresh
        );
    }

    @Override
    public void setProgressIndicator(final boolean active) {
        swipeRefreshView.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshView.setRefreshing(active);
            }
        });
    }

    @Override
    public void onSuccess(List<Champion> championList) {
        if (searchView != null) {
            searchView.setQuery("", false);
        }
        if (championList.isEmpty()) {
            errorTextView.setVisibility(View.VISIBLE);
        }
        setProgressIndicator(false);
        if (onFragmentListener.isTwoPane() &&
                championsAdapter != null &&
                championsAdapter.getChampions() != null &&
                championsAdapter.getChampions().isEmpty() &&
                championList != null &&
                !championList.isEmpty() &&
                championList.get(0) != null) {
            updateChampionDetail(championList.get(0));
        }
        championsAdapter.updateChampions(championList);
    }

    @Override
    public void onFail() {
        if (championsAdapter == null || championsAdapter.getChampions().isEmpty()) {
            errorTextView.setVisibility(View.VISIBLE);
        }
        setProgressIndicator(false);
    }

    @Override
    public void onClickChampion(View view, Champion champion) {
        Tracker mTracker = onFragmentListener.getTracker();
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Champion Click")
                .setAction(champion.getName() + " clicked")
                .build());

        if (!onFragmentListener.isTwoPane()) {
            String transitionName = "";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                transitionName = view.getTransitionName();
            }
            Intent intent = new Intent(getActivity(), ChampionDetailActivity.class);
            intent.putExtra(Globals.PARCEABLE_CHAMPION_KEY, champion);
            intent.putExtra(Globals.TRANSITION_IMAGE_KEY, transitionName);

            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    getActivity(), view, transitionName);
            ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
//            startActivity(intent);
        } else {
            updateChampionDetail(champion);
        }
    }

    private void updateChampionDetail(Champion champion) {
        ChampionListActivity activity = (ChampionListActivity) getActivity();
        activity.updateChampionDetail(champion);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragment_list_champion, menu);
        MenuItem searchItem = menu.findItem(R.id.searchAction);
        if (searchItem != null) {
            searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
            if (searchView != null) {
                searchView.setIconifiedByDefault(false);
                searchView.setIconified(false);
                searchView.setQueryHint(getString(R.string.enter_champion_name));
                searchView.clearFocus();

                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        if (!swipeRefreshView.isRefreshing() && championsAdapter != null && newText != null) {
                            championsAdapter.getFilter().filter(newText);
                            return true;
                        }
                        return false;
                    }
                });

                if (championQuery != null && championQuery.trim().length() > 0) {
                    searchView.setQuery(championQuery, true);
                }
            }
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(
                receiver,
                new IntentFilter(SettingsDialog.BROADCAST_ID)
        );
    }

    @Override
    public void onPause() {
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(
                receiver
        );
        if (languagesCall != null) {
            try {
                languagesCall.cancel();
            } catch (NetworkOnMainThreadException e) {

            }
        }
        presenter.onPause();
        super.onPause();
    }

    private void refreshWithSettings() {
        if (StorageHelper.get().mustUpdateUi()) {
            if (swipeRefreshView != null && championsAdapter != null) {
                refreshChampions(true);
                StorageHelper.get().setMustUpdateUi(false);
            }
        }
    }

}
