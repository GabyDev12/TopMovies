package project.topmovies.visual.fragments;


import project.topmovies.*;
import project.topmovies.logic.statusApp;
import project.topmovies.visual.ChangePassword_Activity;
import project.topmovies.visual.ChangeProfile_Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;


public class Settings_Fragment extends Fragment {

    // VARIABLES //

    private View thisView;


    private TextView textView_tAccountSettings;


    private TextView textView_cProfile;

    private TextView textView_cPassword;


    private ImageView imageView_LangES;

    private ImageView imageView_LangEN;


    // FRAGMENT ACTIONS //

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        thisView = inflater.inflate(R.layout.settings_fraglayout, container, false);


        // Access to views
        textView_tAccountSettings = thisView.findViewById(R.id.textView_tAccountSettings);

        textView_cProfile = thisView.findViewById(R.id.textView_cProfile);
        textView_cPassword = thisView.findViewById(R.id.textView_cPassword);

        imageView_LangES = thisView.findViewById(R.id.imageView_LangES);
        imageView_LangEN = thisView.findViewById(R.id.imageView_LangEN);


        // If the user is logged in
        if (!statusApp.getInstance().loggedIn) {

            textView_tAccountSettings.setVisibility(View.GONE);

            textView_cProfile.setVisibility(View.GONE);
            textView_cPassword.setVisibility(View.GONE);

        }

        // If the user is logged in
        else {

            textView_cProfile.setVisibility(View.VISIBLE);

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

                        statusApp.getInstance().settings = true;

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

                textView_cPassword.setVisibility(View.GONE);

            }

        }



        // Action change language to Spanish
        imageView_LangES.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                setLocale("es");

                statusApp.getInstance().settings = true;

                getActivity().recreate();

            }

        });


        // Action change language to English
        imageView_LangEN.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                setLocale("en");

                statusApp.getInstance().settings = true;

                getActivity().recreate();

            }

        });


        return thisView;

    }


    // Method for set the language selected by the user
    private void setLocale(String lang) {

        Locale locale = new Locale(lang);
        Locale.setDefault(locale);

        Configuration config = new Configuration();
        config.locale = locale;

        getActivity().getBaseContext().getResources().updateConfiguration(config, getActivity().getBaseContext().getResources().getDisplayMetrics());

        // Save data to shared preferences
        SharedPreferences.Editor editor = getActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE).edit();

        editor.putString("My_Lang", lang);
        editor.apply();

    }


    // Load the language saved in shared preferences
    private void loadLocale() {

        SharedPreferences prefs = getActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE);

        String language = prefs.getString("My_Lang", "");

        setLocale(language);

    }

}