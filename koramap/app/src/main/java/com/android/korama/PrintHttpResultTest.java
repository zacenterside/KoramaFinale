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

        new LoadData.GetDataTask(new RecyclerView(this),this,3,3,Util.getListCategorie(3),false,new ProgressBar(this)).execute();
        new LoadData.GetDataTask(new RecyclerView(this),this,3,4,Util.getListCategorie(4),false,new ProgressBar(this)).execute();
        new LoadData.GetDataTask(new RecyclerView(this),this,3,5,Util.getListCategorie(5),false,new ProgressBar(this)).execute();
        new LoadData.GetDataTask(new RecyclerView(this),this,3,6,Util.getListCategorie(6),false,new ProgressBar(this)).execute();
        new LoadData.GetDataTask(new RecyclerView(this),this,3,7,Util.getListCategorie(7),false,new ProgressBar(this)).execute();
        new LoadData.GetDataTask(new RecyclerView(this),this,3,33,Util.getListCategorie(33),false,new ProgressBar(this)).execute();
        new LoadData.GetDataTask(new RecyclerView(this),this,3,8,Util.getListCategorie(8),false,new ProgressBar(this)).execute();
        new LoadData.GetDataTask(new RecyclerView(this),this,3,9,Util.getListCategorie(9),false,new ProgressBar(this)).execute();
        new LoadData.GetDataTask(new RecyclerView(this),this,3,1,Util.getListCategorie(1),false,new ProgressBar(this)).execute();
        new LoadData.GetDataTask(new RecyclerView(this),this,3,10,Util.getListCategorie(10),true,p).execute();



    }


}
