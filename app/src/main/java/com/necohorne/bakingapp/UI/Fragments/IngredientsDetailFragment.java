package com.necohorne.bakingapp.UI.Fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.necohorne.bakingapp.Models.Recipe;
import com.necohorne.bakingapp.R;
import com.necohorne.bakingapp.Utils.Constants;
import com.necohorne.bakingapp.Utils.RecyclerAdapters.IngredientsRecyclerAdapter;

public class IngredientsDetailFragment extends Fragment {

    public RecyclerView mRecyclerView;
    private Bundle mBundle;
    private Recipe mRecipe;

    public IngredientsDetailFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null){
            //check instance state for a saved bundle else get bundle from args
            mBundle = savedInstanceState.getBundle(Constants.RECIPE_BUNDLE);
            if(mBundle != null) {
                mRecipe = mBundle.getParcelable(Constants.RECIPE);
            }
        } else {
            //get bundle from arguments and make recipe object which we can pass to populate the ui elements.
            mBundle = getArguments();
            if(mBundle != null) {
                mRecipe = mBundle.getParcelable(Constants.RECIPE);
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.ingredient_layout , container, false);

        setRecycler(rootView);

        if(mRecipe!= null){
            String title = mRecipe.getName() + " " + getString(R.string.Ingredients);
            getActivity().setTitle(title);
        }

        return rootView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if(mBundle != null){
            outState.putBundle(Constants.RECIPE_BUNDLE , mBundle);
        }
    }

    private void setRecycler(View rootView){
        mRecyclerView = rootView.findViewById(R.id.ingredient_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        IngredientsRecyclerAdapter ingredientsRecyclerAdapter = new IngredientsRecyclerAdapter(mRecipe.getIngredients(), getContext());
        mRecyclerView.setAdapter(ingredientsRecyclerAdapter);
    }


}
