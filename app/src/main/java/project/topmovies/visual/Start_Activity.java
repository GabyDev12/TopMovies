package project.topmovies.visual;


import project.topmovies.*;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class Start_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.start_layout);

    }

    public void lang_Spanish(View v) {

        Intent intentSpanishHomeScreen = new Intent(this, HomeScreen_Activity.class);

        startActivity(intentSpanishHomeScreen);

    }

    public void lang_English(View v) {

        Intent intentEnglishHomeScreen = new Intent(this, HomeScreen_Activity.class);

        startActivity(intentEnglishHomeScreen);

    }

}