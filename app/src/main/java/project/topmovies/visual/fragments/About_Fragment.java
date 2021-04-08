package project.topmovies.visual.fragments;


import project.topmovies.*;
import project.topmovies.logic.statusApp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


public class About_Fragment extends Fragment {

    // VARIABLES //

    private View thisView;


    private ImageView imageView_GitHub;


    // FRAGMENT ACTIONS //

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        thisView = inflater.inflate(R.layout.about_fraglayout, container, false);


        // Access to views
        imageView_GitHub = thisView.findViewById(R.id.imageView_GitHub);


        // Action click on GitHub image
        imageView_GitHub.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intentGitHubRepository = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/MrDev-12/TopMovies"));

                statusApp.getInstance().about = true;

                startActivity(intentGitHubRepository);

            }

        });


        return thisView;

    }

}