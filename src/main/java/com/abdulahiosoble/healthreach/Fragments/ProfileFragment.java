package com.abdulahiosoble.healthreach.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.abdulahiosoble.healthreach.Activities.MainActivity;
import com.abdulahiosoble.healthreach.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class ProfileFragment extends Fragment {
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private TextView name, username, email, number;
    private String namestr, usernamestr, emailstr, numberstr;
    private Button logout;
    private DatabaseReference databaseReference, childref;
    //private User user;
    private ProgressDialog dialog;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        name = (TextView) view.findViewById(R.id.name);
        username = (TextView) view.findViewById(R.id.username);
        email = (TextView) view.findViewById(R.id.email);
        number = (TextView) view.findViewById(R.id.phone);
        logout = (Button) view.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(), MainActivity.class));
            }
        });

        //dialog = new ProgressDialog(getContext());
        //dialog.setMessage("Loading....");
        //dialog.setCancelable(false);
        //dialog.show();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        childref = databaseReference.child("User");
        Log.i("UserID", firebaseUser.getUid());
/*
        childref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for(DataSnapshot child: children){
                    if(child.getKey().equals(firebaseUser.getUid())){
                        User user = child.getValue(User.class);
                        Log.i(user.getName(), user.getUsername());
                        /*namestr = "Name: " + user.getName();
                        usernamestr = user.getUsername();
                        emailstr = "Email: " + user.getEmail();
                        numberstr = "Number: " + user.getNumber();

                        name.setText(user.getName());
                        username.setText(user.getUsername());
                        email.setText(user.getEmail());
                        number.setText(user.getNumber());
                        dialog.dismiss();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                dialog.dismiss();
            }
        });
        */

        return view;
    }

}
