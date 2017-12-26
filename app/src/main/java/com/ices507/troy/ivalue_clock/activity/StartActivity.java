package com.ices507.troy.ivalue_clock.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.ices507.troy.ivalue_clock.R;
import com.ices507.troy.ivalue_clock.fragment.BaseFragment;
import com.ices507.troy.ivalue_clock.fragment.CollectFragment;
import com.ices507.troy.ivalue_clock.fragment.ListFragment;
import com.ices507.troy.ivalue_clock.fragment.MainFragment;

public class StartActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static final int PAGE_MAIN = 0x000;
    public static final int PAGE_RECORDS = 0x001;
    public static final int PAGE_INFO = 0x002;
    public static final int PAGE_SETTINGS = 0x003;
    @SuppressWarnings("unchecked")
    private Class<? extends BaseFragment>[] allFragments = new Class[]{MainFragment.class,
            ListFragment.class, CollectFragment.class};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
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
                this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                Log.e("DrawerOpened","Drawer Opened");
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                Log.e("DrawerClosed","Drawer Closed");
            }
        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView =  findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        transact(PAGE_MAIN);
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
        getMenuInflater().inflate(R.menu.start, menu);
        return false;//返回true则显示菜单，否则不显示
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
        switch (id) {
            case R.id.nav_main:
                transact(PAGE_MAIN);
                break;
            case R.id.nav_record:
                transact(PAGE_RECORDS);
                break;
            case R.id.nav_user_info:
                transact(PAGE_INFO);
                break;
            case R.id.nav_settings:
                transact(PAGE_SETTINGS);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void transact(int operation) {
        Log.e("Transaction","xxxx");
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        try {
            BaseFragment fragment = allFragments[operation].newInstance();
            fragment.setFragmentInteractionListener(new BaseFragment.OnFragmentInteractionListener() {
                @Override
                public void onFragmentInteraction(Uri uri) {

                }
            });
            transaction.replace(R.id.id_content,fragment);
            transaction.commit();
        }catch (IllegalAccessException e){
            Log.e("IllegalAccessException",e.toString());
        }
        catch (InstantiationException e){
            Log.e("InstantiationException", e.toString());
        }


    }

}
