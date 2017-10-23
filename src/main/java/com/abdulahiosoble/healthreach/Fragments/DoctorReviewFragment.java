package com.abdulahiosoble.healthreach.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.abdulahiosoble.healthreach.Adapters.ReviewAdapter;
import com.abdulahiosoble.healthreach.Adapters.ReviewViewHolder;
import com.abdulahiosoble.healthreach.Models.Rating;
import com.abdulahiosoble.healthreach.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hedgehog.ratingbar.RatingBar;

import java.util.UUID;

public class DoctorReviewFragment extends Fragment {
    private static final String TAG = DoctorReviewFragment.class.getSimpleName();
    private RecyclerView ratingRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ReviewAdapter mRatingAdapter;
    private DatabaseReference mDatabaseRef;
    private RatingBar overallRating;
    private DatabaseReference childRef;
    private String doctorID;
    private String mID;
    private String rID;
    private Bundle bundle;

    float overallRate = 0;
    float count = 0;
    Intent intent;
    float rating = 0;
    FloatingActionButton floatingActionButton;
    public DoctorReviewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_doctor_review, container, false);

        ratingRecyclerView = (RecyclerView) view.findViewById(R.id.reviewRecyclerView);
        overallRating = (RatingBar) view.findViewById(R.id.overallRating);
        linearLayoutManager = new LinearLayoutManager(getActivity());

        //doctorID = getArguments().getString("id");

//        Log.i("Doctors ID is: ", doctorID);
        bundle = getArguments();
        rID = bundle.getString("id");

        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        childRef = mDatabaseRef.child("Rating").child(rID);
        Query query = childRef.orderByChild("rating");
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        childRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for(DataSnapshot child: children){
                    Rating r = child.getValue(Rating.class);
                    overallRate += r.getRating();
                    count++;
                }
                if(count != 0){
                    overallRating.setStar(overallRate / count);
                }
                else{
                    overallRating.setStar(overallRate);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        mRatingAdapter = new ReviewAdapter(Rating.class, R.layout.rating_row, ReviewViewHolder.class, childRef, getContext());

        ratingRecyclerView.setLayoutManager(linearLayoutManager);
        ratingRecyclerView.setAdapter(mRatingAdapter);

        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.addReview);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buildDialog();
            }
        });

        Log.i(TAG, "Was Completed");
        return view;
    }

    //Build the Rating Pop Up Dialog
    public void buildDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View newView = getActivity().getLayoutInflater().inflate(R.layout.dialog_rating, null);

        final RatingBar newRating = (RatingBar) newView.findViewById(R.id.newRating);
        final EditText problem = (EditText) newView.findViewById(R.id.dialogProblem);
        final EditText comment = (EditText) newView.findViewById(R.id.dialogComment);

        newRating.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(float RatingCount) {
                rating = RatingCount;
            }
        });

        Button addReviewButton = (Button) newView.findViewById(R.id.buttonAddReview);
        builder.setView(newView);
        AlertDialog dialog = builder.create();
        final AlertDialog finalDialog = dialog;
        addReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rating == 0 && problem.getText().toString().equals("")){
                    Toast.makeText(getContext(), "Please fill in the required fields", Toast.LENGTH_SHORT).show();
                }
                else if(rating < 3 && comment.getText().toString().equals("")){
                    Toast.makeText(getContext(), "A comment needs to be added for ratings less than 3", Toast.LENGTH_SHORT).show();
                }
                else if(problem.getText().toString().equals("")){
                    Toast.makeText(getContext(), "Please fill in the required fields", Toast.LENGTH_SHORT).show();
                }
                else{

                    addReviewToDatabase(problem.getText().toString().trim(), comment.getText().toString().trim(), rating);
                    floatingActionButton.setClickable(false);
                }
                finalDialog.dismiss();
            }
        });


        dialog.show();
    }

    //Add the review to the database
    private void addReviewToDatabase(String problem, String comment, Float rating) {
        Rating rate = new Rating(comment, problem, rating);
        mID = UUID.randomUUID().toString();
        childRef.child(mID).setValue(rate);

    }

}
