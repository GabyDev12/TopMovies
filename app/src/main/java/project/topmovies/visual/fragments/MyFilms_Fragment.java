package project.topmovies.visual.fragments;


import project.topmovies.*;
import project.topmovies.logic.Movie;
import project.topmovies.logic.MovieSeen;
import project.topmovies.logic.adapters.RecyclerView_MovieSeen_Adapter;
import project.topmovies.logic.adapters.RecyclerView_Movie_Adapter;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class MyFilms_Fragment extends Fragment {

    // VARIABLES //

    private View thisView;

    private RecyclerView mRecyclerView;
    private RecyclerView_MovieSeen_Adapter mAdapter;

    private ProgressBar progressBar_MyFilms;

    private TextView textView_noMyFilms;

    private List<MovieSeen> moviesSeenList;


    private FirebaseAuth mAuth;

    private DatabaseReference moviesSeenRef;


    // FRAGMENT ACTIONS //

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        thisView = inflater.inflate(R.layout.myfilms_fraglayout, container, false);


        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();


        // Access to views
        progressBar_MyFilms = thisView.findViewById(R.id.progressBar_MyFilms);

        textView_noMyFilms = thisView.findViewById(R.id.textView_noMyFilms);


        // Get reference of data in Firebase
        moviesSeenRef = FirebaseDatabase.getInstance().getReference("users").child(mAuth.getCurrentUser().getUid());


        // LOAD DATA

        moviesSeenList = new ArrayList<>();


        // Check if the user has watched movies
        moviesSeenRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                // If there are watched films
                if (snapshot.hasChild("watchedMovies")) {

                    moviesSeenRef = moviesSeenRef.child("watchedMovies");

                    moviesSeenRef.addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            // Loop through all movies seen and store each one in the list
                            for (DataSnapshot movieDS : snapshot.getChildren()) {

                                // Get "Movie Title"
                                String movieTitle = movieDS.getKey();

                                // Get "Date Watched"
                                String dateWatched = movieDS.child("dateWatched").getValue(String.class);

                                // Get "Time Watched"
                                String timeWatched = movieDS.child("timeWatched").getValue(String.class);

                                // Get "Tickets Number"
                                String ticketsNumber = movieDS.child("ticketsNumber").getValue(String.class) + " tickets";

                                // Get "Final Price"
                                String finalPrice = movieDS.child("finalPrice").getValue(String.class) + " €";


                                // Create movie seen object and add it to the list
                                MovieSeen movieSeen = new MovieSeen(movieTitle, dateWatched, timeWatched, ticketsNumber, finalPrice);

                                moviesSeenList.add(movieSeen);

                            }

                            mAdapter = new RecyclerView_MovieSeen_Adapter(moviesSeenList, container.getContext());


                            // Configure RecyclerView
                            mRecyclerView = thisView.findViewById(R.id.recyclerView_MyFilms);

                            mRecyclerView.setHasFixedSize(true);

                            LinearLayoutManager layoutManager = new LinearLayoutManager(container.getContext());
                            mRecyclerView.setLayoutManager(layoutManager);

                            mRecyclerView.setAdapter(mAdapter);

                            // Hide the progress bar
                            progressBar_MyFilms.setVisibility(View.GONE);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                            Toast.makeText(getActivity(), "There was a problem loading the watched movies data. Sorry", Toast.LENGTH_LONG).show();

                            // Hide the progress bar
                            progressBar_MyFilms.setVisibility(View.GONE);

                        }

                    });

                }

                // If there are no watched films
                else {

                    textView_noMyFilms.setVisibility(View.VISIBLE);

                    // Hide the progress bar
                    progressBar_MyFilms.setVisibility(View.GONE);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }

        });

        //


        return thisView;

    }

}