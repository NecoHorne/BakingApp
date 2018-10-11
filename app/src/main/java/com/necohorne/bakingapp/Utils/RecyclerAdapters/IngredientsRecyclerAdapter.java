package com.necohorne.bakingapp.Utils.RecyclerAdapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.necohorne.bakingapp.Models.Ingredients;
import com.necohorne.bakingapp.R;

import java.util.ArrayList;

public class IngredientsRecyclerAdapter extends RecyclerView.Adapter<IngredientsRecyclerAdapter.ViewHolder> {

    private ArrayList<Ingredients> mIngredientsList;
    private Context mContext;

    public IngredientsRecyclerAdapter(ArrayList<Ingredients> ingredientsList, Context context) {
        mIngredientsList = ingredientsList;
        mContext = context;
    }

    @NonNull
    @Override
    public IngredientsRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_list_item_layout, parent,false);
        return new  ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsRecyclerAdapter.ViewHolder holder, int position) {

        Ingredients ingredients = mIngredientsList.get(position);
        holder.ingredient.setText(ingredients.getIngredient());
        holder.quantity.setText(String.valueOf(ingredients.getQuantity()));
        holder.measurement.setText(ingredients.getMeasure());

    }

    @Override
    public int getItemCount() {
        return mIngredientsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView ingredient;
        public TextView quantity;
        public TextView measurement;

        public ViewHolder(View itemView) {
            super(itemView);
            ingredient = itemView.findViewById(R.id.list_ingredient_tv);
            quantity = itemView.findViewById(R.id.list_quantity_tv);
            measurement = itemView.findViewById(R.id.list_measure_tv);
        }
    }
}
