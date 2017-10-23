package com.abdulahiosoble.healthreach.Adapters;

import android.content.Context;
import android.util.Log;

import com.abdulahiosoble.healthreach.Models.Rating;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;

public class ReviewAdapter extends FirebaseRecyclerAdapter<Rating, ReviewViewHolder> {
    private static final String TAG = ReviewAdapter.class.getSimpleName();
    private Context context;

    public ReviewAdapter(Class<Rating> modelClass, int modelLayout, Class<ReviewViewHolder> viewHolderClass, DatabaseReference ref, Context context) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        this.context = context;
        Log.i(TAG, "Was Called");
    }

    @Override
    protected void populateViewHolder(ReviewViewHolder viewHolder, Rating model, int position) {
        viewHolder.rProblem.setText(model.getProblem());
        viewHolder.mRatingBar.setStar(model.getRating());
        viewHolder.rComment.setText(model.getComment());
        viewHolder.mRatingBar.setClickable(false);
        Log.i("PopulateViewHolder", "Was called.");
    }
}
