package com.necohorne.bakingapp.UI.Activities;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.necohorne.bakingapp.IdlingResource.MainIdlingResource;
import com.necohorne.bakingapp.Models.Recipe;
import com.necohorne.bakingapp.R;
import com.necohorne.bakingapp.Utils.JsonUtils;
import com.necohorne.bakingapp.Utils.NetworkUtils;
import com.necohorne.bakingapp.Utils.RecyclerAdapters.RecipeRecyclerAdapter;

import java.io.IOException;
import java.util.ArrayList;

import static com.necohorne.bakingapp.Utils.NetworkUtils.recipeUrl;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    public RecyclerView mRecyclerView;
    public ArrayList<Recipe> mRecipeList;
    public RecipeRecyclerAdapter mRecipeRecyclerAdapter;
    private MainIdlingResource mIdlingResource;

    //TODO UI test.

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new MainIdlingResource();
        }
        return mIdlingResource;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initRecycler();
        getIdlingResource();
        new HasConnection().execute();
    }

    private void initRecycler() {
        mRecyclerView = findViewById(R.id.main_recycler_view);

        //check for tab size if so use a grid layout manager
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int widthDP = displayMetrics.widthPixels / (getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);

        if(widthDP > 600){
            GridLayoutManager layoutManager = new GridLayoutManager(MainActivity.this, 3);
            mRecyclerView.setLayoutManager(layoutManager);
        }else {
            LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
            mRecyclerView.setLayoutManager(layoutManager);
        }
    }

    public class HasConnection extends AsyncTask<Void, Void, Boolean>{

        @Override
        protected Boolean doInBackground(Void... voids) {
            return NetworkUtils.hasInternetAccess(getApplicationContext());
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if(aBoolean){
                new getRecipes().execute();
            }else {
                ImageView noConnection = findViewById(R.id.no_connection_iv);
                noConnection.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.INVISIBLE);
                Toast.makeText(MainActivity.this, "You are not connected to the internet, Please connect and try again", Toast.LENGTH_LONG).show();
            }
        }
    }

    public class getRecipes extends AsyncTask<Void, Void, ArrayList<Recipe>>{

        @Override
        protected ArrayList<Recipe> doInBackground(Void... voids) {
            try {
                return JsonUtils.getRecipeList(NetworkUtils.getResponseFromHttpUrl(recipeUrl()));
            } catch(IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Recipe> recipes) {
            mRecipeList = recipes;
            if(!mRecipeList.isEmpty() || mRecipeList != null){
                mRecipeRecyclerAdapter = new RecipeRecyclerAdapter(mRecipeList, MainActivity.this);
                mRecyclerView.setAdapter(mRecipeRecyclerAdapter);
                mIdlingResource.setIdleState(true);
            }
            super.onPostExecute(recipes);
        }
    }
}
