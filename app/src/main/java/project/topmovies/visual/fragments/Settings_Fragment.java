package project.topmovies.visual.fragments;


import project.topmovies.*;
import project.topmovies.logic.statusApp;
import project.topmovies.visual.ChangePassword_Activity;
import project.topmovies.visual.ChangeProfile_Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class Settings_Fragment extends Fragment {

    // VARIABLES //

    private View thisView;


    private TextView textView_cProfile;

    private TextView textView_cPassword;


    // FRAGMENT ACTIONS //

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        thisView = inflater.inflate(R.layout.settings_fraglayout, container, false);


        // Access to views
        textView_cProfile = thisView.findViewById(R.id.textView_cProfile);
        textView_cPassword = thisView.findViewById(R.id.textView_cPassword);


        // Action change profile
        textView_cProfile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // If is a normal user
                if (statusApp.getInstance().gAuth == false) {

                    Intent intentChangeProfile = new Intent(getActivity(), ChangeProfile_Activity.class);

                    startActivity(intentChangeProfile);

                }

                // If is a Google user
                else {

                    Intent intentGoogleAccount = new Intent(Intent.ACTION_VIEW, Uri.parse("https://myaccount.google.com"));

                    startActivity(intentGoogleAccount);

                }

            }

        });


        // If is a normal user
        if (statusApp.getInstance().gAuth == false) {

            textView_cPassword.setVisibility(View.VISIBLE);


            // Action change password
            textView_cPassword.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    Intent intentChangePassword = new Intent(getActivity(), ChangePassword_Activity.class);

                    startActivity(intentChangePassword);

                }

            });

        }

        // If is a Google user
        else {

            textView_cPassword.setVisibility(View.INVISIBLE);

        }


        return thisView;

    }

}