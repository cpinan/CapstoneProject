package com.carlospinan.lolguide.dialogs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.carlospinan.lolguide.R;
import com.carlospinan.lolguide.data.Globals;
import com.carlospinan.lolguide.data.models.ChampionSpell;
import com.carlospinan.lolguide.data.models.ChampionSpellVar;
import com.carlospinan.lolguide.helpers.APIHelper;
import com.carlospinan.lolguide.helpers.Helper;
import com.carlospinan.lolguide.helpers.StorageHelper;

import java.util.List;

/**
 * @author Carlos Piñan
 */
public class ChampionSpellDialog extends DialogFragment {

    public static ChampionSpellDialog newInstance(ChampionSpell spell) {
        ChampionSpellDialog mChampionSpellDialog = new ChampionSpellDialog();
        Bundle arguments = new Bundle();
        arguments.putParcelable(Globals.PARCEABLE_CHAMPION_SPELL, spell);
        mChampionSpellDialog.setArguments(arguments);
        return mChampionSpellDialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_champion_spell, container, false);

        ChampionSpell spell = getArguments().getParcelable(Globals.PARCEABLE_CHAMPION_SPELL);

        ImageView spellImageView = (ImageView) view.findViewById(R.id.spellImageView);
        TextView spellNameTextView = (TextView) view.findViewById(R.id.spellNameTextView);
        TextView spellTooltip = (TextView) view.findViewById(R.id.spellTooltip);
        TextView spellRange = (TextView) view.findViewById(R.id.spellRange);

        Object range = spell.getRange();
        String rangeString;
        if (range instanceof List) {
            List<String> rangeList = (List<String>) range;
            rangeString = rangeList.get(0);
        } else {
            rangeString = String.valueOf(range);
        }
        spellRange.setText(String.format("%s: %s", getString(R.string.range), rangeString));

        String imagePath = StorageHelper.get().getChampionAbilityUrl(spell.getImage().getFull());
        Glide.with(spellImageView.getContext()).
                load(imagePath).
                placeholder(R.color.colorPrimaryDark).
                error(android.R.color.holo_red_dark).
                into(spellImageView);

        spellNameTextView.setText(spell.getName());
        String tooltip = spell.getTooltip();
        // Replaces variables
        List<ChampionSpellVar> vars = spell.getVars();
        if (vars != null && !vars.isEmpty()) {
            for (ChampionSpellVar var : vars) {
                String key = var.getKey();
                String keyReplace = String.format("{{ %s }}", key);
                String coeff = Helper.getStringFromDoubleList(var.getCoeff());
                coeff = coeff.replace(Helper.DELIMITER, "/");
                tooltip = tooltip.replace(keyReplace, coeff);
            }
        }
        List<String> effectBurn = spell.getEffectBurn();
        String costBurn = spell.getCostBurn();
        tooltip = APIHelper.get().replaceLolVariables(tooltip, costBurn, effectBurn);
        tooltip = String.format(Globals.PATTERN_HTML, tooltip);
        spellTooltip.setText(Html.fromHtml(tooltip));
        getDialog().setTitle(spell.getName());
        return view;
    }
}
