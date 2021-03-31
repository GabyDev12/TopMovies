package project.topmovies.visual.fragments;


import project.topmovies.*;
import project.topmovies.logic.statusApp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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
import com.squareup.picasso.Picasso;


public class Profile_Fragment extends Fragment {

    // VARIABLES //

    private View thisView;


    private ImageView imageView_pUserImage;


    private TextView textView_pUserName;

    private TextView textView_pID;


    private RelativeLayout relativeLayout_FirebaseUserInfo;

    private TextView textView_pName;

    private TextView textView_pLastname;


    private TextView textView_pEmail;

    private TextView textView_pWatchedMovies;


    private Button button_pSignOut;


    private FirebaseAuth mAuth;

    private GoogleSignInClient mGoogleSignInClient;

    private DatabaseReference usersRef;


    // FRAGMENT ACTIONS //

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        thisView = inflater.inflate(R.layout.profile_fraglayout, container, false);


        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();


        // Access to views
        imageView_pUserImage = thisView.findViewById(R.id.imageView_pUserImage);


        textView_pUserName = thisView.findViewById(R.id.textView_pUserName);
        textView_pID = thisView.findViewById(R.id.textView_pID);


        relativeLayout_FirebaseUserInfo = thisView.findViewById(R.id.relativeLayout_FirebaseUserInfo);

        textView_pName = thisView.findViewById(R.id.textView_pName);
        textView_pLastname = thisView.findViewById(R.id.textView_pLastname);


        textView_pEmail = thisView.findViewById(R.id.textView_pEmail);
        textView_pWatchedMovies = thisView.findViewById(R.id.textView_pWatchedMovies);


        button_pSignOut = thisView.findViewById(R.id.button_pSignOut);


        // Check if a user is logged in
        if (mAuth.getCurrentUser() != null) {

            // Get reference of actual user data in Firebase
            usersRef = FirebaseDatabase.getInstance().getReference("users").child(mAuth.getCurrentUser().getUid());


            // LOAD DATA

            textView_pID.setText("ID: " + mAuth.getCurrentUser().getUid());


            // Load the profile image of the user
            if (mAuth.getCurrentUser().getPhotoUrl() != null) {

                Picasso.with(getActivity())
                        .load(mAuth.getCurrentUser().getPhotoUrl())
                        .fit()
                        .centerCrop()
                        .placeholder(R.drawable.img_loading)
                        .into(imageView_pUserImage);

            }


            // If is a normal user
            if (statusApp.getInstance().gAuth == false) {

                usersRef.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        textView_pUserName.setText(snapshot.child("name").getValue(String.class) + " " + snapshot.child("lastName").getValue(String.class));

                        textView_pName.setText(snapshot.child("name").getValue(String.class));
                        textView_pLastname.setText(snapshot.child("lastName").getValue(String.class));

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                        Toast.makeText(getActivity(), "There was a problem loading the user data. Sorry", Toast.LENGTH_LONG).show();

                    }

                });

                relativeLayout_FirebaseUserInfo.setVisibility(View.VISIBLE);

            }

            // If is a Google user
            else {

                textView_pUserName.setText("Google User");

                relativeLayout_FirebaseUserInfo.setVisibility(View.GONE);

            }

            textView_pEmail.setText(mAuth.getCurrentUser().getEmail());

            // Count watched movies by the user
            usersRef.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    // If there are watched films
                    if (snapshot.hasChild("watchedMovies")) {

                        usersRef.child("watchedMovies").addValueEventListener(new ValueEventListener() {

                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                long amountWatchedMovies = snapshot.getChildrenCount();

                                textView_pWatchedMovies.setText(String.valueOf(amountWatchedMovies));

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                                Toast.makeText(getActivity(), "There was a problem counting the watched movies. Sorry", Toast.LENGTH_LONG).show();

                            }

                        });

                    }

                    else {

                        textView_pWatchedMovies.setText("0");

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                    Toast.makeText(getActivity(), "There was a problem loading the watched movies data. Sorry", Toast.LENGTH_LONG).show();

                }

            });


            //

        }


        // Action sign out
        button_pSignOut.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // Firebase sign out
                mAuth.signOut();

                statusApp.getInstance().loggedIn = false;


                // Google sign out
                if (statusApp.getInstance().gAuth == true) {

                    // Configure Google Sign In
                    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .requestIdToken("209581455260-2qkd36kiu1vi8gqsumbrqrn6o7noi8gf.apps.googleusercontent.com")
                            .requestEmail()
                            .build();

                    mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);


                    mGoogleSignInClient.signOut()
                            .addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {

                                @Override
                                public void onComplete(@NonNull Task<Void> task) { }

                            });

                    mGoogleSignInClient.revokeAccess()
                            .addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {

                                @Override
                                public void onComplete(@NonNull Task<Void> task) { }

                            });

                    statusApp.getInstance().gAuth = false;

                }


                // Reload the activity for apply the changes
                getActivity().recreate();


                // Inform the user
                Toast.makeText(getActivity(), "Signed out successfully!", Toast.LENGTH_LONG).show();

            }

        });


        return thisView;

    }

}