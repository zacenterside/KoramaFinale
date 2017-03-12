package com.android.korama;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import mehdi.sakout.aboutpage.AboutPage;

public class About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .setImage(R.drawable.ic_logo)
                .addEmail("korama.net@gmail.com")
                .addWebsite("http://korama.net/")
                .addFacebook("korama")
                .addTwitter("koramafan")
                .addYoutube("korama1")
                .setDescription("للتواصل أو الإعلان لدينا")
                .create();
        setContentView(aboutPage);
    }
}
