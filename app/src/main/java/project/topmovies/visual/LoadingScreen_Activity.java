package project.topmovies.visual;


import project.topmovies.*;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;


public class LoadingScreen_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.loading_layout);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                Intent intentLoadingScreen = new Intent(LoadingScreen_Activity.this, Start_Activity.class);

                startActivity(intentLoadingScreen);

                finish();

            }

        },3000);

    }

}