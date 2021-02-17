package project.topmovies.visual.fragments;


import project.topmovies.*;
import project.topmovies.logic.adapters.RecyclerView_Adapter;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class ComingSoon_Fragment extends Fragment {

    private View thisView;

    private RecyclerView mRecyclerView;
    private RecyclerView_Adapter mAdapter;

    private List<String> myDataSet;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        thisView = inflater.inflate(R.layout.comingsoon_fraglayout, container, false);

        // LOAD DATA

        myDataSet = new ArrayList<>();

        myDataSet.add("Java");
        myDataSet.add("Python");
        myDataSet.add("Patata");

        mAdapter = new RecyclerView_Adapter(myDataSet, container.getContext());

        //

        mRecyclerView = thisView.findViewById(R.id.recyclerView_ComingSoon);

        mRecyclerView.setHasFixedSize(true);

        GridLayoutManager layoutManager = new GridLayoutManager(container.getContext(), 2);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setAdapter(mAdapter);

        return thisView;

    }

}