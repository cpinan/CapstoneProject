package com.carlospinan.lolguide.views.championdetail;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.carlospinan.lolguide.R;
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

    private Champion champion;

    public static ChampionDetailFragment newInstance(Champion champion) {
        ChampionDetailFragment mChampionDetailFragment = new ChampionDetailFragment();
        Bundle arguments = new Bundle();
        arguments.putParcelable(Globals.PARCEABLE_CHAMPION_KEY, champion);
        mChampionDetailFragment.setArguments(arguments);
        return mChampionDetailFragment;
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
        String title, description, imagePath;

        runBarAnimation(R.drawable.drawable_bar_attack, getString(R.string.attack), attackBarView, champion.getInfo().getAttack());
        runBarAnimation(R.drawable.drawable_bar_defense, getString(R.string.health), defenseBarView, champion.getInfo().getDefense());
        runBarAnimation(R.drawable.drawable_bar_magic, getString(R.string.magic), magicBarView, champion.getInfo().getMagic());
        runBarAnimation(R.drawable.drawable_bar_difficult, getString(R.string.difficulty), difficultyBarView, champion.getInfo().getDifficulty());

        // Passive and abilities.
        ChampionPassive passive = champion.getPassive();
        imagePath = StorageHelper.get().getPassiveAbilityUrl(passive.getImage().getFull());
        title = getString(R.string.Passive) + " - " + passive.getName();
        description = passive.getDescription();
        loadAbility(title, description, imagePath, null);

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
            loadAbility(title, description, imagePath, abilityDetail);
            i++;
        }
    }

    private void runBarAnimation(int backgroundResource, String title, View view, int data) {
        ImageView championBar = (ImageView) view.findViewById(R.id.championBar);
        TextView descriptionTextView = (TextView) view.findViewById(R.id.descriptionTextView);
        if (championBar != null) {
            championBar.setBackgroundResource(backgroundResource);
        }
        descriptionTextView.setText(title);
        ObjectAnimator.ofFloat(view.findViewById(R.id.championBar), "scaleX", (float) (data / MAX_BAR_VALUE)).setDuration(BAR_ANIMATION_TIME).start();
    }

    private void loadAbility(String title, String description, String imagePath, String cost) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.row_champion_ability, null);
        ((TextView) view.findViewById(R.id.abilityTitleTextView)).setText(title);
        ((TextView) view.findViewById(R.id.descriptionTextView)).setText(Html.fromHtml(description));
        if (cost != null) {
            TextView costTextView = (TextView) view.findViewById(R.id.costTextView);
            costTextView.setText(cost);
            costTextView.setVisibility(View.VISIBLE);
        }
        ImageView imageView = (ImageView) view.findViewById(R.id.abilityImageView);
        Glide.with(imageView.getContext()).
                load(imagePath).
                placeholder(R.color.colorPrimaryDark).
                error(android.R.color.holo_red_dark).
                into(imageView);
        abilitiesContainer.addView(view);
    }
}
