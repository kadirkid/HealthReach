package com.abdulahiosoble.healthreach.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.abdulahiosoble.healthreach.R;

public class FindFragment extends Fragment {

    Spinner affiliation, department;
    Button search;
    ArrayAdapter<String> adapter;
    String[] departmentList;
    String[] affiliationList;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    public FindFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_find, container, false);

        department = (Spinner) view.findViewById(R.id.spinner2);
        affiliation = (Spinner) view.findViewById(R.id.spinner3);

        search = (Button) view.findViewById(R.id.search);

        setupSpinner();

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager = getActivity().getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction
                        .replace(R.id.homeframe, new SearchFragment())
                        .commit();
//                startActivity(new Intent(getActivity(), DoctorActivity.class));
            }
        });
        return view;
    }


    public void setupSpinner() {
        departmentList = new String[] {"Select a Department", "------------------","ANESTHESIOLOGY", "Anesthesiology", "Audiology",
                "Bariatric Surgery", "Cardiac Sciences", "Dental", "Dermatology", "Diabesity E.N.T.", "Emergency", "Endocrinology",
                "Gastroenterology", "Gynaecology", "Internal Medicine", "Laboratory", "Laparoscopy", "Neonatology", "Nephrology",
                "Neurology", "Nutrition & Lifestyle Management", "Oncology", "Ophthalmology", "Orthopedics", "Paediatrics",
                "Pharmacy", "Physical Medicine & Rehabilitation", "Physiotherapy", "Psychiatry & Clinical Psychology",
                "Pulmonology & Chest Disease", "Radiology", "Surgery", "Urology", "Rheumatology"};
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, departmentList);
        department.setAdapter(adapter);

        affiliationList = new String[]{"Select a Hospital", "------------------","American Hospital Dubai", "Aster Hospital", "Canadian Specialist Hospital",
                "Mediclinic City Hospital Dubai", "Iranian Hospital", "Latifa Hospital", "Medcare Hospital",
                "Mediclinic Welcare Hospital", "NMC Specialty Hospital", "Prime Hospital Dubai",
                "Rashid Hospital", "Saudi German Hospital-Dubai", "Zulekha Hospital"};

        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, affiliationList);
        affiliation.setAdapter(adapter);

    }

}
