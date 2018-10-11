package com.necohorne.bakingapp.UI.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.necohorne.bakingapp.Models.Recipe;
import com.necohorne.bakingapp.Models.Step;
import com.necohorne.bakingapp.R;
import com.necohorne.bakingapp.UI.Activities.MainActivity;
import com.necohorne.bakingapp.UI.Activities.MenuActivity;
import com.necohorne.bakingapp.Utils.Constants;

public class RecipeDetailFragment extends Fragment {

    private static final String TAG = RecipeDetailFragment.class.getSimpleName();

    public SimpleExoPlayerView mPlayerView;
    public TextView mDetailView;
    public Button mButton;
    private Bundle mBundle;
    private Step mStep;
    private Recipe mRecipe;
    private int mCurrentIndex;

    public RecipeDetailFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null){
            //check instance state for a saved bundle else get bundle from args
            mBundle = savedInstanceState.getBundle(Constants.STEP_BUNDLE);
            if(mBundle != null) {
                mStep = mBundle.getParcelable(Constants.STEP);
                mRecipe = mBundle.getParcelable(Constants.RECIPE);
            }
        } else {
            //get bundle from arguments and make step object which we can pass to populate the ui elements.
            mBundle = getArguments();
            if(mBundle != null) {
                mStep = mBundle.getParcelable(Constants.STEP);
                mRecipe = mBundle.getParcelable(Constants.RECIPE);
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.recipe_detail_layout , container, false);
        mPlayerView = rootView.findViewById(R.id.exoplayer_view);
        mDetailView = rootView.findViewById(R.id.step_detail_text_view);
        mDetailView.setText(mStep.getDescription());
        setUpNextButton(rootView);

        return rootView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if(mBundle != null){
            outState.putBundle(Constants.STEP_BUNDLE , mBundle);
        }
    }

    private void setUpNextButton(View rootView) {

        mButton = rootView.findViewById(R.id.next_button);

        mCurrentIndex = mStep.getId();
        if(mRecipe != null){
            if(mCurrentIndex == mRecipe.getSteps().size() - 1){
                mButton.setVisibility(View.GONE);
            }

            mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mStep != null){
                        if(mCurrentIndex < mRecipe.getSteps().size() - 1){
                            Step nextStep = mRecipe.getSteps().get(mCurrentIndex + 1);

                            Bundle bundle = new Bundle();
                            bundle.putParcelable(Constants.STEP, nextStep);
                            bundle.putParcelable(Constants.RECIPE, mRecipe);

                            RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
                            recipeDetailFragment.setArguments(bundle);

                            if(getFragmentManager() != null) {
                                getFragmentManager().beginTransaction()
                                        .replace(R.id.menu_frame_layout, recipeDetailFragment)
                                        .commit();
                            }
                        }
                    }
                }
            });
        }



    }
}
