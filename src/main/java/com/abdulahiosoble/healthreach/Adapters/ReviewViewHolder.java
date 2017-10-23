package com.abdulahiosoble.healthreach.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.abdulahiosoble.healthreach.R;
import com.hedgehog.ratingbar.RatingBar;

/**
 * Created by abdulahiosoble on 10/3/17.
 */

public class ReviewViewHolder extends RecyclerView.ViewHolder {
    RatingBar mRatingBar;
    TextView rProblem, rComment;

    public ReviewViewHolder(View itemView) {
        super(itemView);
        mRatingBar = (RatingBar) itemView.findViewById(R.id.seperateRating);
        rProblem = (TextView) itemView.findViewById(R.id.problemTextView);
        rComment = (TextView) itemView.findViewById(R.id.commentTextView);
    }
}
