package com.necohorne.bakingapp.UI.Fragments;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.necohorne.bakingapp.Models.Recipe;
import com.necohorne.bakingapp.Models.Step;
import com.necohorne.bakingapp.R;
import com.necohorne.bakingapp.UI.Activities.MenuActivity;
import com.necohorne.bakingapp.Utils.Constants;

public class RecipeDetailFragment extends Fragment {

    private static final String TAG = RecipeDetailFragment.class.getSimpleName();

    public SimpleExoPlayerView mPlayerView;
    public SimpleExoPlayer mExoPlayer;
    public TextView mDetailView;
    public Button mButton;
    private Bundle mBundle;
    private Step mStep;
    private Recipe mRecipe;
    private int mCurrentIndex;
    private long mResumePosition;

    public RecipeDetailFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null){
            //check instance state for a saved bundle else get bundle from args
            mResumePosition = savedInstanceState.getLong(Constants.CURRENT_POSITION);
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

        if(!mStep.getVideoURL().isEmpty()){
            initPlayer(Uri.parse(mStep.getVideoURL()));
        }else {
//            The below method does not want to work and i have no clue why
//            mPlayerView.setDefaultArtwork(BitmapFactory.decodeResource
//                    (getResources(), R.mipmap.baking_image));
        }

        return rootView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if(mBundle != null){
            outState.putBundle(Constants.STEP_BUNDLE , mBundle);
            outState.putLong(Constants.CURRENT_POSITION, mResumePosition);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {

        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) mPlayerView.getLayoutParams();
            params.width= params.MATCH_PARENT;
            params.height= params.MATCH_PARENT;
            mPlayerView.setLayoutParams(params);
            getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE);
            ((MenuActivity) getActivity()).getSupportActionBar().hide();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) mPlayerView.getLayoutParams();
            params.width=params.MATCH_CONSTRAINT;
            params.height=params.MATCH_CONSTRAINT;
            mPlayerView.setLayoutParams(params);
            getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            ((MenuActivity) getActivity()).getSupportActionBar().show();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mPlayerView != null && mPlayerView.getPlayer() != null) {
            mResumePosition = Math.max(0, mPlayerView.getPlayer().getCurrentPosition());

            mPlayerView.getPlayer().release();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
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

                            android.support.v4.app.FragmentManager fragmentManager = ((MenuActivity)getContext()).getSupportFragmentManager();
                            if(fragmentManager != null) {
                                releasePlayer();
                                fragmentManager.beginTransaction()
                                        .replace(R.id.menu_frame_layout, recipeDetailFragment)
                                        .addToBackStack(Constants.STEP_FRAGMENT)
                                        .commit();
                            }
                        }
                    }
                }
            });
        }
    }

    private void initPlayer(Uri uri){
        if(mExoPlayer == null){
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);
            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(getContext(), "BakingApp");
            MediaSource mediaSource = new ExtractorMediaSource(uri, new DefaultDataSourceFactory(
                    getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    private void releasePlayer() {
        if(mExoPlayer != null){
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

}
