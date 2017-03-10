package com.android.korama;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;


public class PrintHttpResultTest extends AppCompatActivity {


    static TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_http_result_test);

        ProgressBar p = (ProgressBar) findViewById(R.id.pb);
        p.setVisibility(View.INVISIBLE);

        new LoadData.GetDataTask(new RecyclerView(this),this,0,3,Util.getListCategorie(3),false,new ProgressBar(this)).execute();
        new LoadData.GetDataTask(new RecyclerView(this),this,0,4,Util.getListCategorie(4),false,new ProgressBar(this)).execute();
        new LoadData.GetDataTask(new RecyclerView(this),this,0,5,Util.getListCategorie(5),false,new ProgressBar(this)).execute();
        new LoadData.GetDataTask(new RecyclerView(this),this,0,6,Util.getListCategorie(6),false,new ProgressBar(this)).execute();
        new LoadData.GetDataTask(new RecyclerView(this),this,0,7,Util.getListCategorie(7),false,new ProgressBar(this)).execute();
        new LoadData.GetDataTask(new RecyclerView(this),this,0,33,Util.getListCategorie(33),false,new ProgressBar(this)).execute();
        new LoadData.GetDataTask(new RecyclerView(this),this,0,8,Util.getListCategorie(8),false,new ProgressBar(this)).execute();
        new LoadData.GetDataTask(new RecyclerView(this),this,0,9,Util.getListCategorie(9),false,new ProgressBar(this)).execute();
        new LoadData.GetDataTask(new RecyclerView(this),this,0,1,Util.getListCategorie(1),false,new ProgressBar(this)).execute();
        new LoadData.GetDataTask(new RecyclerView(this),this,0,10,Util.getListCategorie(10),true,p).execute();



    }


}
