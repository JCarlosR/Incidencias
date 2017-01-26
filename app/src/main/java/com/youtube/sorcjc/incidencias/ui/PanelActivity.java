package com.youtube.sorcjc.incidencias.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.youtube.sorcjc.incidencias.R;
import com.youtube.sorcjc.incidencias.ui.fragment.AboutFragment;
import com.youtube.sorcjc.incidencias.ui.fragment.DashboardFragment;
import com.youtube.sorcjc.incidencias.ui.fragment.ProjectsChartFragment;
import com.youtube.sorcjc.incidencias.ui.fragment.SendFragment;
import com.youtube.sorcjc.incidencias.ui.fragment.SupportsChartFragment;

public class PanelActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
        getMenuInflater().inflate(R.menu.panel, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here
        int id = item.getItemId();

        Fragment fragment;

        if (id == R.id.action_settings) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragment = new AboutFragment();

            fragmentManager.beginTransaction()
                    .replace(R.id.content_panel, fragment)
                    .commit();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here
        int id = item.getItemId();

        Fragment fragment = null;
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (id == R.id.chart_state) {
            fragment = new DashboardFragment();
        } else if (id == R.id.nav_send) {
            fragment = new SendFragment();
        } else if (id == R.id.chart_projects) {
            fragment = new ProjectsChartFragment();
        } else if (id == R.id.chart_supports) {
            fragment = new SupportsChartFragment();
        }

        if (fragment != null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_panel, fragment)
                    .commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
