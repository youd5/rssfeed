package com.example.rssfeed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.youtube.player.YouTubeStandalonePlayer;

public class VideoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        Intent intent = YouTubeStandalonePlayer.createVideoIntent(this, "XXXX", "BFda1MZ54G4");
        startActivity(intent);

    }
}
