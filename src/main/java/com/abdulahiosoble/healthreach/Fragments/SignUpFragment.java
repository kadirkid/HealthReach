package com.abdulahiosoble.healthreach.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.abdulahiosoble.healthreach.Utilities.CustomToast;
import com.abdulahiosoble.healthreach.Activities.HomeActivity;
import com.abdulahiosoble.healthreach.Activities.MainActivity;
import com.abdulahiosoble.healthreach.Models.User;
import com.abdulahiosoble.healthreach.R;
import com.abdulahiosoble.healthreach.Utilities.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpFragment extends Fragment implements View.OnClickListener {
    private static View view;
    private static EditText fullName, emailId, mobileNumber, username,
            password, confirmPassword;
    private static TextView login;
    private static Button signUpButton;
    private static CheckBox terms_conditions;
    private static ProgressDialog progressDialog;
    private static FirebaseAuth firebaseAuth;
    private static DatabaseReference databaseReference;
    private static User user;

    public SignUpFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        initViews();
        setListeners();
        return view;
    }

    // Initialize all views
    private void initViews() {
        fullName = (EditText) view.findViewById(R.id.fullName);
        emailId = (EditText) view.findViewById(R.id.userEmailId);
        mobileNumber = (EditText) view.findViewById(R.id.mobileNumber);
        username = (EditText) view.findViewById(R.id.username);
        password = (EditText) view.findViewById(R.id.password);
        confirmPassword = (EditText) view.findViewById(R.id.confirmPassword);
        signUpButton = (Button) view.findViewById(R.id.signUpBtn);
        login = (TextView) view.findViewById(R.id.already_user);
        terms_conditions = (CheckBox) view.findViewById(R.id.terms_conditions);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(getContext());
        databaseReference = FirebaseDatabase.getInstance().getReference();

        // Setting text selector over textviews
        XmlResourceParser xrp = getResources().getXml(R.drawable.text_selector);
        try {
            ColorStateList csl = ColorStateList.createFromXml(getResources(),
                    xrp);

            login.setTextColor(csl);
            terms_conditions.setTextColor(csl);
        } catch (Exception e) {
        }
    }

    // Set Listeners
    private void setListeners() {
        signUpButton.setOnClickListener(this);
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signUpBtn:

                // Call checkValidation method
                if(checkValidation()){
                    sign();
                }
                break;

            case R.id.already_user:

                // Replace login fragment
                new MainActivity().replaceLoginFragment();
                break;
        }

    }

    // Check Validation Method
    private boolean checkValidation() {

        // Get all edittext texts
        String getFullName = fullName.getText().toString();
        String getEmailId = emailId.getText().toString();
        String getMobileNumber = mobileNumber.getText().toString();
        String getUsername = username.getText().toString();
        String getPassword = password.getText().toString();
        String getConfirmPassword = confirmPassword.getText().toString();

        // Pattern match for email id
        Pattern p = Pattern.compile(Utils.regEx);
        Matcher m = p.matcher(getEmailId);

        // Check if all strings are null or not
        if (getFullName.equals("") || getFullName.length() == 0
                || getEmailId.equals("") || getEmailId.length() == 0
                || getMobileNumber.equals("") || getMobileNumber.length() == 0
                || getUsername.equals("") || getUsername.length() == 0
                || getPassword.equals("") || getPassword.length() == 0
                || getConfirmPassword.equals("")
                || getConfirmPassword.length() == 0){
            new CustomToast().Show_Toast(getActivity(), view,
                    "All fields are required.");
            return false;
        }



            // Check if email id valid or not
        else if (!m.find()){
            new CustomToast().Show_Toast(getActivity(), view,
                    "Your Email Id is Invalid.");
            return false;
        }


            // Check if both password should be equal
        else if (!getConfirmPassword.equals(getPassword)){
            new CustomToast().Show_Toast(getActivity(), view,
                    "Both password doesn't match.");
            return false;
        }


            // Make sure user should check Terms and Conditions checkbox
        else if (!terms_conditions.isChecked()){
            new CustomToast().Show_Toast(getActivity(), view,
                    "Please select Terms and Conditions.");
            return false;
        }

        else    //Everything is valid
            return true;

    }

    private void sign(){    //Begin the actual signup process;
        // Get all edittext texts
        final String getFullName = fullName.getText().toString();
        final String getEmailId = emailId.getText().toString();
        final String getMobileNumber = mobileNumber.getText().toString();
        final String getUsername = username.getText().toString();
        final String getPassword = password.getText().toString();
        final String getConfirmPassword = confirmPassword.getText().toString();

        user = new User(getUsername, getPassword, getEmailId, getFullName, getMobileNumber);

        progressDialog.setMessage("Registering User...");
        progressDialog.show();

        //Register the user
        firebaseAuth.createUserWithEmailAndPassword(getEmailId, getPassword)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();

                        if(task.isSuccessful()){
                            progressDialog.setMessage("Signing In...");

                            //Sign the user in
                            firebaseAuth.signInWithEmailAndPassword(getEmailId, getPassword)
                                    .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            progressDialog.dismiss();
                                            setUpInformation(); //Set up all the users information;
                                            //Add Users Information to the Database
                                            FirebaseUser fUser = firebaseAuth.getCurrentUser();
                                            databaseReference.child("User").child(fUser.getUid()).setValue(user);

                                            if(task.isSuccessful()){
                                                startActivity(new Intent(getActivity(), HomeActivity.class));
                                            }
                                            else{
                                                Toast.makeText(getContext(), "Signing In Failed", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                        else{
                            Toast.makeText(getContext(), "Sign Up Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}



/*EditText usernameS, passwordS, emailSU;
    Button bSignup;
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
    String username, email, pass;
    DatabaseReference databaseReference;
    User user;
    public SignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_sign_up, container, false);
        bSignup = (Button) v.findViewById(R.id.bSignup);
        bSignup.setOnClickListener(this);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(getContext());
        databaseReference = FirebaseDatabase.getInstance().getReference();
        return v;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.bSignup) {
            register();
        }

        else if(view.getId() == R.id.bLogin){
            changeFragment(new LoginFragment());
        }

        else if(view.getId() == R.id.RSignUp){
            //put down the keyboard when the background is pressed
            InputMethodManager IM = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
            IM.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }
    }




    public void register(){
        usernameS = (EditText) getActivity().findViewById(R.id.etUsernameSU);
        passwordS = (EditText) getActivity().findViewById(R.id.etPasswordSU);
        emailSU = (EditText) getActivity().findViewById(R.id.etEmailSU);

        username = usernameS.getText().toString().trim();
        pass = passwordS.getText().toString().trim();
        email = emailSU.getText().toString().trim();

        if(!verify(email, username, pass)){
            return;
        }
        else{
            //user = new User(username,pass,email);
            progressDialog.setMessage("Registering User...");
            progressDialog.show();

            firebaseAuth.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();

                            if(task.isSuccessful()){
                                FirebaseUser fUser = firebaseAuth.getCurrentUser();
                                databaseReference.child(fUser.getUid()).setValue(user);
                                progressDialog.setMessage("Signing In...");

                                firebaseAuth.signInWithEmailAndPassword(email, pass)
                                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                progressDialog.dismiss();

                                                if(task.isSuccessful()){
                                                    startActivity(new Intent(getActivity(), HomeActivity.class));
                                                }
                                                else{
                                                    Toast.makeText(getContext(), "Signing In Failed", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            }
                            else{
                                Toast.makeText(getContext(), "Sign Up Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }



    }

    public boolean verify(String user, String pass, String email){
        if(TextUtils.isEmpty(user)){
            Toast.makeText(getContext(), "Enter a valid Username", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(TextUtils.isEmpty(pass) || pass.length() < 4){
            Toast.makeText(getContext(), "Enter a valid Password", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(!email.contains("@") || TextUtils.isEmpty(email)){
            Toast.makeText(getContext(), "Enter a valid Email Address", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    public void changeFragment(Fragment f){
        getFragmentManager().beginTransaction().replace(R.id.mainframe, f).commit();
    }
    */










/*
            usernameS = (EditText) getActivity().findViewById(R.id.etUsernameSU);
            passwordS = (EditText) getActivity().findViewById(R.id.etPasswordSU);
            email = (EditText) getActivity().findViewById(R.id.etEmail);

            if (passwordS.getText().toString().matches("") || usernameS.getText().toString().matches("") || email.getText().toString().matches("")) {
                Toast.makeText(getActivity(), "Please fill in the required fields", Toast.LENGTH_SHORT).show();
            } else {
                user = new ParseUser();
                user.setUsername(usernameS.getText().toString());
                user.setPassword(passwordS.getText().toString());
                user.setEmail(email.getText().toString());

                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            ParseUser.logInInBackground(usernameS.getText().toString(), passwordS.getText().toString(), new LogInCallback() {
                                @Override
                                public void done(ParseUser user, ParseException e) {
                                    if (user != null) {
                                        Log.i("Sign Up and Login", "Successful");
                                        Toast.makeText(getActivity(), "Sign Up and Login Successful", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getActivity(), HomeActivity.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(getActivity(), "Login Failed:" + e.toString(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }*/
