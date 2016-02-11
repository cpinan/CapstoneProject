package com.carlospinan.lolguide.views.championdetail;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.carlospinan.lolguide.R;
import com.carlospinan.lolguide.activities.VideoActivity;
import com.carlospinan.lolguide.data.Globals;
import com.carlospinan.lolguide.data.models.Champion;
import com.carlospinan.lolguide.data.models.ChampionPassive;
import com.carlospinan.lolguide.data.models.ChampionSpell;
import com.carlospinan.lolguide.helpers.APIHelper;
import com.carlospinan.lolguide.helpers.StorageHelper;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Carlos Piñan
 */
public class ChampionDetailFragment extends Fragment {

    private static final float MAX_BAR_VALUE = 10.0f;
    private static final long BAR_ANIMATION_TIME = 1500L;

    @Bind(R.id.attackBarView)
    View attackBarView;

    @Bind(R.id.defenseBarView)
    View defenseBarView;

    @Bind(R.id.magicBarView)
    View magicBarView;

    @Bind(R.id.difficultyBarView)
    View difficultyBarView;

    @Bind(R.id.abilitiesContainer)
    LinearLayout abilitiesContainer;

    @Bind(R.id.attributesTextView)
    TextView attributesTextView;

    @Bind(R.id.allytipsContainer)
    LinearLayout allytipsContainer;

    @Bind(R.id.enemytipsContainer)
    LinearLayout enemytipsContainer;

    @Bind(R.id.championTitleTextView)
    TextView championTitleTextView;

    @Bind(R.id.infoContainer)
    View infoContainer;

    private Champion champion;

