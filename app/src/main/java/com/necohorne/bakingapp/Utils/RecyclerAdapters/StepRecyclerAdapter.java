package com.necohorne.bakingapp.Utils.RecyclerAdapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.necohorne.bakingapp.Models.Step;
import com.necohorne.bakingapp.R;

import java.util.ArrayList;

public class StepRecyclerAdapter extends RecyclerView.Adapter<StepRecyclerAdapter.ViewHolder>{


    private final ArrayList<Step> mStepList;
    private final Context mContext;

    public StepRecyclerAdapter(ArrayList<Step> stepList, Context context) {

        mStepList = stepList;
        mContext = context;
    }

    @NonNull
    @Override
    public StepRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_card_list_item, parent,false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StepRecyclerAdapter.ViewHolder holder, int position) {
        Step step = mStepList.get(position);
        holder.mStepText.setText(step.getShortDescription());
    }

    @Override
    public int getItemCount() {
        return mStepList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mStepText;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.findViewById(R.id.list_recipe_step_tv);
        }
    }
}
