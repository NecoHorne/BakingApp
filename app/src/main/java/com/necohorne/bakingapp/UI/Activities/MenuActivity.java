package com.necohorne.bakingapp.UI.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.necohorne.bakingapp.R;
import com.necohorne.bakingapp.UI.Fragments.IngredientsDetailFragment;
import com.necohorne.bakingapp.UI.Fragments.RecipeMenuFragment;
import com.necohorne.bakingapp.Utils.Constants;

public class MenuActivity extends AppCompatActivity {

    private static final String TAG = MenuActivity.class.getSimpleName();
    private boolean mTwoPane;
    private Bundle mBundle;
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        mTwoPane = findViewById(R.id.tab_linear_layout) != null;
        mFragmentManager = getSupportFragmentManager();

        /*
        check instance state for a saved bundle else get bundle from intent
        and pass to fragments as args.
         */
        if(savedInstanceState != null) {
            mBundle = savedInstanceState.getBundle(Constants.RECIPE_BUNDLE);
        }else {
            Intent sentIntent = getIntent();
            mBundle = sentIntent.getExtras();
        }

        /*
        to prevent multiple fragment overlays from switching between fragments or on screen orientation check if the
        frame layout contains a fragment, if so don't load another otherwise load as normal.
        */
        if(mFragmentManager.findFragmentById(R.id.menu_frame_layout) == null){
            if(mTwoPane) tabLayout();
            else phoneLayout();
        }
    }

    private void tabLayout() {
        if(mBundle != null){
            RecipeMenuFragment recipeMenuFragment = new RecipeMenuFragment();
            recipeMenuFragment.setArguments(mBundle);
            mFragmentManager.beginTransaction()
                    .add(R.id.tab_recipe_menu, recipeMenuFragment, Constants.RECIPE_FRAGMENT)
                    .commit();

            IngredientsDetailFragment ingredientsDetailFragment = new IngredientsDetailFragment();
            ingredientsDetailFragment.setArguments(mBundle);
            mFragmentManager.beginTransaction()
                    .replace(R.id.menu_frame_layout, ingredientsDetailFragment, Constants.INGREDIENT_FRAGMENT)
                    .commit();
        }
    }

    private void phoneLayout() {
        if(mBundle != null){
            RecipeMenuFragment recipeMenuFragment = new RecipeMenuFragment();
            recipeMenuFragment.setArguments(mBundle);
            mFragmentManager.beginTransaction()
                    .add(R.id.menu_frame_layout, recipeMenuFragment, Constants.RECIPE_FRAGMENT)
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //this is the easiest solution I have found to the backstack nightmare I am not sure if there is something that works better than this.
        int backStackEntryCount = mFragmentManager.getBackStackEntryCount();
        while(backStackEntryCount > 0){
            mFragmentManager.popBackStackImmediate();
            backStackEntryCount = mFragmentManager.getBackStackEntryCount();
        }

        if(mTwoPane){
            IngredientsDetailFragment ingredientsDetailFragment = new IngredientsDetailFragment();
            ingredientsDetailFragment.setArguments(mBundle);
            mFragmentManager.beginTransaction()
                    .replace(R.id.menu_frame_layout, ingredientsDetailFragment)
                    .addToBackStack(Constants.RECIPE_FRAGMENT)
                    .commit();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(mBundle != null){
            outState.putBundle(Constants.RECIPE_BUNDLE , mBundle);
        }
    }

}
