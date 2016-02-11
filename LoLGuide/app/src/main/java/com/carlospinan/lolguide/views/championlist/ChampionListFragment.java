package com.carlospinan.lolguide.views.championlist;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.carlospinan.lolguide.R;
import com.carlospinan.lolguide.activities.ChampionDetailActivity;
import com.carlospinan.lolguide.adapters.ChampionsAdapter;
import com.carlospinan.lolguide.data.Globals;
import com.carlospinan.lolguide.data.enums.ChampDataEnum;
import com.carlospinan.lolguide.data.models.Champion;
import com.carlospinan.lolguide.data.responses.ChampionsResponse;
import com.carlospinan.lolguide.helpers.APIHelper;
import com.carlospinan.lolguide.listeners.APICallback;
import com.carlospinan.lolguide.listeners.ChampionsAdapterListener;
import com.carlospinan.lolguide.listeners.OnFragmentListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;

/**
 * @author Carlos Piñan
 */
public class ChampionListFragment extends Fragment
        implements ChampionsAdapterListener, ChampionListContract.View {

    private static final int COLS_PORTRAIT = 3;
    private static final int COLS_LANDSCAPE = 5;

    @Bind(R.id.swipeRefreshView)
    SwipeRefreshLayout swipeRefreshView;

    @Bind(R.id.championRecyclerView)
    RecyclerView championRecyclerView;

    @Bind(R.id.searchView)
    SearchView searchView;

    @Bind(R.id.errorTextView)
    TextView errorTextView;

    private ChampionListPresenter presenter;
    private OnFragmentListener onFragmentListener;
    private ChampionsAdapter championsAdapter;
    private Call<ChampionsResponse> championsResponseCall;

    public static ChampionListFragment newInstance() {
        ChampionListFragment mChampionListFragment = new ChampionListFragment();
        return mChampionListFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_champion, container, false);
        ButterKnife.bind(this, view);

        int orientation = getResources().getConfiguration().orientation;
        int columns = orientation == Configuration.ORIENTATION_LANDSCAPE ? COLS_LANDSCAPE : COLS_PORTRAIT;

        searchView.setIconifiedByDefault(false);
        searchView.setIconified(false);
        searchView.setQueryHint(getString(R.string.enter_champion_name));

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

        List<Champion> champions = new ArrayList<>();
        String championQuery = "";
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
                refreshChampions();
            }
        });

        errorTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!swipeRefreshView.isRefreshing()) {
                    refreshChampionsAndShowLoading();
                }
            }
        });

        if (savedInstanceState == null) {
            refreshChampionsAndShowLoading();
        }
        if (championQuery != null && championQuery.trim().length() > 0) {
            searchView.setQuery(championQuery, true);
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter = new ChampionListPresenter(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (championsResponseCall != null) {
            try {
                championsResponseCall.cancel();
            } catch (NetworkOnMainThreadException e) {

            }
        }
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

    private void refreshChampionsAndShowLoading() {
        swipeRefreshView.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshView.setRefreshing(true);
            }
        });
        refreshChampions();
    }

    private void refreshChampions() {
        errorTextView.setVisibility(View.GONE);
        championsResponseCall = APIHelper.get().lolStaticAPI().getChampions(
                new APICallback<ChampionsResponse>() {
                    @Override
                    public void onSuccess(ChampionsResponse response) {
                        searchView.setQuery("", false);
                        championsAdapter.updateChampions(response.getData());
                        stopLoading();
                    }

                    @Override
                    public void onFail(Throwable throwable) {
                        errorTextView.setVisibility(View.VISIBLE);
                        stopLoading();
                    }
                },
                ChampDataEnum.image, ChampDataEnum.spells,
                ChampDataEnum.allytips, ChampDataEnum.enemytips,
                ChampDataEnum.info, ChampDataEnum.lore,
                ChampDataEnum.passive, ChampDataEnum.skins,
                ChampDataEnum.stats, ChampDataEnum.tags,
                ChampDataEnum.blurb
        );
    }

    @Override
    public void setProgressIndicator(boolean active) {

    }

    //
    private void stopLoading() {
        swipeRefreshView.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshView.setRefreshing(false);
            }
        });
    }

    @Override
    public void onClickChampion(View view, Champion champion) {
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
    }
}
