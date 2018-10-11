package com.necohorne.bakingapp.UI.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.necohorne.bakingapp.R;
import com.necohorne.bakingapp.UI.Fragments.RecipeMenuFragment;

public class MenuActivity extends AppCompatActivity {

    private static final String TAG = MenuActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        //get the bundle from the intent, pass bundle to fragment as arguments;
        Intent sentIntent = getIntent();
        Bundle bundle = sentIntent.getExtras();
        if(bundle != null){
            RecipeMenuFragment recipeMenuFragment = new RecipeMenuFragment();
            recipeMenuFragment.setArguments(bundle);

            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.menu_frame_layout, recipeMenuFragment)
                    .commit();
        }


    }
}
