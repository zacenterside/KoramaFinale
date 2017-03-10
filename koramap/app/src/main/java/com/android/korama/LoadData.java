package com.android.korama;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.android.korama.model.Post;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;

/**
 * Created by Dell Latitude E7470 on 10/03/2017.
 */

public class LoadData {


    public static class GetDataTask extends AsyncTask<String, String, String> {

        ProgressDialog dialog;
        RecyclerView mRecyclerView;
        Context c;
        int loadedPosts;
        int categorie;
        boolean isSpalsh;
        ProgressBar pb;

        public GetDataTask(RecyclerView rv, Context context,int l,int cat,LinkedList<Post> posts,boolean isSplash,ProgressBar pb) {
            mRecyclerView=rv;
            c=context;
            loadedPosts=l;
            categorie=cat;
            this.pb=pb;

            this.isSpalsh = isSplash;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pb.setVisibility(View.VISIBLE);
        }


        @Override
        protected String doInBackground(String... params) {


            //creating request
            OkHttpClient client = new OkHttpClient();
            HttpUrl.Builder urlBuilder = HttpUrl.parse("http://korama.net").newBuilder();
            urlBuilder.addQueryParameter("json", "get_recent_posts");
            int l=loadedPosts+10;
            urlBuilder.addQueryParameter("count", l+"");

            urlBuilder.addQueryParameter("cat", categorie+"");
            String url = urlBuilder.build().toString();


            Request request = new Request.Builder()
                    .url(url)
                    .build();
            String result="f";

            try {
                //getting response
                Response r =client.newCall(request).execute();
                result = r.body().string();

                //full response
                JSONObject jsonObject = new JSONObject(result);

                //getting posts from response
                JSONArray articles = jsonObject.getJSONArray("posts");
                //Util.getListCategorie(categorie).clear();
                Log.d("RQ","response posts : "+articles);
                Log.d("RQ","--------End response posts");
                System.out.println("llllebght"+articles.length());
                String goodTitle;
                //parsing json to model (Post)
                for(int i=loadedPosts ; i<articles.length();i++){
                    JSONObject article = new JSONObject(articles.get(i).toString());
                    Post p =new Post();
                    if (Build.VERSION.SDK_INT >= 24) {
                        goodTitle = Html.fromHtml(article.getString("title"), Html.FROM_HTML_MODE_LEGACY).toString();
                    }
                    else
                    {
                        goodTitle = Html.fromHtml(article.getString("title")).toString();
                    }

                    p.setTitle(goodTitle);


                    p.setContent(article.getString("content"));

                    p.setStatus(article.getString("status"));
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    try {
                        p.setDt(formatter.parse(article.getString("date")));

                    } catch (ParseException e) {
                        e.printStackTrace();
                        Log.e("RQ","Erro parsing date ");

                    }
                    Log.d("RQ","date : "+p.getDt());

                    JSONObject image = article.getJSONObject("thumbnail_images");
                    JSONObject image_full = image.getJSONObject("full");
                    p.setImage_url(image_full.getString("url"));
                    Log.d("RQ","image full url : "+image_full.getString("url"));

                    Util.getListCategorie(categorie).add(p) ;
                    System.out.println("fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff");

                }
               // Log.d("RQ","model posts : "+Util.posts);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return result;
        }

        @Override
        protected void onPostExecute(String aVoid) {
            super.onPostExecute(aVoid);
            pb.setVisibility(View.INVISIBLE);
            if(isSpalsh){
                c.startActivity(new Intent(c,MainActivity.class));
                ((Activity)c).finish();}
            if(mRecyclerView.getAdapter()!=null){
                mRecyclerView.getAdapter().notifyDataSetChanged();}

        }
    }

}