    public static ChampionDetailFragment newInstance(Champion champion) {
        ChampionDetailFragment mChampionDetailFragment = new ChampionDetailFragment();
        Bundle arguments = new Bundle();
        arguments.putParcelable(Globals.PARCEABLE_CHAMPION_KEY, champion);
        mChampionDetailFragment.setArguments(arguments);
        return mChampionDetailFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_champion, container, false);
        ButterKnife.bind(this, view);
        if (getArguments() != null) {
            champion = getArguments().getParcelable(Globals.PARCEABLE_CHAMPION_KEY);
        }
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (champion != null) {
            prepareUi(champion);
        }
    }

    public void prepareUi(Champion champion) {
        abilitiesContainer.removeAllViews();
        allytipsContainer.removeAllViews();
        enemytipsContainer.removeAllViews();

        infoContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Open dialog with champion stats and lore
            }
        });

        int championId = champion.getChampionId();
        String title, description, imagePath;
        championTitleTextView.setText(champion.getTitle());

        List<String> championTags = champion.getChampionTags();
        String attributes = "";
        for (String tag : championTags) {
            attributes += tag + " ";
        }
        attributes = attributes.trim();
        attributesTextView.setText(attributes);

        runBarAnimation(R.drawable.drawable_bar_attack, getString(R.string.attack), attackBarView, champion.getInfo().getAttack());
        runBarAnimation(R.drawable.drawable_bar_defense, getString(R.string.health), defenseBarView, champion.getInfo().getDefense());
        runBarAnimation(R.drawable.drawable_bar_magic, getString(R.string.magic), magicBarView, champion.getInfo().getMagic());
        runBarAnimation(R.drawable.drawable_bar_difficult, getString(R.string.difficulty), difficultyBarView, champion.getInfo().getDifficulty());

        // Passive and abilities.
        ChampionPassive passive = champion.getPassive();
        imagePath = StorageHelper.get().getPassiveAbilityUrl(passive.getImage().getFull());
        title = getString(R.string.Passive) + " - " + passive.getName();
        description = passive.getDescription();
        loadAbility(championId, 1, title, description, imagePath, null);

        int i = 0;
        List<ChampionSpell> spells = champion.getSpells();
        for (ChampionSpell spell : spells) {
            title = Globals.ABILITIES_KEYS[i % Globals.ABILITIES_KEYS.length] + " - " + spell.getName();
            description = spell.getDescription();
            imagePath = StorageHelper.get().getChampionAbilityUrl(spell.getImage().getFull());
            String abilityDetail = null;
            String costBurn = spell.getCostBurn();
            if (spell.getResource() != null && costBurn != null) {
                String spellResource = spell.getResource();
                if (costBurn == null || costBurn.equalsIgnoreCase("0")) {
                    List<String> effectBurn = spell.getEffectBurn();
                    costBurn = effectBurn.get(
                            APIHelper.get().getIndexFromEffectBurn(spellResource)
                    );
                }
                abilityDetail = spellResource.replaceAll(Globals.PATTERN_COST_TYPE_1, costBurn);
                abilityDetail = abilityDetail.replaceAll(Globals.PATTERN_COST_TYPE_2, costBurn);
            }
            int j = (i + 2) - ((i / Globals.ABILITIES_KEYS.length) * Globals.ABILITIES_KEYS.length);
            loadAbility(championId, j, title, description, imagePath, abilityDetail);
            i++;
        }

        // Ally and Enemy Tips
        loadTips(allytipsContainer, champion.getAllyTips());
        loadTips(enemytipsContainer, champion.getEnemyTips());
    }

    private void runBarAnimation(int backgroundResource, String title, View view, int data) {
        ImageView championBar = (ImageView) view.findViewById(R.id.championBar);
        TextView descriptionTextView = (TextView) view.findViewById(R.id.descriptionTextView);
        if (championBar != null) {
            championBar.setBackgroundResource(backgroundResource);
        }
        descriptionTextView.setText(title);
        championBar.setContentDescription(title + ": " + data + "/" + MAX_BAR_VALUE);
        ObjectAnimator.ofFloat(championBar, "scaleX", (float) (data / MAX_BAR_VALUE)).setDuration(BAR_ANIMATION_TIME).start();
    }

    private void loadAbility(
            final int championId,
            final int abilityId,
            final String title,
            final String description,
            final String imagePath,
            final String cost
    ) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.row_champion_ability, null);
        ((TextView) view.findViewById(R.id.abilityTitleTextView)).setText(title);
        ((TextView) view.findViewById(R.id.descriptionTextView)).setText(
                Html.fromHtml(description + "<p align=\"right\"><b>" + getString(R.string.more).toUpperCase() + "</b></p>")
        );
        if (cost != null) {
            TextView costTextView = (TextView) view.findViewById(R.id.costTextView);
            costTextView.setText(cost);
            costTextView.setVisibility(View.VISIBLE);
        }
        ImageView imageView = (ImageView) view.findViewById(R.id.abilityImageView);
        imageView.setContentDescription(description);
        Glide.with(imageView.getContext()).
                load(imagePath).
                placeholder(R.color.colorPrimaryDark).
                error(android.R.color.holo_red_dark).
                into(imageView);

        view.findViewById(R.id.abilityImageContainer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), VideoActivity.class);
                intent.putExtra(VideoActivity.CHAMPION_ID_KEY, championId);
                intent.putExtra(VideoActivity.CHAMPION_ABILITY_KEY, abilityId);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.abilityDataContainer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, description, Snackbar.LENGTH_LONG).show();
            }
        });

        abilitiesContainer.addView(view);
    }

    private void loadTips(LinearLayout parentView, List<String> tips) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        for (String tip : tips) {
            View view = inflater.inflate(R.layout.row_champion_tips, null);
            ((TextView) view.findViewById(R.id.tipTextView)).setText(Html.fromHtml(tip));
            ViewCompat.setImportantForAccessibility(view.findViewById(R.id.indicatorImageView), ViewCompat.IMPORTANT_FOR_ACCESSIBILITY_NO);
            parentView.addView(view);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_champion, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.skinsAction:

                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
