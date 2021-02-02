package project.topmovies.visual;


import project.topmovies.HomeScreen_Activity;
import project.topmovies.R;

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

                Intent intentPantallaCarga = new Intent(LoadingScreen_Activity.this, HomeScreen_Activity.class);

                startActivity(intentPantallaCarga);

                finish();

            }

        },3000);

    }

}