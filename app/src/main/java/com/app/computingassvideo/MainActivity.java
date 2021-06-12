package com.app.computingassvideo;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.app.computingassvideo.databinding.ActivityMainBinding;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ExoPlayer player;
    ArrayList<String> arrayList = new ArrayList<>();
    ActivityMainBinding activityMainBinding;
    private boolean playWhenReady = true;
    private int currentWindow = 0;
    private long playbackPosition = 0;

    int runningVideo = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(LayoutInflater.from(MainActivity.this), null, false);
        setContentView(activityMainBinding.getRoot());
        arrayList.add("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4");
        arrayList.add("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4");
        arrayList.add("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4");
        arrayList.add("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4");
        activityMainBinding.v1.setOnClickListener(v -> {
            initVideo(arrayList.get(0));
            runningVideo=1;
        });

        activityMainBinding.v2.setOnClickListener(v -> {
            initVideo(arrayList.get(1));
            runningVideo=2;

        });

        activityMainBinding.v3.setOnClickListener(v -> {
            initVideo(arrayList.get(2));
            runningVideo=3;

        });

        activityMainBinding.v4.setOnClickListener(v -> {
            initVideo(arrayList.get(3));
            runningVideo=4;

        });
    }

    private void initVideo(String url) {
        if(runningVideo!=-1){
            releasePlayer();
            currentWindow = 0;
            playbackPosition = 0;
        }
        player = ExoPlayerFactory.newSimpleInstance(MainActivity.this);
        activityMainBinding.sep.setPlayer(player);
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(MainActivity.this, "exo-player");
        Uri uri = Uri.parse(url);

        MediaSource mediaSource = new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(uri);

        player.setPlayWhenReady(playWhenReady);
        player.seekTo(currentWindow, playbackPosition);

        player.prepare(mediaSource,false,false);

    }

    private void releasePlayer(){
        if(player != null){
            playWhenReady = player.getPlayWhenReady();
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            player.release();
            player = null;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if(runningVideo!=-1){
            initVideo(arrayList.get(runningVideo));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        releasePlayer();
    }

    @Override
    protected void onStop() {
        super.onStop();
        releasePlayer();
    }
}