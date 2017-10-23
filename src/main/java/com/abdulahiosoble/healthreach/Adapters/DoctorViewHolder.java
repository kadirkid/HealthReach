package com.abdulahiosoble.healthreach.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.abdulahiosoble.healthreach.R;


public class DoctorViewHolder extends RecyclerView.ViewHolder {
    private static final String TAG = DoctorViewHolder.class.getSimpleName();
    ImageView doctorImage;
    TextView dName, dDepartment, dAffiliation;

    public DoctorViewHolder(View itemView) {
        super(itemView);

        doctorImage = (ImageView) itemView.findViewById(R.id.doctorImage);
        dName = (TextView) itemView.findViewById(R.id.doctorName);
        dDepartment = (TextView) itemView.findViewById(R.id.doctorDepartment);
        dAffiliation = (TextView) itemView.findViewById(R.id.doctorAffiliation);
    }
}
