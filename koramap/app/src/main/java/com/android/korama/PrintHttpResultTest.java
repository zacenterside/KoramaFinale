package com.android.korama;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import static com.android.korama.R.id.a;

public class PrintHttpResultTest extends AppCompatActivity {


    static TextView txt;
    public static String t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_http_result_test);

        txt = (TextView)findViewById(a);

        new LoadData.GetDataTask(new RecyclerView(this),this,3,3,Util.getListCategorie(3),false).execute();
        new LoadData.GetDataTask(new RecyclerView(this),this,3,4,Util.getListCategorie(4),false).execute();
        new LoadData.GetDataTask(new RecyclerView(this),this,3,5,Util.getListCategorie(5),false).execute();
        new LoadData.GetDataTask(new RecyclerView(this),this,3,6,Util.getListCategorie(6),false).execute();
        new LoadData.GetDataTask(new RecyclerView(this),this,3,7,Util.getListCategorie(7),false).execute();
        new LoadData.GetDataTask(new RecyclerView(this),this,3,33,Util.getListCategorie(33),false).execute();
        new LoadData.GetDataTask(new RecyclerView(this),this,3,8,Util.getListCategorie(8),false).execute();
        new LoadData.GetDataTask(new RecyclerView(this),this,3,9,Util.getListCategorie(9),false).execute();
        new LoadData.GetDataTask(new RecyclerView(this),this,3,1,Util.getListCategorie(1),false).execute();
        new LoadData.GetDataTask(new RecyclerView(this),this,3,10,Util.getListCategorie(10),true).execute();



    }
    

}
