package com.carlospinan.lolguide.activities;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import com.carlospinan.lolguide.R;
import com.carlospinan.lolguide.helpers.StorageHelper;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Carlos Piñan
 */
public class VideoActivity extends AppCompatActivity {

    public static final String CHAMPION_ID_KEY = "championIdKey";
    public static final String CHAMPION_ABILITY_KEY = "championAbilityKey"; // 1 = passive 5 = R

    @Bind(R.id.videoView)
    VideoView videoView;

    @Bind(R.id.progressBar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        ButterKnife.bind(this);

        final int championId = getIntent().getIntExtra(CHAMPION_ID_KEY, -1);
        final int championAbilityId = getIntent().getIntExtra(CHAMPION_ABILITY_KEY, -1);
        if (championId == -1 || championAbilityId == -1) {
            showErrorAndTerminate();
        } else {
            try {
                String url = StorageHelper.get().getVideoAbilityUrl(championId, championAbilityId);
                Uri videoUri = Uri.parse(url);
                progressBar.setVisibility(View.VISIBLE);
                final MediaController mediaController = new MediaController(this);
                mediaController.setAnchorView(videoView);
                mediaController.setMediaPlayer(videoView);
                videoView.setMediaController(mediaController);
                videoView.setVideoURI(videoUri);
                videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mediaController.show();
                        progressBar.setVisibility(View.GONE);
                        videoView.start();
                    }
                });
                videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        VideoActivity.this.finish();
                    }
                });
            } catch (Exception e) {
                showErrorAndTerminate();
            }
        }
    }

    private void showErrorAndTerminate() {
        Toast.makeText(this, R.string.invalid_data, Toast.LENGTH_LONG).show();
        VideoActivity.this.finish();
    }
}
