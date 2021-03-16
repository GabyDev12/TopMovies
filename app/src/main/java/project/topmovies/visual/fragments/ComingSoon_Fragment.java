package project.topmovies.visual.fragments;


import project.topmovies.*;
import project.topmovies.logic.Movie;
import project.topmovies.logic.adapters.RecyclerView_Movie_Adapter;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class ComingSoon_Fragment extends Fragment {

    // VARIABLES //

    private View thisView;

    private RecyclerView mRecyclerView;
    private RecyclerView_Movie_Adapter mAdapter;

    private ProgressBar progressBar_ComingSoon;

    private List<Movie> moviesList;

    private DatabaseReference moviesRef;


    // FRAGMENT ACTIONS //

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        thisView = inflater.inflate(R.layout.comingsoon_fraglayout, container, false);


        // Access to views
        progressBar_ComingSoon = thisView.findViewById(R.id.progressBar_ComingSoon);


        // Get reference of data in Firebase
        moviesRef = FirebaseDatabase.getInstance().getReference("movies");


        // LOAD DATA

        moviesList = new ArrayList<>();

        moviesRef.addValueEventListener(new ValueEventListener() {

            @RequiresApi (api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                // Loop through all movies and store each one in the list
                for (DataSnapshot movieDS : snapshot.getChildren()) {

                    // Get "Release Date"
                    String releaseDate = movieDS.child("release-date").getValue(String.class);

                    // Only load the movies which "Release Date" is after the actual date
                    if (LocalDate.parse(releaseDate).isAfter(LocalDate.now())) {

                        // Get "Title"
                        String title = movieDS.getKey();

                        // Get "Actors"
                        List<String> actors = new ArrayList<>();

                        if (movieDS.child("actors").hasChildren()) {

                            for (DataSnapshot actorsDS : movieDS.child("actors").getChildren()) {

                                actors.add(actorsDS.getValue(String.class));

                            }

                        }

                        else  {

                            actors.add(movieDS.child("actors").getValue(String.class));

                        }

                        // Get "Categories"
                        List<String> categories = new ArrayList<>();

                        if (movieDS.child("categories").hasChildren()) {

                            for (DataSnapshot categoriesDS : movieDS.child("categories").getChildren()) {

                                categories.add(categoriesDS.getValue(String.class));

                            }

                        }

                        else  {

                            categories.add(movieDS.child("categories").getValue(String.class));

                        }

                        // Get "Director"
                        List<String> director = new ArrayList<>();

                        if (movieDS.child("director").hasChildren()) {

                            for (DataSnapshot directorDS : movieDS.child("director").getChildren()) {

                                director.add(directorDS.getValue(String.class));

                            }

                        }

                        else  {

                            director.add(movieDS.child("director").getValue(String.class));

                        }

                        // Get "Poster URL"
                        String posterURL = movieDS.child("poster").getValue(String.class);

                        // Get "Runtime"
                        String runtime = Long.toString(movieDS.child("runtime").getValue(Long.class));

                        // Get "Synopsis"
                        String synopsis = movieDS.child("synopsis").getValue(String.class);

                        // Get "Trailer URL"
                        String trailerURL = movieDS.child("trailer").getValue(String.class);

                        // Get "Year"
                        String year = Long.toString(movieDS.child("year").getValue(Long.class));


                        // Create movie object and add it to the list
                        Movie movie = new Movie(title, actors, categories, director, posterURL, releaseDate, runtime, synopsis, trailerURL, year);

                        moviesList.add(movie);

                    }

                }

                mAdapter = new RecyclerView_Movie_Adapter(moviesList, container.getContext());


                // Configure RecyclerView
                mRecyclerView = thisView.findViewById(R.id.recyclerView_ComingSoon);

                mRecyclerView.setHasFixedSize(true);

                GridLayoutManager layoutManager = new GridLayoutManager(container.getContext(), 2);
                mRecyclerView.setLayoutManager(layoutManager);

                mRecyclerView.setAdapter(mAdapter);

                // Hide the progress bar
                progressBar_ComingSoon.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(getActivity(), "There was a problem loading the movies data. Sorry", Toast.LENGTH_LONG).show();

                // Hide the progress bar
                progressBar_ComingSoon.setVisibility(View.GONE);

            }

        });

        //

        return thisView;

    }

}