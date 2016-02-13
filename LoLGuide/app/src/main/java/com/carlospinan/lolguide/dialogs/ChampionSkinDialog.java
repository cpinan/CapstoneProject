package com.carlospinan.lolguide.dialogs;

import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.carlospinan.lolguide.R;
import com.carlospinan.lolguide.data.Globals;
import com.carlospinan.lolguide.data.models.Champion;
import com.carlospinan.lolguide.data.models.ChampionSkin;
import com.carlospinan.lolguide.helpers.StorageHelper;

import java.io.IOException;

/**
 * @author Carlos Piñan
 */
public class ChampionSkinDialog extends DialogFragment {

    public static ChampionSkinDialog newInstance(Champion champion, int index) {
        ChampionSkinDialog mChampionInfoDialog = new ChampionSkinDialog();
        Bundle arguments = new Bundle();
        arguments.putParcelable(Globals.PARCEABLE_CHAMPION_KEY, champion);
        arguments.putInt(Globals.INDEX_KEY, index);
        mChampionInfoDialog.setArguments(arguments);
        return mChampionInfoDialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_skins_options, container, false);
        final Champion champion = getArguments().getParcelable(Globals.PARCEABLE_CHAMPION_KEY);
        int index = getArguments().getInt(Globals.INDEX_KEY);
        final ChampionSkin skin = champion.getSkins().get(index);

        final String imageUrl = StorageHelper.get().getChampionLoadingUrl(
                champion.getKey(),
                skin.getNum()
        );

        final Button wallpaperButton = (Button) view.findViewById(R.id.wallpaperButton);
        wallpaperButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Glide.with(getActivity()).
                                load(imageUrl).
                                asBitmap().
                                into(
                                        target
                                );
                    }
                }
        );

        view.findViewById(R.id.closeButton).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismiss();
                    }
                }
        );

        getDialog().setTitle(champion.getName() + " " + skin.getName());
        return view;
    }

    private SimpleTarget<Bitmap> target = new SimpleTarget<Bitmap>() {
        @Override
        public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
            WallpaperManager myWallpaperManager
                    = WallpaperManager.getInstance(getActivity());
            try {
                myWallpaperManager.setBitmap(bitmap);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    };

}
