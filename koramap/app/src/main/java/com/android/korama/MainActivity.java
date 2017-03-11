package com.android.korama;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chaimaa on 06/03/2017.
 */

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pb= (ProgressBar) findViewById(R.id.pb);
        pb.setVisibility(View.INVISIBLE);

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        if (viewPager != null) {
            setupViewPager(viewPager);
        }

       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.cat1:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.cat2:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.cat3:
                        viewPager.setCurrentItem(2);
                        break;
                    case R.id.cat4:
                        viewPager.setCurrentItem(3);
                        break;
                    case R.id.cat5:
                        viewPager.setCurrentItem(4);
                        break;
                    /*case R.id.cat6:
                        viewPager.setCurrentItem(5);
                        break;*/
                    case R.id.cat7:
                        viewPager.setCurrentItem(5);
                        break;
                    case R.id.cat8:
                        viewPager.setCurrentItem(6);
                        break;
                    case R.id.cat9:
                        viewPager.setCurrentItem(7);
                        break;
                    case R.id.cat10:
                        viewPager.setCurrentItem(9);
                        break;
                }
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

    }

    //-----start : if u delete this drop-down menu will not work ??? --to check later---
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sample_actions, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        switch (AppCompatDelegate.getDefaultNightMode()) {
            case AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM:
                menu.findItem(R.id.menu_night_mode_system).setChecked(true);
                break;
            case AppCompatDelegate.MODE_NIGHT_AUTO:
                menu.findItem(R.id.menu_night_mode_auto).setChecked(true);
                break;
            case AppCompatDelegate.MODE_NIGHT_YES:
                menu.findItem(R.id.menu_night_mode_night).setChecked(true);
                break;
            case AppCompatDelegate.MODE_NIGHT_NO:
                menu.findItem(R.id.menu_night_mode_day).setChecked(true);
                break;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.menu_night_mode_system:
                setNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                break;
            case R.id.menu_night_mode_day:
                setNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case R.id.menu_night_mode_night:
                setNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            case R.id.menu_night_mode_auto:
                setNightMode(AppCompatDelegate.MODE_NIGHT_AUTO);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private void setNightMode(@AppCompatDelegate.NightMode int nightMode) {
        AppCompatDelegate.setDefaultNightMode(nightMode);

        if (Build.VERSION.SDK_INT >= 11) {
            recreate();
        }
    }

    //--------------------------End------------------------------

    private void setupViewPager(ViewPager viewPager) {
        viewPager.setVerticalScrollBarEnabled(true);
        viewPager.setHorizontalScrollBarEnabled(true);
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new CheeseListFragment().setCategorie(3,pb), getResources().getString(R.string.category_1));
        adapter.addFragment(new CheeseListFragment().setCategorie(4,pb), getResources().getString(R.string.category_2));
        adapter.addFragment(new CheeseListFragment().setCategorie(5,pb),  getResources().getString(R.string.category_3));
        adapter.addFragment(new CheeseListFragment().setCategorie(6,pb),  getResources().getString(R.string.category_4));
        adapter.addFragment(new CheeseListFragment().setCategorie(7,pb),  getResources().getString(R.string.category_5));
        //adapter.addFragment(new CheeseListFragment().setCategorie(33,pb),  getResources().getString(R.string.category_6));
        adapter.addFragment(new CheeseListFragment().setCategorie(8,pb),  getResources().getString(R.string.category_7));
        adapter.addFragment(new CheeseListFragment().setCategorie(9,pb),  getResources().getString(R.string.category_8));
        adapter.addFragment(new CheeseListFragment().setCategorie(10,pb),  getResources().getString(R.string.category_9));
        adapter.addFragment(new CheeseListFragment().setCategorie(1,pb),  getResources().getString(R.string.category_10));
        viewPager.setAdapter(adapter);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);

        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }
}
