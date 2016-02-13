package com.carlospinan.lolguide.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.carlospinan.lolguide.R;
import com.carlospinan.lolguide.data.Globals;
import com.carlospinan.lolguide.data.models.Champion;
import com.carlospinan.lolguide.data.models.ChampionSkin;
import com.carlospinan.lolguide.dialogs.ChampionSkinDialog;
import com.carlospinan.lolguide.helpers.StorageHelper;

/**
 * @author Carlos Piñan
 */
public class ChampionSkinFragment extends Fragment {

    private int index;
    private Champion champion;

    public static ChampionSkinFragment newInstance(Champion champion, int index) {
        ChampionSkinFragment mChampionSkinFragment = new ChampionSkinFragment();
        Bundle arguments = new Bundle();
        arguments.putParcelable(Globals.PARCEABLE_CHAMPION_KEY, champion);
        arguments.putInt(Globals.INDEX_KEY, index);
        mChampionSkinFragment.setArguments(arguments);
        return mChampionSkinFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            champion = getArguments().getParcelable(Globals.PARCEABLE_CHAMPION_KEY);
            index = getArguments().getInt(Globals.INDEX_KEY);
        }
        if (champion == null) {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
        View view = inflater.inflate(R.layout.skin_layout, container, false);
        ImageView skinImageView = (ImageView) view.findViewById(R.id.skinImageView);
        TextView skinNameTextView = (TextView) view.findViewById(R.id.skinNameTextView);

        ChampionSkin skin = champion.getSkins().get(index);
        skinNameTextView.setText(skin.getName());
        String imageUrl = StorageHelper.get().getChampionLoadingUrl(
                champion.getKey(),
                skin.getNum()
        );
        Glide.with(skinImageView.getContext()).
                load(imageUrl).
                placeholder(R.color.colorPrimaryDark).
                error(android.R.color.holo_red_dark).
                into(skinImageView);

        skinImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChampionSkinDialog dialog = ChampionSkinDialog.newInstance(
                        champion,
                        index
                );
                dialog.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AppDialogTheme);
                dialog.show(getActivity().getSupportFragmentManager(), "CHAMPION_DIALOG_SKIN");
            }
        });

        return view;
    }
}
