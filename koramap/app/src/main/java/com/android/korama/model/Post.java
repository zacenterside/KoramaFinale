package com.android.korama.model;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Chaimaa on 06/03/2017.
 */

public class Post implements Serializable {
    private String title;
    private String content;
    private String author;
    private Date dt;
    private int views;
    private String source;
    private String imageUrl; //full
    private String category;
    private String status; //publish or not
    private String url;
    private String iframe;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDt() {
        return dt;
    }

    public void setDt(Date dt) {
        this.dt = dt;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getImage_url() {
        return imageUrl;
    }

    public void setImage_url(String image_url) {
        this.imageUrl = image_url;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIframe() {
        return iframe;
    }

    public void setIframe(String iframe) {
        this.iframe = iframe;
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }
    public static List<String> devideIframeFromContent(String post){
        List returns = new ArrayList<>();
        Log.d("RQ","*****video from post");
        String iframes = "";
        String iframe ="";
        String cleanPost = post;

        int startIndex = -1;
        int endIndex = -1;

        do {
            if(cleanPost.equals("")) break;
            startIndex = cleanPost.indexOf("<iframe");
            Log.d("RQ", "startIndex " + startIndex);
            endIndex = cleanPost.indexOf("</iframe>");
            Log.d("RQ", "endIndex " + endIndex);

            if (startIndex != -1 && endIndex != -1) {
                iframe = cleanPost.substring(startIndex, endIndex + 9);
                cleanPost = cleanPost.replace(iframe, "");
                iframes += iframe;

            }
        }while(startIndex!=-1 && endIndex != -1);

        returns.add(cleanPost);
        returns.add(iframes);
        return returns;
    }


}
