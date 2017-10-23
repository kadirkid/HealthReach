package com.abdulahiosoble.healthreach.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;

import com.abdulahiosoble.healthreach.Activities.DoctorActivity;
import com.abdulahiosoble.healthreach.Models.Doctor;
import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;


public class DoctorAdapter extends FirebaseRecyclerAdapter<Doctor, DoctorViewHolder> {
    private static final String TAG = DoctorAdapter.class.getSimpleName();
    private Context context;
    FragmentTransaction fragmentTransaction;
//    private ArrayList<Doctor> doctorsA;

    public DoctorAdapter(Class<Doctor> modelClass, int modelLayout, Class<DoctorViewHolder> viewHolderClass, DatabaseReference ref, Context context) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        this.context = context;
        Log.i("Contructor", "Was Called");
    }


    @Override
    protected void populateViewHolder(final DoctorViewHolder viewHolder, final Doctor model, int position) {
        viewHolder.dName.setText(model.getName());
        viewHolder.dDepartment.setText(model.getDepartment());
        viewHolder.dAffiliation.setText(model.getAffiliation());
        Log.i("Populate View Holder", "Was Called");
        Glide
                .with(context)
                .load(model.getImage())
                .into(viewHolder.doctorImage);

//        doctorsA.add(model);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DoctorActivity.class);
                intent.putExtra("id", model.getId());
                intent.putExtra("name", model.getName());
                context.startActivity(intent);
            }
        });
    }

}
