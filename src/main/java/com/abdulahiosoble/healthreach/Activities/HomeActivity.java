package com.abdulahiosoble.healthreach.Activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.abdulahiosoble.healthreach.Fragments.AboutFragment;
import com.abdulahiosoble.healthreach.Fragments.FindFragment;
import com.abdulahiosoble.healthreach.Fragments.ProfileFragment;
import com.abdulahiosoble.healthreach.R;
import com.abdulahiosoble.healthreach.Utilities.Utils;

public class HomeActivity extends AppCompatActivity {
    private FragmentManager fragmentManager;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private NavigationView nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        drawer = (DrawerLayout) findViewById(R.id.drawer);
        toggle = new ActionBarDrawerToggle(this, drawer, R.string.open, R.string.close);
        nav = (NavigationView) findViewById(R.id.navigationView);
        fragmentManager = getSupportFragmentManager();

        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navSearch:
                        fragmentManager
                                .beginTransaction()
                                .replace(R.id.homeframe, new FindFragment(),
                                        Utils.Find_Fragment).commit();
                        break;
                    case R.id.navAbout:
                        fragmentManager
                                .beginTransaction()
                                .replace(R.id.homeframe, new AboutFragment(),
                                        Utils.About_Fragment).commit();
                        break;

                    case R.id.navProfile:
                        fragmentManager
                                .beginTransaction()
                                .replace(R.id.homeframe, new ProfileFragment(),
                                        Utils.Profile_Fragment).commit();
                        break;
                }
                if(drawer.isDrawerOpen(GravityCompat.START)){
                    drawer.closeDrawer(GravityCompat.START);
                }
                return false;
            }
        });

        drawer.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Log.i("Home Activity", "Was Opened");
        // If savedinstnacestate is null then replace login fragment
        //if (savedInstanceState == null) {
        fragmentManager
                .beginTransaction()
                .replace(R.id.homeframe, new FindFragment(),
                        Utils.Search_Fragment).commit();
        //}


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(toggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }
}
