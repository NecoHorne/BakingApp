package com.necohorne.bakingapp.UI.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.necohorne.bakingapp.R;

public class RecipeDetailFragment extends Fragment {

    public SimpleExoPlayerView mPlayerView;
    public TextView mDetailView;
    public Button mButton;

    public RecipeDetailFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.recipe_detail_layout , container, false);
        mPlayerView = rootView.findViewById(R.id.exoplayer_view);
        mDetailView = rootView.findViewById(R.id.step_detail_text_view);
        mButton = rootView.findViewById(R.id.next_button);
        return rootView;

    }
}
