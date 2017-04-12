/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.korama;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.korama.model.Post;
import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.NativeExpressAdView;


public class CheeseDetailActivity extends AppCompatActivity {

    public static final String EXTRA_NAME = "cheese_name";
    Post post;
    WebView webView;
    WebView webVideo;
    Activity myActivity;
    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        myActivity = this;
        //---------banner ad--------------
        MobileAds.initialize(getApplicationContext(), getResources().getString(R.string.admob_id));//just to speed up ad fetch
        AdView mAdView = (AdView) findViewById(R.id.banner_AdView);

        //AdRequest adRequest = new AdRequest.Builder().build();
        AdRequest adRequest = new AdRequest.Builder()
                //.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)// for test
                // Check the LogCat to get your test device ID
                .addTestDevice("1E0E3A3F30546176A2722281C7620F4A")
                .build();
        mAdView.loadAd(adRequest);


        //---------------Native ad-----------------

        NativeExpressAdView adView = (NativeExpressAdView)findViewById(R.id.native_AdView);

        AdRequest request = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)// for test
                // Check the LogCat to get your test device ID
                .addTestDevice("1E0E3A3F30546176A2722281C7620F4A")
                .build();
        adView.loadAd(request);
        //----------------------------------------

        Intent intent = getIntent();
        //final String cheeseName = intent.getStringExtra(EXTRA_NAME);
        post = (Post) getIntent().getSerializableExtra("Post");
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);


        TextView title = (TextView) findViewById(R.id.title_post);
        //TextView content = (TextView) findViewById(R.id.content_post);

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Brushez.ttf");
        title.setTypeface(font);
        //content.setTypeface(font);
        font = Typeface.createFromAsset(getAssets(), "fonts/Rawy-Regular.otf");
        collapsingToolbar.setCollapsedTitleTypeface(font);
        collapsingToolbar.setExpandedTitleTypeface(font);

        toolbar.setPadding(toolbar.getPaddingLeft(),toolbar.getPaddingTop(),toolbar.getPaddingRight()+20,toolbar.getTitleMarginBottom());
        if(post.getTitle() != null) {
            title.setText(post.getTitle());
        }

         webView = (WebView) findViewById(R.id.webContent);
         webVideo = (WebView) findViewById(R.id.webVideo);
        //w.setPluginState(WebSettings.PluginState.ON);
        //w.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        findViewById(R.id.share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share(post);
            }
        });


       // webView.loadDataWithBaseURL(null, post.getContent(), "text/html", "UTF-8", null);
        /*CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(post.getTitle());
        */



        /*if (Build.VERSION.SDK_INT >= 14) { //this is for zoom not fon size :(
            webView.getSettings().setTextZoom((int)(webView.getSettings().getTextZoom() * 1.1));
        }
        else{
            webView.getSettings().setTextSize(WebSettings.TextSize.LARGER);
        }*/
        //webView.getSettings().setDefaultFontSize(webView.getSettings().getDefaultFontSize()+10);

        //webView.setWebChromeClient(new WebChromeClient());
        //webView.setWebViewClient(new WebViewClient());

        //webView.getSettings().setUseWideViewPort(false);
        //webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        //webView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
        //webView.setInitialScale(1);
        //webView.setWebChromeClient(new WebChromeClient());
        //webView.getSettings().setAllowFileAccess(true);
        //webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        //webView.getSettings().setPluginState(WebSettings.PluginState.ON_DEMAND);


        //webView.getSettings().setLoadWithOverviewMode(true);
        //webView.getSettings().setUseWideViewPort(true);
        //DisplayMetrics displaymetrics = new DisplayMetrics();
        //getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        //int height = displaymetrics.heightPixels;
        //int width = displaymetrics.widthPixels;


        //webView.loadDataWithBaseURL("http://vimeo.com",  "<html dir=\"rtl\" lang=\"\"><body>" + post.getContent() + "</body></html>", "text/html", "UTF-8", null);
        if(post.getContent() != null) {
            Log.d("COL","post content : "+post.getContent());
            displayHtmlText(post.getContent(), "", webView);
        }
        Log.d("RQ","before post.getIframe() ");
        if(post.getIframe() != null){
            Log.d("RQ","post.getIframe() "+post.getIframe());
            //webVideo.setVisibility(View.VISIBLE);
            displayIframe(post.getIframe(),webVideo);


        }
        if(post.getImage_url() != null)
            loadBackdrop();
    }

    private void loadBackdrop() {
        final ImageView imageView = (ImageView) findViewById(R.id.backdrop);
        Glide.with(this).load(post.getImage_url()).centerCrop().into(imageView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.sample_actions, menu);
        return true;
    }


    public void share(Post s){

        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, post.getTitle());
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, s.getUrl());
        startActivity(Intent.createChooser(sharingIntent,"" ));


    }
    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    public void displayIframe(String iframe,WebView webView){
        WebSettings settings = webView.getSettings();

        settings.setJavaScriptEnabled(true);

        settings.setMinimumFontSize(18);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setVerticalScrollBarEnabled(false);
        //settings.setLoadWithOverviewMode(true);
        //settings.setUseWideViewPort(true);
        //settings.setJavaScriptCanOpenWindowsAutomatically(true);
        //settings.setSupportMultipleWindows(true);
        //settings.setBuiltInZoomControls(true);
        //webView.setHorizontalScrollBarEnabled(false);
        //webView.setVerticalScrollBarEnabled(false);


        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());

        String changeFontHtml = changedHeaderHtml(iframe);
        Log.d("RQ","changeFontHtml webVideo "+changeFontHtml);
        webView.loadDataWithBaseURL("http://vimeo.com", changeFontHtml , "text/html", "UTF-8", null);

    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    public void displayHtmlText(String htmlContent, String message,
                                WebView webView){

        WebSettings settings = webView.getSettings();

        settings.setJavaScriptEnabled(true);
        settings.setMinimumFontSize(18);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setVerticalScrollBarEnabled(false);

        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());
        String changeFontHtml = changedHeaderHtml(htmlContent);
        Log.d("RQ","changeFontHtml webView "+changeFontHtml);
        webView.loadDataWithBaseURL("http://vimeo.com", changeFontHtml , "text/html", "UTF-8", null);


    }

    public String changedHeaderHtml(String htmlText) {

        String head = "<html dir=\"rtl\" lang=\"\"><head><meta name=\"viewport\" content=\"width=device-width, user-scalable=yes\" /> <style type=\"text/css\"> .post_text{font-size: 220%;width:220%;}</style></head><body>";

        String closedTag = "</body></html>";
        String changeFontHtml = head + htmlText + closedTag;
        return changeFontHtml;
    }
    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onPause() {
        super.onPause();
        webView.onPause();
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onResume() {
        super.onResume();
        webView.onResume();

    }
    @Override
    public void onBackPressed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if (getFragmentManager().getBackStackEntryCount() > 0) {
                getFragmentManager().popBackStack();
            } else {
                super.onBackPressed();
            }
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                onBackPressed();
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }

}

