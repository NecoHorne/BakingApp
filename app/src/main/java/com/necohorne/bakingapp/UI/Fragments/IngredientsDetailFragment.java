package com.necohorne.bakingapp.UI.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.necohorne.bakingapp.R;

public class IngredientsDetailFragment extends Fragment {

    public RecyclerView mRecyclerView;

    public IngredientsDetailFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.ingredient_layout , container, false);
        mRecyclerView = rootView.findViewById(R.id.ingredient_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        getActivity().setTitle("Ingredients");
        return rootView;
    }
}
