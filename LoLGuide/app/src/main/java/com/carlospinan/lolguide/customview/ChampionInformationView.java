package com.carlospinan.lolguide.customview;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.carlospinan.lolguide.R;
import com.carlospinan.lolguide.data.models.ChampionInformation;

/**
 * @author Carlos Piñan
 */
public class ChampionInformationView extends LinearLayout {

    private static final float MAX_BAR_VALUE = 10.0f;
    private static final long BAR_ANIMATION_TIME = 1500L;
    private static final long BAR_DELAY_START_TIME = 150L;

    private View attackBarView;
    private View defenseBarView;
    private View magicBarView;
    private View difficultyBarView;

    private Context context;

    public ChampionInformationView(Context context) {
        this(context, null);
    }

    public ChampionInformationView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChampionInformationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.custom_view_champ_stats, this, true);

        if (attrs != null) {
            attackBarView = findViewById(R.id.attackBarView);
            ((TextView) attackBarView.findViewById(R.id.descriptionTextView)).setText(context.getString(R.string.attack));
            attackBarView.findViewById(R.id.championBar).setBackgroundResource(R.drawable.drawable_bar_attack);

            defenseBarView = findViewById(R.id.defenseBarView);
            ((TextView) defenseBarView.findViewById(R.id.descriptionTextView)).setText(context.getString(R.string.health));
            defenseBarView.findViewById(R.id.championBar).setBackgroundResource(R.drawable.drawable_bar_defense);

            magicBarView = findViewById(R.id.magicBarView);
            ((TextView) magicBarView.findViewById(R.id.descriptionTextView)).setText(context.getString(R.string.magic));
            magicBarView.findViewById(R.id.championBar).setBackgroundResource(R.drawable.drawable_bar_magic);

            difficultyBarView = findViewById(R.id.difficultyBarView);
            ((TextView) difficultyBarView.findViewById(R.id.descriptionTextView)).setText(context.getString(R.string.difficulty));
            difficultyBarView.findViewById(R.id.championBar).setBackgroundResource(R.drawable.drawable_bar_difficult);
        }
    }

    public void setInformation(ChampionInformation info) {
        ImageView attackImageView = null;
        ImageView defenseImageView = null;
        ImageView magicImageView = null;
        ImageView difficultyImageView = null;
        if (attackBarView != null) {
            attackImageView = (ImageView) attackBarView.findViewById(R.id.championBar);
        }
        if (defenseBarView != null) {
            defenseImageView = (ImageView) defenseBarView.findViewById(R.id.championBar);
        }
        if (magicBarView != null) {
            magicImageView = (ImageView) magicBarView.findViewById(R.id.championBar);
        }
        if (difficultyBarView != null) {
            difficultyImageView = (ImageView) difficultyBarView.findViewById(R.id.championBar);
        }
        if (attackImageView != null) {
            runAnimation(context.getString(R.string.attack), attackImageView, info.getAttack());
        }
        if (defenseImageView != null) {
            runAnimation(context.getString(R.string.health), defenseImageView, info.getDefense());
        }
        if (magicImageView != null) {
            runAnimation(context.getString(R.string.magic), magicImageView, info.getMagic());
        }
        if (difficultyImageView != null) {
            runAnimation(context.getString(R.string.difficulty), difficultyImageView, info.getDifficulty());
        }
    }

    private void runAnimation(String title, ImageView imageView, int value) {
        attackBarView.findViewById(R.id.championBar).setContentDescription(title + ": " + value + "/" + MAX_BAR_VALUE);
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(imageView, "scaleX", (float) (value / MAX_BAR_VALUE));
        objectAnimator.setDuration(BAR_ANIMATION_TIME);
        objectAnimator.setStartDelay(BAR_DELAY_START_TIME);
        objectAnimator.start();
    }

}
