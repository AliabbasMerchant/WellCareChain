package com.example.max.wellcare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.util.Log;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String MYTAG = "DEBUG_UI";
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        SharedPreferences sp = getApplicationContext().getSharedPreferences("MyPref",0);
//        boolean registered = sp.getBoolean("registered",false);
//        if(!registered)
//        {
//            Intent intent = new Intent(this,Register.class);
//            startActivity(intent);
//        }

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        fm = getFragmentManager();
    }


    private int currentFragmentIndex=-1;
    FragmentManager fm ;
    FragmentTransaction ft ;
    public void changeFragment(int fragmentIndex)
    {
        Fragment fragment;

        boolean isFragmentChanged = false;
        if (currentFragmentIndex!=fragmentIndex)
        {
            switch(fragmentIndex)
            {
                case 0:
                    fragment = new myinfo_fragment();
                    fm = getFragmentManager();
                    ft = fm.beginTransaction();
                    ft.addToBackStack("0");

                    ft.replace(R.id.masterFragment_place,fragment);

                    ft.commit();
                    isFragmentChanged=true;
                    break;

                case 1:
                    fragment = new service_Pathalogy();
                    fm = getFragmentManager();
                    ft = fm.beginTransaction();

                    ft.replace(R.id.masterFragment_place,fragment);
                    ft.addToBackStack("1");
                    ft.commit();
                    isFragmentChanged=true;
                    break;
                case 2:
                    fragment = new service_Chemist();
                    fm = getFragmentManager();
                    ft = fm.beginTransaction();
                    ft.replace(R.id.masterFragment_place,fragment);
                    ft.addToBackStack("2");

                    ft.commit();
                    isFragmentChanged=true;
                    break;
                case 3:
                    fragment = new service_Doctor();
                    fm = getFragmentManager();
                    ft = fm.beginTransaction();
                    ft.addToBackStack("3");

                    ft.replace(R.id.masterFragment_place,fragment);

                    ft.commit();
                    isFragmentChanged=true;
                    break;

                default:

            }
        }

        if(isFragmentChanged)
        {

        }


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_edit) {
            // Handle the camera action
            Log.i(MYTAG,"Edit Menu");
            changeFragment(0);


        } else if (id == R.id.nav_pathalogy) {

            Log.i(MYTAG,"Pathalogy");
            changeFragment(1);

        } else if (id == R.id.nav_chemist) {
            Log.i(MYTAG,"Chemist");
            changeFragment(2);
        } else if (id == R.id.nav_doctor) {
            Log.i(MYTAG,"Doctor");
            changeFragment(3);
        } else if (id == R.id.nav_share) {
            Log.i(MYTAG,"Share");
            changeFragment(4);
        } else if (id == R.id.nav_send) {
            Log.i(MYTAG,"Send");
            changeFragment(5);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
