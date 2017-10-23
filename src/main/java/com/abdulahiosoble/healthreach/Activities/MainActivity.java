package com.abdulahiosoble.healthreach.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.abdulahiosoble.healthreach.Fragments.LoginFragment;
import com.abdulahiosoble.healthreach.R;
import com.abdulahiosoble.healthreach.Utilities.Utils;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {
    private static FragmentManager fragmentManager;
    private FirebaseAuth firebaseAuth;
    private ActionBar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        firebaseAuth =  FirebaseAuth.getInstance();
        toolbar = getSupportActionBar();
        toolbar.hide();
        // If no user is signed in is null then replace login fragment
        if (firebaseAuth.getCurrentUser() == null) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.mainframe, new LoginFragment(),
                            Utils.Login_Fragment).commit();
        }
        else{
            overridePendingTransition(R.anim.slide_up, android.R.anim.slide_in_left);
            startActivity(new Intent(MainActivity.this, HomeActivity.class));
        }
        if (savedInstanceState == null) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.mainframe, new LoginFragment(),
                            Utils.Login_Fragment).commit();
        }

        // On close icon click finish activity
        /*findViewById(R.id.close_activity).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        finish();
                    }
                });
*/
    }

    // Replace Login Fragment with animation
    public void replaceLoginFragment() {
        fragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.left_enter, R.anim.right_out)
                .replace(R.id.mainframe, new LoginFragment(),
                        Utils.Login_Fragment).commit();
    }

    @Override
    public void onBackPressed() {

        // Find the tag of signup and forgot password fragment
        Fragment SignUp_Fragment = fragmentManager
                .findFragmentByTag(Utils.SignUp_Fragment);
        Fragment ForgotPassword_Fragment = fragmentManager
                .findFragmentByTag(Utils.ForgotPassword_Fragment);

        // Check if both are null or not
        // If both are not null then replace login fragment else do backpressed
        // taskthunqcl

        if (SignUp_Fragment != null)
            replaceLoginFragment();
        else{
            finish();
            super.onBackPressed();
            System.exit(1);
        }

    }
}

/*


Toolbar toolbar;
    TabLayout tab;
    ViewPager mViewPager;
    ViewPagerAdapter mViewAdapter;

       toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
                <include
        android:layout_height="wrap_content"
        android:layout_width="match_parent"
        layout="@layout/toolbar_layout" />


         tab = (TabLayout) findViewById(R.id.tabLayout);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mViewAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        mViewAdapter.addFragments(new LoginFragment(), "Login");
        mViewAdapter.addFragments(new SignUpFragment(), "Sign Up");

        mViewPager.setAdapter(mViewAdapter);
        tab.setupWithViewPager(mViewPager);





        <android.support.design.widget.AppBarLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content">



        <android.support.design.widget.TabLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:id="@+id/tabLayout"
            app:tabMode="fixed"
            app:tabGravity="fill">


        </android.support.design.widget.TabLayout>
        <FrameLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:id="@+id/mainframe">

        </FrameLayout>


    </android.support.design.widget.AppBarLayout>

    <android.support.v4.view.ViewPager
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:id="@+id/viewPager">

    </android.support.v4.view.ViewPager>


 */