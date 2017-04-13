package com.android.korama;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
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
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.korama.model.load;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Chaimaa on 06/03/2017.
 */

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    ProgressBar pb;
    int rateOrNot;
    InterstitialAd mInterstitialAd;
    Adapter adapter = new Adapter(getSupportFragmentManager());
    /*private BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("MessageService", " --------- onReceive ---------");
        }
    };*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rateOrNot= 0;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //----------Notification----------


        FirebaseMessaging.getInstance().subscribeToTopic("new_article");

        //registerReceiver(myReceiver, new IntentFilter(MessageService.INTENT_FILTER));

        //-----------End Notification-----

        //---------banner ad--------------
        MobileAds.initialize(getApplicationContext(), getResources().getString(R.string.admob_id));
        AdView mAdView = (AdView) findViewById(R.id.banner_AdView);

        //AdRequest adRequest = new AdRequest.Builder().build();
        AdRequest adRequest = new AdRequest.Builder()
                //.addTestDevice(AdRequest.DEVICE_ID_EMULATOR) // for test
                // Check the LogCat to get your test device ID
                .addTestDevice("1E0E3A3F30546176A2722281C7620F4A")
                .build();

        mAdView.loadAd(adRequest);


        //----------Interstitial ad---------
        //showInterstitial();


        pb= (ProgressBar) findViewById(R.id.pb);
        pb.setVisibility(View.INVISIBLE);

        TextView tv = new TextView(getApplicationContext());
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        tv.setLayoutParams(lp);
        tv.setText("الكرة المغربية");
        tv.setTextSize(20);
        tv.setTextColor(Color.parseColor("#FFFFFF"));
        Typeface tf = Typeface.createFromAsset(getAssets(),"fonts/Rawy-Regular.otf");
        tv.setTypeface(tf);
        /*
        ImageView iv = new ImageView(getApplicationContext());
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        iv.setLayoutParams(lp);
        iv.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_logo));

        */
        final ActionBar ab = getSupportActionBar();

        ab.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        ab.setCustomView(tv);


        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }
        navigationView.setItemIconTintList(null);
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

        /*SpannableString s = new SpannableString("الكرة المغربية"); //change app title style (still not working)
        s.setSpan(new TypefaceSpan(this,"BElham.ttf"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Update the action bar title with the TypefaceSpan instance
        android.app.ActionBar actionBar = getActionBar();

        actionBar.setTitle(s);*/



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
                    case R.id.about:
                        startActivity(new Intent(MainActivity.this,About.class));
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
        // getMenuInflater().inflate(R.menu.sample_actions, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
      /*  switch (AppCompatDelegate.getDefaultNightMode()) {
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
        }*/
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
        adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new CheeseListFragment().setCategorie(3,pb), getResources().getString(R.string.category_1));
        adapter.addFragment(new CheeseListFragment().setCategorie(7,pb),  getResources().getString(R.string.category_5));
        adapter.addFragment(new CheeseListFragment().setCategorie(10,pb),  getResources().getString(R.string.category_9));
        adapter.addFragment(new CheeseListFragment().setCategorie(4,pb), getResources().getString(R.string.category_2));
        adapter.addFragment(new CheeseListFragment().setCategorie(5,pb),  getResources().getString(R.string.category_3));
        adapter.addFragment(new CheeseListFragment().setCategorie(6,pb),  getResources().getString(R.string.category_4));
        //adapter.addFragment(new CheeseListFragment().setCategorie(33,pb),  getResources().getString(R.string.category_6));
        adapter.addFragment(new CheeseListFragment().setCategorie(8,pb),  getResources().getString(R.string.category_7));
        adapter.addFragment(new CheeseListFragment().setCategorie(9,pb),  getResources().getString(R.string.category_8));
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
    public void onBackPressed(){
        rateOrNot++;
        if(rateOrNot%5 == 0)
            showRateAppDialog();
        else
            moveTaskToBack(true);

    }

    public void showRateAppDialog(){

        new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.rateUp_title))
                .setMessage(getResources().getString(R.string.rateUp))
                .setPositiveButton(getResources().getString(R.string.rateItNow), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        rateApp();
                    }
                })
                /*.setNegativeButton(getResources().getString(R.string.noRate), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                        rateOrNot= false;
                    }
                })*/
                .setNeutralButton(getResources().getString(R.string.rateLater), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                        moveTaskToBack(true);
                    }
                })
                //.setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }


    public void rateApp()
    {
        try
        {
            Intent rateIntent = rateIntentForUrl("market://details");
            startActivity(rateIntent);
        }
        catch (ActivityNotFoundException e)
        {
            Intent rateIntent = rateIntentForUrl("https://play.google.com/store/apps/details");
            startActivity(rateIntent);
        }
    }

    private Intent rateIntentForUrl(String url)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("%s?id=%s", url, getPackageName())));
        int flags = Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_MULTIPLE_TASK;
        if (Build.VERSION.SDK_INT >= 21)
        {
            flags |= Intent.FLAG_ACTIVITY_NEW_DOCUMENT;
        }
        else
        {
            //noinspection deprecation
            flags |= Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET;
        }
        intent.addFlags(flags);
        return intent;
    }
    private void showInterstitial() {
        //--------Interstitial ad------------
        mInterstitialAd = new InterstitialAd(this);
        // set the ad unit ID
        mInterstitialAd.setAdUnitId(getString(R.string.ad_id_interstitial));
        AdRequest adRequest = new AdRequest.Builder().build();

        // Load ads into Interstitial Ads
        mInterstitialAd.loadAd(adRequest);

        mInterstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded() {
                mInterstitialAd.show();
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

    @Override
    protected void onPostResume() {
        super.onPostResume();

        Observable<load> vals = Observable.just(new load(0,3),new load(0,4),new load(0,5),new load(0,6),new load(0,7),new load(0,8),new load(0,9),new load(0,1),new load(0,10));


        vals.flatMap(new Func1<load, Observable<?>>() {
            @Override
            public Observable<?> call(load getDataTask) {
                return Observable.just(getDataTask).subscribeOn(Schedulers.newThread())
                        .map(new Func1<load, Object>() {
                            @Override
                            public Object call(load getDataTask) {
                                return getDataTask.load();
                            }
                        });
            }
        }).subscribe(new Subscriber<Object>() {
            @Override
            public void onCompleted() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();

                    }
                });

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object o) {
                System.out.println("Received " + (o) +
                        " on thread " + Thread.currentThread().getName());
            }});

    }


    /*
    @Override
    protected void onDestroy() {
        unregisterReceiver(myReceiver);
        super.onDestroy();
    }*/
}
