package com.necohorne.bakingapp.UI.Fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.necohorne.bakingapp.Models.Ingredients;
import com.necohorne.bakingapp.Models.Recipe;
import com.necohorne.bakingapp.R;
import com.necohorne.bakingapp.Utils.Constants;
import com.necohorne.bakingapp.Utils.RecyclerAdapters.StepRecyclerAdapter;

public class RecipeMenuFragment extends Fragment {

    public RecyclerView mRecyclerView;
    public CardView mCardView;
    public Recipe mRecipe;
    public ImageView mImageView;
    private Bundle mBundle;

    public RecipeMenuFragment() {
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

        View rootView = inflater.inflate(R.layout.recipe_menu_layout , container, false);
        setRecycler(rootView);
        setIngredientView(rootView);
        setImageBanner(rootView);

        return rootView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if(mBundle != null){
            outState.putBundle(Constants.RECIPE_BUNDLE , mBundle);
        }

    }

    private void setRecycler(View rootView) {
        mRecyclerView = rootView.findViewById(R.id.recipe_menu_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        if(mRecipe != null){
            getActivity().setTitle(mRecipe.getName());
            StepRecyclerAdapter stepRecyclerAdapter = new StepRecyclerAdapter(mRecipe.getSteps() , getContext(), mRecipe);
            mRecyclerView.setAdapter(stepRecyclerAdapter);
        }
    }

    public void setImageBanner(View rootView){

        mImageView = rootView.findViewById(R.id.menu_ingredient_image_view);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.baking_image);
        int imageWidth = bitmap.getWidth();
        int imageHeight = bitmap.getHeight();
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int newWidth = metrics.widthPixels;
        float scaleFactor = (float)newWidth/(float)imageWidth;
        int newHeight = (int)(imageHeight * scaleFactor);
        bitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);
        mImageView.setImageBitmap(bitmap);
    }

    public void setIngredientView(View rootView){

        mCardView = rootView.findViewById(R.id.ingredients_card_view);
        mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mRecipe != null){
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(Constants.RECIPE, mRecipe);
                    IngredientsDetailFragment ingredientsDetailFragment = new IngredientsDetailFragment();
                    ingredientsDetailFragment.setArguments(bundle);
                    if(getFragmentManager() != null) {
                        getFragmentManager().beginTransaction()
                                .replace(R.id.menu_frame_layout, ingredientsDetailFragment)
                                .addToBackStack(Constants.RECIPE_FRAGMENT)
                                .commit();
                    }
                }
            }
        });
    }
}
