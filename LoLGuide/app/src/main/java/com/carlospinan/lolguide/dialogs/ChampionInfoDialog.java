package com.carlospinan.lolguide.dialogs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.carlospinan.lolguide.R;
import com.carlospinan.lolguide.data.Globals;
import com.carlospinan.lolguide.data.models.Champion;
import com.carlospinan.lolguide.data.models.ChampionStats;

/**
 * @author Carlos Piñan
 */
public class ChampionInfoDialog extends DialogFragment {

    public static ChampionInfoDialog newInstance(Champion champion) {
        ChampionInfoDialog mChampionInfoDialog = new ChampionInfoDialog();
        Bundle arguments = new Bundle();
        arguments.putParcelable(Globals.PARCEABLE_CHAMPION_KEY, champion);
        mChampionInfoDialog.setArguments(arguments);
        return mChampionInfoDialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_champion_info, container, false);
        Champion champion = getArguments().getParcelable(Globals.PARCEABLE_CHAMPION_KEY);
        String title = champion.getName() + " - " + champion.getTitle();
        ((TextView) view.findViewById(R.id.titleTextView)).setText(title);
        ((TextView) view.findViewById(R.id.loreTextView)).setText(Html.fromHtml(champion.getLore()));

        // Champion Stats
        String format = "%.2f (+%.2f %s)";
        String attackSpeedFormat = "%.2f (+%.2f %s %s)";
        String per_level = getString(R.string.per_level).toLowerCase();
        ChampionStats stats = champion.getStats();
        assignStats(view.findViewById(R.id.hpStats), getString(R.string.health), String.format(format, stats.getHp(), stats.getHpperlevel(), per_level));
        assignStats(view.findViewById(R.id.hpRegenStats), getString(R.string.health_regen_per_5), String.format(format, stats.getHpregen(), stats.getHpregenperlevel(), per_level));
        assignStats(view.findViewById(R.id.mpStats), getString(R.string.mana), String.format(format, stats.getMp(), stats.getMpperlevel(), per_level));
        assignStats(view.findViewById(R.id.mpRegenStats), getString(R.string.mana_regen_per_5), String.format(format, stats.getMpregen(), stats.getMpregenperlevel(), per_level));
        assignStats(view.findViewById(R.id.moveSpeedStats), getString(R.string.move_speed), String.valueOf(stats.getMovespeed()));
        assignStats(view.findViewById(R.id.armorStats), getString(R.string.armor), String.format(format, stats.getArmor(), stats.getArmorperlevel(), per_level));
        assignStats(view.findViewById(R.id.attackDamageStats), getString(R.string.attack_damage), String.format(format, stats.getAttackdamage(), stats.getAttackdamageperlevel(), per_level));
        assignStats(view.findViewById(R.id.attackRangeStats), getString(R.string.attack_range), String.valueOf(stats.getAttackrange()));
        assignStats(view.findViewById(R.id.attackSpeedStats), getString(R.string.attack_speed), String.format(attackSpeedFormat, stats.getAttackspeedoffset(), stats.getAttackspeedperlevel(), "%", per_level));
        assignStats(view.findViewById(R.id.criticalStats), getString(R.string.critical), String.format(format, stats.getCrit(), stats.getCritperlevel(), per_level));
        view.findViewById(R.id.criticalStats).setVisibility(View.GONE);

        getDialog().setTitle(title);
        return view;
    }

    private void assignStats(View view, String title, String stats) {
        TextView statsTitleTextView = (TextView) view.findViewById(R.id.statsTitleTextView);
        TextView statsTextView = (TextView) view.findViewById(R.id.statsTextView);
        statsTitleTextView.setText(title);
        statsTextView.setText(stats);
    }


}
