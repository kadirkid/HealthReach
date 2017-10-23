package com.abdulahiosoble.healthreach.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toolbar;
import android.util.Log;

import com.abdulahiosoble.healthreach.Adapters.ViewPagerAdapter;
import com.abdulahiosoble.healthreach.Fragments.DoctorInformationFragment;
import com.abdulahiosoble.healthreach.Fragments.DoctorReviewFragment;
import com.abdulahiosoble.healthreach.R;

public class DoctorActivity extends AppCompatActivity {
    Toolbar toolbar;
    TabLayout tab;
    ViewPager mViewPager;
    ViewPagerAdapter mViewAdapter;
    String doctorName = "", doctorID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);


        toolbar = (Toolbar) findViewById(R.id.toolBar);
        //Get the Doctors ID
        Intent intent = getIntent();
        doctorID = intent.getStringExtra("id");
        doctorName = (String) intent.getStringExtra("name");
        Log.i("--------------", doctorName);

        
        //setSupportActionBar(toolbar);
        setActionBar(toolbar);
        //Put the Doctor's ID in a bundle
        Bundle bundle = new Bundle();
        bundle.putString("id", doctorID);

        //Send the Doctors ID to the fragments
        DoctorInformationFragment doctorInformationFragment = new DoctorInformationFragment();
        DoctorReviewFragment doctorReviewFragment = new DoctorReviewFragment();
        doctorInformationFragment.setArguments(bundle);
        doctorReviewFragment.setArguments(bundle);

        Log.i("--------------", "----------------");
        Log.i("ID", doctorID);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);

        tab = (TabLayout) findViewById(R.id.tabLayout);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mViewAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        mViewAdapter.addFragment(doctorInformationFragment, "Information");
        mViewAdapter.addFragment(doctorReviewFragment, "Reviews");

        mViewPager.setAdapter(mViewAdapter);
        tab.setupWithViewPager(mViewPager);
    }
}
