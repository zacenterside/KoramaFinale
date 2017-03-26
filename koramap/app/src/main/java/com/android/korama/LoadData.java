package com.android.korama;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
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
import java.util.List;

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
        View pb;
        List<Post> temp = new LinkedList<>();

        public GetDataTask(RecyclerView rv, Context context,int l,int cat,LinkedList<Post> posts,boolean isSplash,View pb) {
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
                for(int i=0 ; i<articles.length();i++){
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
                    p.setUrl(article.getString("url"));

                    p.setStatus(article.getString("status"));
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    try {
                        p.setDt(formatter.parse(article.getString("date")));

                    } catch (ParseException e) {
                        e.printStackTrace();
                        Log.e("RQ","Erro parsing date ");

                    }
                    Log.d("RQ","date : "+p.getDt());
                    if(article.has("thumbnail_images")){
                            JSONObject image = article.getJSONObject("thumbnail_images");
                            JSONObject image_full = image.getJSONObject("medium");
                            p.setImage_url(image_full.getString("url"));
                            //Log.d("RQ","image full url : "+image_full.getString("url"));

                            System.out.println("rrrrrrrrrrrr   "+ article.get("categories").toString() );
                            if(i < Util.getListCategorie(categorie).size())
                                Util.getListCategorie(categorie).set(i,p) ;
                            else
                                Util.getListCategorie(categorie).add(p) ;
                    }

                    JSONObject custom_fields = article.getJSONObject("custom_fields");
                    Log.d("RQ","custom_fields : "+ custom_fields);
                    Log.d("RQ","ggg");
                    if(custom_fields.has("tie_embed_code")){ //custom_fields{ ..{tie_embed_code[myIframeVideo,...]}
                        System.out.println("before in");
                        Log.d("RQ","before in");
                        //String video = article.getJSONObject("custom_fields").getJSONArray("tie_embed_code").getString(0);
                        JSONArray tie_embed_code = custom_fields.getJSONArray("tie_embed_code");
                        Log.d("RQ","tie_embed_code : "+ tie_embed_code);
                        String video = tie_embed_code.getString(0);
                        Log.d("RQ","video : "+ video);
                        if (Build.VERSION.SDK_INT >= 24) {
                            p.setIframe(Html.fromHtml(video, Html.FROM_HTML_MODE_LEGACY).toString());
                        }else
                        {
                            p.setIframe(Html.fromHtml(video).toString());
                        }
                        System.out.println("iframe : "+ video);
                        Log.d("RQ","iframe : "+ video);
                    }
                    System.out.println("fuuuuuckinnng log");
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
            if(isSpalsh){
                c.startActivity(new Intent(c,MainActivity.class));
                ((Activity)c).finish();}
            if(mRecyclerView.getAdapter()!=null){
                mRecyclerView.getAdapter().notifyDataSetChanged();}


            if(pb instanceof ProgressBar)
                pb.setVisibility(View.INVISIBLE);
            if(pb instanceof SwipeRefreshLayout)
                ((SwipeRefreshLayout) pb).setRefreshing(false);

        }
    }

}
