package com.abdulahiosoble.healthreach.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.abdulahiosoble.healthreach.Utilities.CustomToast;
import com.abdulahiosoble.healthreach.Activities.HomeActivity;
import com.abdulahiosoble.healthreach.R;
import com.abdulahiosoble.healthreach.Utilities.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginFragment extends Fragment implements View.OnClickListener {
    private static View view;

    private static EditText emailid, password;
    private static Button loginButton;
    private static TextView signUp;
    private static CheckBox show_hide_password;
    private static LinearLayout loginLayout;
    private static Animation shakeAnimation;
    private static FragmentManager fragmentManager;
    private static ProgressDialog progressDialog;
    private static FirebaseAuth firebaseAuth;

    public LoginFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login, container, false);
        initViews();
        setListeners();
        return view;
    }

    // Initiate Views
    private void initViews() {
        fragmentManager = getActivity().getSupportFragmentManager();

        emailid = (EditText) view.findViewById(R.id.login_emailid);
        password = (EditText) view.findViewById(R.id.login_password);
        loginButton = (Button) view.findViewById(R.id.loginBtn);
        signUp = (TextView) view.findViewById(R.id.createAccount);
        show_hide_password = (CheckBox) view
                .findViewById(R.id.show_hide_password);
        loginLayout = (LinearLayout) view.findViewById(R.id.login_layout);

        // Load ShakeAnimation
        shakeAnimation = AnimationUtils.loadAnimation(getActivity(),
                R.anim.shake);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(getContext());

        // Setting text selector over textviews
        XmlResourceParser xrp = getResources().getXml(R.drawable.text_selector);
        try {
            ColorStateList csl = ColorStateList.createFromXml(getResources(),
                    xrp);

            show_hide_password.setTextColor(csl);
            signUp.setTextColor(csl);
        } catch (Exception e) {
        }
    }

    // Set Listeners
    private void setListeners() {
        loginButton.setOnClickListener(this);
        signUp.setOnClickListener(this);

        // Set check listener over checkbox for showing and hiding password
        show_hide_password
                .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton button,
                                                 boolean isChecked) {

                        // If it is checkec then show password else hide
                        // password
                        if (isChecked) {

                            show_hide_password.setText(R.string.hide_pwd);// change
                            // checkbox
                            // text

                            password.setInputType(InputType.TYPE_CLASS_TEXT);
                            password.setTransformationMethod(HideReturnsTransformationMethod
                                    .getInstance());// show password
                        } else {
                            show_hide_password.setText(R.string.show_pwd);// change
                            // checkbox
                            // text

                            password.setInputType(InputType.TYPE_CLASS_TEXT
                                    | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            password.setTransformationMethod(PasswordTransformationMethod
                                    .getInstance());// hide password

                        }

                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginBtn:
                if(checkValidation()){
                    login();
                }
                break;

            case R.id.createAccount:

                // Replace signup frgament with animation
                fragmentManager
                        .beginTransaction()
                        .setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                        .replace(R.id.mainframe, new SignUpFragment(),
                                Utils.SignUp_Fragment).commit();
                break;
        }

    }

    // Check Validation before login
    private boolean checkValidation() {
        // Get email id and password
        String getEmailId = emailid.getText().toString();
        String getPassword = password.getText().toString();

        // Check patter for email id
        Pattern p = Pattern.compile(Utils.regEx);

        Matcher m = p.matcher(getEmailId);

        // Check for both field is empty or not
        if (getEmailId.equals("") || getEmailId.length() == 0
                || getPassword.equals("") || getPassword.length() == 0) {
            loginLayout.startAnimation(shakeAnimation);
            new CustomToast().Show_Toast(getActivity(), view,
                    "Enter both credentials.");
            return false;
        }
        // Check if email id is valid or not
        else if (!m.find()){
            new CustomToast().Show_Toast(getActivity(), view,
                    "Your Email Id is Invalid.");
            return false;
        }
        //Go for login
        else
            return true;

    }

    private void login(){
        // Get email id and password
        String getEmailId = emailid.getText().toString();
        String getPassword = password.getText().toString();

        progressDialog.setMessage("Logging In...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(getEmailId, getPassword)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();

                        if(task.isSuccessful()){
                            startActivity(new Intent(getActivity(), HomeActivity.class));
                        }
                        else{
                            Toast.makeText(getContext(), "Logging In Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }//End of login

}//End of fragment

/*
    EditText password, emailet;

    FrameLayout loginFragmentFrame;
    Button bLogin, bSignup;
    String email, pass;
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;


    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_login, container, false);

        password = (EditText) v.findViewById(R.id.etPassword);
        password.setOnKeyListener(this);

        loginFragmentFrame = (FrameLayout) v.findViewById(R.id.login_fragment);
        loginFragmentFrame.setOnClickListener(this);

        bLogin = (Button) v.findViewById(R.id.bLogin);
        bLogin.setOnClickListener(this);

        bSignup = (Button) v.findViewById(R.id.bSignup);
        bSignup.setOnClickListener(this);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(getContext());

        //ParseAnalytics.trackAppOpenedInBackground(getActivity().getIntent());

        return v;
    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        //Put down the keyboard and log in when enter is pressed on password
        if(i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN){
            onClick(view);
        }
        return false;
    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.login_fragment){

            //put down the keyboard when the background is pressed
            InputMethodManager IM = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
            IM.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);

        }

        else if(view.getId() == R.id.bLogin){
            login();
        }

        else if(view.getId() == R.id.bSignup){
            changeFragment(new SignUpFragment());
        }
    }

    public void login(){
        emailet = (EditText) getActivity().findViewById(R.id.etEmail);
        password = (EditText) getActivity().findViewById(R.id.etPassword);

        email = emailet.getText().toString().trim();
        pass = password.getText().toString().trim();

        progressDialog.setMessage("Logging In...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();

                        if(task.isSuccessful()){
                            startActivity(new Intent(getActivity(), HomeActivity.class));
                        }
                        else{
                            Toast.makeText(getContext(), "Logging In Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }


    public void changeFragment(Fragment f){
        getFragmentManager().beginTransaction().replace(R.id.mainframe, f).commit();
    }*/

/*
//Check if user is in the database and log him in
            if(username.getText().toString().matches("") || password.getText().toString().matches("")){
                    Toast.makeText(getActivity(), "A Username and Password is required!", Toast.LENGTH_SHORT).show();
                    } else{
                    ParseUser.logInInBackground(username.getText().toString(), password.getText().toString(), new LogInCallback() {
@Override
public void done(ParseUser user, ParseException e) {
        if (user != null) {
        Log.i("Login", "Successful");
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        startActivity(intent);
        } else {
        Toast.makeText(getActivity(), "Login Failed:" + e.toString(), Toast.LENGTH_SHORT).show();
        }
        }
        });
        }
*/
