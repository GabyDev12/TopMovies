package project.topmovies.visual.fragments;


import project.topmovies.*;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class About_Fragment extends Fragment {

    // VARIABLES //

    private View thisView;


    // FRAGMENT ACTIONS //

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        thisView = inflater.inflate(R.layout.about_fraglayout, container, false);

        return thisView;

    }

}