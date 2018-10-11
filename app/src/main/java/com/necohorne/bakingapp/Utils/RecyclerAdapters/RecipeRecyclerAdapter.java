package com.necohorne.bakingapp.Utils.RecyclerAdapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.necohorne.bakingapp.Models.Recipe;
import com.necohorne.bakingapp.R;
import com.necohorne.bakingapp.Utils.Constants;

import java.util.ArrayList;

public class RecipeRecyclerAdapter extends RecyclerView.Adapter<RecipeRecyclerAdapter.ViewHolder> {

    private final ArrayList<Recipe> mRecipeList;
    private final Context mContext;

    public RecipeRecyclerAdapter(ArrayList<Recipe> recipeList, Context context) {
        mRecipeList = recipeList;
        mContext = context;
    }

    @NonNull
    @Override
    public RecipeRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_card_list_item, parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeRecyclerAdapter.ViewHolder holder, int position) {
        final Recipe recipe = mRecipeList.get(position);

        holder.mTitleText.setText(recipe.getName());
        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.bakeing_list_image);

        int imageWidth = bitmap.getWidth();
        int imageHeight = bitmap.getHeight();

        DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();

        int newWidth = metrics.widthPixels;
        float scaleFactor = (float)newWidth/(float)imageWidth;
        int newHeight = (int)(imageHeight * scaleFactor);

        bitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);
        holder.mImageView.setImageBitmap(bitmap);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(Constants.RECIPE, recipe);
            }
        });

    }


    @Override
    public int getItemCount() {
        return mRecipeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mTitleText;
        public ImageView mImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            mTitleText = itemView.findViewById(R.id.list_title_tv);
            mImageView = itemView.findViewById(R.id.main_banner_image);
        }
    }
}
