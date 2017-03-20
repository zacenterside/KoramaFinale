package com.android.korama;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.korama.model.load;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static com.android.korama.R.color.primary_dark;


public class PrintHttpResultTest extends AppCompatActivity {


    static TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_http_result_test);



        ProgressBar p = (ProgressBar) findViewById(R.id.pb);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.primary_dark));
        }



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
            startActivity(new Intent(PrintHttpResultTest.this,MainActivity.class));
                finish();
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


}
