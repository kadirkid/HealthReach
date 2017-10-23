package com.abdulahiosoble.healthreach.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abdulahiosoble.healthreach.Adapters.DoctorAdapter;
import com.abdulahiosoble.healthreach.Adapters.DoctorViewHolder;
import com.abdulahiosoble.healthreach.Models.Doctor;
import com.abdulahiosoble.healthreach.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SearchFragment extends Fragment {
    private static final String TAG = SearchFragment.class.getSimpleName();
    private RecyclerView doctorRecyclerview;
    private RecyclerView.LayoutManager LayoutManager;
    private DoctorAdapter mDoctorAdapter;
    private DatabaseReference mDatabaseRef;
    private DatabaseReference childRef;
    ProgressDialog progress;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        Log.i("Search Fragment", "Was Opened");

        getActivity().setTitle("Doctors");
        LayoutManager = new GridLayoutManager(getContext(), 2);
        doctorRecyclerview = (RecyclerView)view.findViewById(R.id.rDoctorSearch);
        progress = new ProgressDialog(getContext());
        progress.setMessage("Loading...");
        progress.setCancelable(false);
        progress.show();

        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        mDatabaseRef.keepSynced(true);
        childRef = mDatabaseRef.child("Doctor");

        childRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                progress.dismiss();
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

        mDoctorAdapter = new DoctorAdapter(Doctor.class, R.layout.doctor_row, DoctorViewHolder.class, childRef, getContext());

        doctorRecyclerview.setLayoutManager(LayoutManager);
        doctorRecyclerview.setAdapter(mDoctorAdapter);

        //new search(getActivity()).execute();
        //set the timer here for 3 second
        //Timer t = new Timer();
        //t.schedule(new waitingtimer(), 3000);

        //progress.dismiss();

        Log.i(TAG, "Was Completed");
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(progress != null && progress.isShowing()){
            progress.dismiss();
        }
    }

    public void setUpDoctors(){
        //Declare the Recycler view Adapter

    }
    /*
private class waitingtimer extends TimerTask {
    @Override
    public void run() {

        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                progress.dismiss();
            }
        });
    }

}


private class search extends AsyncTask<Void, Void, Void> {

    Activity mContex;


    public search(Activity c) {

        this.mContex = c;
    }

    @Override
    protected Void doInBackground(Void... params) {
        setUpDoctors();
        return null;

    }

    @Override
    protected void onPreExecute() {

        super.onPreExecute();

    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        progress.dismiss();
    }
}*/

}
