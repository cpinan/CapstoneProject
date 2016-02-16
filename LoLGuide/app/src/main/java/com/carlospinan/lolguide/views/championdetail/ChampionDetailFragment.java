package com.carlospinan.lolguide.views.championdetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.carlospinan.lolguide.R;
import com.carlospinan.lolguide.activities.SkinsActivity;
import com.carlospinan.lolguide.activities.VideoActivity;
import com.carlospinan.lolguide.customview.ChampionInformationView;
import com.carlospinan.lolguide.customview.WorkaroundNestedScrollView;
import com.carlospinan.lolguide.data.Globals;
import com.carlospinan.lolguide.data.models.Champion;
import com.carlospinan.lolguide.data.models.ChampionPassive;
import com.carlospinan.lolguide.data.models.ChampionSpell;
import com.carlospinan.lolguide.dialogs.ChampionInfoDialog;
import com.carlospinan.lolguide.dialogs.ChampionSpellDialog;
import com.carlospinan.lolguide.helpers.APIHelper;
import com.carlospinan.lolguide.helpers.Helper;
import com.carlospinan.lolguide.helpers.StorageHelper;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Carlos Piñan
 */
public class ChampionDetailFragment extends Fragment implements ChampionDetailContract.View {

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

    @Bind(R.id.rootLayout)
    View rootLayout;

    @Bind(R.id.favoriteChampionsAction)
    FloatingActionButton favoriteChampionsAction;

    @Bind(R.id.progressBar)
    ProgressBar progressBar;

    @Bind(R.id.nestedScrollView)
    WorkaroundNestedScrollView nestedScrollView;

    @Bind(R.id.championInformationView)
    ChampionInformationView championInformationView;

    private Champion champion;
    private ChampionDetailPresenter presenter;

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
        presenter = new ChampionDetailPresenter(this);
        if (getArguments() != null) {
            champion = getArguments().getParcelable(Globals.PARCEABLE_CHAMPION_KEY);
            updateFavoriteButton(champion.getChampionId());
        }
        favoriteChampionsAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (champion != null) {
                    int championId = champion.getChampionId();
                    if (!StorageHelper.get().getChampion(championId)) {
                        presenter.saveChampion(getActivity(), championId);
                    } else {
                        presenter.removeChampion(getActivity(), championId);
                    }
                }
            }
        });

        progressBar.setVisibility(View.VISIBLE);
        favoriteChampionsAction.setVisibility(View.GONE);
        nestedScrollView.setVisibility(View.GONE);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (champion != null) {
            prepareUi(champion);
        }
    }

    private void updateFavoriteButton(int championId) {
        if (!StorageHelper.get().getChampion(championId)) {
            favoriteChampionsAction.setColorFilter(getResources().getColor(R.color.yellow));
        } else {
            favoriteChampionsAction.setColorFilter(getResources().getColor(R.color.black));
        }
    }

    public void prepareUi(final Champion champion) {
        progressBar.setVisibility(View.GONE);
        favoriteChampionsAction.setVisibility(View.VISIBLE);
        nestedScrollView.setVisibility(View.VISIBLE);

        if (this.champion == null) {
            this.champion = champion;
        }
        abilitiesContainer.removeAllViews();
        allytipsContainer.removeAllViews();
        enemytipsContainer.removeAllViews();

        infoContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChampionInfoDialog dialog = ChampionInfoDialog.newInstance(champion);
                dialog.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AppDialogTheme);
                dialog.show(getActivity().getSupportFragmentManager(), "CHAMPION_DIALOG_INFO");
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

        championInformationView.setInformation(champion.getInfo());

        // Passive and abilities.
        ChampionPassive passive = champion.getPassive();
        imagePath = StorageHelper.get().getPassiveAbilityUrl(passive.getImage().getFull());
        title = getString(R.string.Passive) + " - " + passive.getName();
        description = passive.getDescription();
        loadAbility(championId, 1, title, description, imagePath, null, null, null);

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
            loadAbility(championId, j, title, description, imagePath, abilityDetail, spell.getCooldownBurn(), spell);
            i++;
        }

        // Ally and Enemy Tips
        loadTips(allytipsContainer, champion.getAllyTips());
        loadTips(enemytipsContainer, champion.getEnemyTips());
    }

    private void loadAbility(
            final int championId,
            final int abilityId,
            final String title,
            final String description,
            final String imagePath,
            final String cost,
            final String cooldown,
            final ChampionSpell spell
    ) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.row_champion_ability, null);
        TextView cooldownTextView = (TextView) view.findViewById(R.id.cooldownTextView);
        if (cooldown != null) {
            cooldownTextView.setText(cooldown + "s " + getString(R.string.cool_down));
            cooldownTextView.setVisibility(View.VISIBLE);
        }
        ((TextView) view.findViewById(R.id.abilityTitleTextView)).setText(title);

        String htmlDescription = description + ((abilityId == 1) ? "" : "<br><br><b>" + getString(R.string.more).toUpperCase() + "</b>");

        ((TextView) view.findViewById(R.id.descriptionTextView)).setText(
                Html.fromHtml(htmlDescription)
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
                error(R.drawable.not_available).
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


        if (abilityId > 1 && spell != null) {
            // Not passive.
            view.findViewById(R.id.abilityDetailContainer).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ChampionSpellDialog dialog = ChampionSpellDialog.newInstance(spell);
                    dialog.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AppDialogTheme);
                    dialog.show(getActivity().getSupportFragmentManager(), "CHAMPION_DIALOG_SPELL");
                }
            });
        }
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
                if (champion != null) {
                    Intent intent = new Intent(
                            getActivity(),
                            SkinsActivity.class
                    );
                    intent.putExtra(Globals.PARCEABLE_CHAMPION_KEY, champion);
                    Helper.get().intentWithConnection(
                            rootLayout,
                            getActivity(),
                            intent
                    );
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onComplete() {
        updateFavoriteButton(champion.getChampionId());
    }
}
