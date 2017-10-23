package com.abdulahiosoble.healthreach.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.abdulahiosoble.healthreach.Models.Doctor;
import com.abdulahiosoble.healthreach.R;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class DoctorInformationFragment extends Fragment {
    private static final String TAG = DoctorInformationFragment.class.getSimpleName();
    private TextView departmentInfo, numberInfo, affInfo, specInfo, eduInfo, expInfo, nameInfo;
    private ImageView doctorIm;
    private DatabaseReference mDatabaseRef;
    private DatabaseReference childRef;
    private Doctor doc = new Doctor();
    private String doctorID;
    private Bundle bundle;

    public DoctorInformationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_doctor_information, container, false);

        bundle = getArguments();
        doctorID = bundle.getString("id");

        doctorIm = (ImageView) view.findViewById(R.id.doctorImage);

        departmentInfo = (TextView) view.findViewById(R.id.departmentInformation);
        numberInfo = (TextView) view.findViewById(R.id.numberInformation);
        affInfo = (TextView) view.findViewById(R.id.affiliationInformation);
        specInfo = (TextView) view.findViewById(R.id.specialtyInformation);
        eduInfo = (TextView) view.findViewById(R.id.educationInformation);
        expInfo = (TextView) view.findViewById(R.id.experienceInformation);
        nameInfo = (TextView) view.findViewById(R.id.nameInformation);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        mDatabaseRef.keepSynced(true);
        childRef = mDatabaseRef.child("Doctor");
        childRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                for(DataSnapshot child: children){
                    if(child.getKey().equals(doctorID)) {
                        doc = child.getValue(Doctor.class);

                        Log.i(doc.getName(), doc.getAffiliation());

                        nameInfo.setText(doc.getName());
                        departmentInfo.setText(doc.getDepartment());
                        numberInfo.setText(doc.getNumber().toString());
                        affInfo.setText(doc.getAffiliation());
                        specInfo.setText(doc.getSpecialty());
                        eduInfo.setText(doc.getEducation());
                        expInfo.setText(doc.getExperience());

                        Glide
                                .with(getContext())
                                .load(doc.getImage())
                                .into(doctorIm);
                    }
                    else{
                        Log.i("--------------", child.getKey());
                        Log.i("--------------", doctorID);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i(TAG, databaseError.toString());
            }
        });

        if(doc == null) {
            Log.i("---------------", "doc is null");
        }
        return view;
    }
}
