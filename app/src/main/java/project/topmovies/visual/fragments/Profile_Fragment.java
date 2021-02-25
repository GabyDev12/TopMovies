package project.topmovies.visual.fragments;


import project.topmovies.*;
import static project.topmovies.logic.statusApp.*;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class Profile_Fragment extends Fragment {

    // VARIABLES //

    private View thisView;


    // FRAGMENT ACTIONS //

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        thisView = inflater.inflate(R.layout.profile_fraglayout, container, false);

        return thisView;

    }

}