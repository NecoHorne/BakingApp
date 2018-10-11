package com.necohorne.bakingapp.Utils.RecyclerAdapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.necohorne.bakingapp.Models.Recipe;
import com.necohorne.bakingapp.Models.Step;
import com.necohorne.bakingapp.R;
import com.necohorne.bakingapp.UI.Activities.MenuActivity;
import com.necohorne.bakingapp.UI.Fragments.RecipeDetailFragment;
import com.necohorne.bakingapp.Utils.Constants;

import java.util.ArrayList;

public class StepRecyclerAdapter extends RecyclerView.Adapter<StepRecyclerAdapter.ViewHolder>{


    private final ArrayList<Step> mStepList;
    private final Context mContext;
    private final Recipe mRecipe;

    public StepRecyclerAdapter(ArrayList<Step> stepList, Context context, Recipe recipe) {
        mStepList = stepList;
        mContext = context;
        mRecipe = recipe;
    }

    @NonNull
    @Override
    public StepRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_card_list_item, parent,false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StepRecyclerAdapter.ViewHolder holder, int position) {
        final Step step = mStepList.get(position);
        holder.mStepText.setText(step.getShortDescription());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(Constants.STEP, step);
                bundle.putParcelable(Constants.RECIPE, mRecipe);

                RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
                recipeDetailFragment.setArguments(bundle);

                android.support.v4.app.FragmentManager fragmentManager =((MenuActivity)mContext).getSupportFragmentManager();
                if(fragmentManager != null) {
                    fragmentManager.beginTransaction()
                            .replace(R.id.menu_frame_layout, recipeDetailFragment)
                            .addToBackStack(Constants.RECIPE_FRAGMENT)
                            .commit();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mStepList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mStepText;

        public ViewHolder(View itemView) {
            super(itemView);
           mStepText = itemView.findViewById(R.id.list_recipe_step_tv);
        }
    }
}
