package project.topmovies.visual.fragments;


import project.topmovies.*;
import project.topmovies.logic.Movie;
import project.topmovies.logic.adapters.RecyclerView_Adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ComingSoon_Fragment extends Fragment {

    // VARIABLES //

    private View thisView;

    private RecyclerView mRecyclerView;
    private RecyclerView_Adapter mAdapter;

    private List<Movie> moviesList;

    private DatabaseReference moviesRef;


    // FRAGMENT ACTIONS //

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        thisView = inflater.inflate(R.layout.comingsoon_fraglayout, container, false);


        // Get reference of data in Firebase

        moviesRef = FirebaseDatabase.getInstance().getReference("movies");


        // LOAD DATA

        moviesList = new ArrayList<>();

        moviesRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(getActivity(), "There was a problem loading the movies data. Sorry", Toast.LENGTH_LONG).show();

            }

        });

        mAdapter = new RecyclerView_Adapter(moviesList, container.getContext());

        //

        mRecyclerView = thisView.findViewById(R.id.recyclerView_ComingSoon);

        mRecyclerView.setHasFixedSize(true);

        GridLayoutManager layoutManager = new GridLayoutManager(container.getContext(), 2);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setAdapter(mAdapter);

        return thisView;

    }

}