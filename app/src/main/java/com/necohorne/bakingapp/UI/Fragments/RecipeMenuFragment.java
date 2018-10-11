package com.necohorne.bakingapp.UI.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.necohorne.bakingapp.R;

public class RecipeMenuFragment extends Fragment {

    public RecyclerView mRecyclerView;
    public CardView mCardView;

    public RecipeMenuFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.recipe_menu_layout , container, false);
        mRecyclerView = rootView.findViewById(R.id.recipe_menu_recycler_view);
        mCardView = rootView.findViewById(R.id.ingredients_card_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);

        return rootView;
    }
}
