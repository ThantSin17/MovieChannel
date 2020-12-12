package com.stone.moviechannel.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.util.Util;
import com.stone.moviechannel.R;

import androidx.appcompat.app.AppCompatActivity;

public class PlayerActivity extends AppCompatActivity {

    private boolean playWhenReady = true;
    private int currentWindow = 0;
    private long playbackPosition = 0;
    private SimpleExoPlayer player;
    private static String url = "";
    private PlayerView playerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_player);
        playerView=findViewById(R.id.video_view);



    }

    public static Intent gotoPlayerActivity(Context context, String uri) {
        url = uri;

        return new Intent(context, PlayerActivity.class);

    }
    private void initializePlayer() {

        player = new SimpleExoPlayer.Builder(this).build();
        playerView.setPlayer(player);

        MediaItem mediaItem = MediaItem.fromUri(Uri.parse(url));
        player.setMediaItem(mediaItem);
        player.setPlayWhenReady(playWhenReady);
        player.seekTo(currentWindow, playbackPosition);
        player.prepare();

    }
    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT >= 24) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        hideSystemUi();
        if ((Util.SDK_INT < 24 || player == null)) {
            initializePlayer();
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT < 24) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT >= 24) {
            releasePlayer();
        }
    }
    private void releasePlayer() {
        if (player != null) {
            playWhenReady = player.getPlayWhenReady();
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            player.release();
            player = null;
        }
    }


    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

}

// pDialog = new ProgressDialog(this);
//         // Set progressbar title
//         pDialog.setTitle("Android Video Streaming Tutorial");
//         // Set progressbar message
//         pDialog.setMessage("Buffering...");
//         pDialog.setIndeterminate(false);
//         pDialog.setCancelable(false);
//         // Show progressbar
//         pDialog.show();
//
//         try {
//         // Start the MediaController
//         mediaController = new MediaController(this);
//         mediaController.setAnchorView(binding.vView);
//
//         // Get the URL from String VideoURL
//         Uri video = Uri.parse(url);
//         binding.vView.setMediaController(mediaController);
//         binding.vView.setVideoURI(video);
//
//
//         } catch (Exception e) {
//         Log.e("Error", e.getMessage());
//         e.printStackTrace();
//         }
//
//
//         // mediaController.setEnabled(true);
//
//         binding.vView.requestFocus();
//         binding.vView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//// Close the progress bar and play the video
//public void onPrepared(MediaPlayer mp) {
//        pDialog.dismiss();
//        binding.vView.start();
//        }
//        });