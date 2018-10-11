package com.necohorne.bakingapp.UI.Activities;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initRecycler();

        new getRecipes().execute();

    }

    private void initRecycler() {
        mRecyclerView = findViewById(R.id.main_recycler_view);
        LinearLayoutManager recipeLayoutManager = new LinearLayoutManager(MainActivity.this);
        mRecyclerView.setLayoutManager(recipeLayoutManager);

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
            }
            super.onPostExecute(recipes);
        }
    }
}
