package project.topmovies.visual;


import project.topmovies.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;


public class MovieTrailer_Activity extends AppCompatActivity {

    // VARIABLES //

    YouTubePlayerView youTubePlayerView;

    private String videoID;

    private String trailerURL;


    // ACTIVITY ACTIONS //

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // Set screen in fullscreen mode
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.movietrailer_layout);


        // Hide our action bar.
        getSupportActionBar().hide();


        // Load URL of trailer
        trailerURL = (String) getIntent().getSerializableExtra("TRAILER");

        // Get the video ID
        videoID = trailerURL.substring(trailerURL.indexOf('=') + 1);


        // Access to views
        youTubePlayerView = findViewById(R.id.YouTubePlayerView_Trailer);



        // Add listener for the YouTubePlayerView
        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {

            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {

                // Loading the selected video into the YouTube Player
                youTubePlayer.loadVideo(videoID, 0);

            }

            @Override
            public void onStateChange(@NonNull YouTubePlayer youTubePlayer, @NonNull PlayerConstants.PlayerState state) {

                // This method is called if video has ended
                super.onStateChange(youTubePlayer, state);

            }

        });

    }

}