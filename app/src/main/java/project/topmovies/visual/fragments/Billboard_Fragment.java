package project.topmovies.visual.fragments;


import project.topmovies.*;
import project.topmovies.logic.RecyclerView_Adapter;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class Billboard_Fragment extends Fragment {

    private View thisView;

    private RecyclerView mRecyclerView;
    private RecyclerView_Adapter mAdapter;

    private List<String> myDataSet;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        thisView = inflater.inflate(R.layout.billboard_fraglayout, container, false);

        myDataSet = new ArrayList<>();

        myDataSet.add("Java");
        myDataSet.add("Python");
        myDataSet.add("Patata");
        myDataSet.add("Lombriz");
        myDataSet.add("Salsa");
        myDataSet.add("Azucar");
        myDataSet.add("Efe");

        mAdapter = new RecyclerView_Adapter(myDataSet, container.getContext());

        mRecyclerView = thisView.findViewById(R.id.recyclerView);

        // Esta línea mejora el rendimiento, si sabemos que el contenido
        // no va a afectar al tamaño del RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // Nuestro RecyclerView usará un linear layout manager
        GridLayoutManager layoutManager = new GridLayoutManager(container.getContext(), 2);
        mRecyclerView.setLayoutManager(layoutManager);

        // Asociamos un adapter (ver más adelante cómo definirlo)
        mRecyclerView.setAdapter(mAdapter);

        return  thisView;

    }

}