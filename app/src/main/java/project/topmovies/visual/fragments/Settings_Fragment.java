package project.topmovies.visual.fragments;


import project.topmovies.*;
import project.topmovies.logic.statusApp;
import project.topmovies.visual.ChangePassword_Activity;
import project.topmovies.visual.ChangeProfile_Activity;
import project.topmovies.visual.HomeScreen_Activity;
import project.topmovies.visual.SignUp_Activity;

import static project.topmovies.logic.statusApp.*;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


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

                Intent intentChangeProfile = new Intent(getActivity(), ChangeProfile_Activity.class);

                startActivity(intentChangeProfile);

            }

        });


        // Action change password
        textView_cPassword.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intentChangePassword = new Intent(getActivity(), ChangePassword_Activity.class);

                startActivity(intentChangePassword);

            }

        });


        return thisView;

    }

}