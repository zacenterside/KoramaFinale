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

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.korama.model.Post;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class CheeseListFragment extends Fragment {


    int loadedPosts= 5;
    RecyclerView rv;
    SimpleStringRecyclerViewAdapter mSimpleStringRecyclerViewAdapter;
    LinkedList<Post> mPosts = Util.posts;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rv = (RecyclerView) inflater.inflate(
                R.layout.fragment_cheese_list, container, false);
        setupRecyclerView(rv);
        return rv;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setupRecyclerView(final RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        mSimpleStringRecyclerViewAdapter = new SimpleStringRecyclerViewAdapter(getActivity(), mPosts);
        recyclerView.setAdapter(mSimpleStringRecyclerViewAdapter);

        recyclerView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(isLastItemDisplaying(recyclerView))
                    new GetDataTask().execute();
            }
        });
    }


    private boolean isLastItemDisplaying(RecyclerView recyclerView) {
        if (recyclerView.getAdapter().getItemCount() != 0) {
            int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
            if (lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == recyclerView.getAdapter().getItemCount() - 1)
                return true;
        }
        return false;
    }


    private List<String> getRandomSublist(String[] array, int amount) {
        ArrayList<String> list = new ArrayList<>(amount);
        Random random = new Random();
        while (list.size() < amount) {
            list.add(array[random.nextInt(array.length)]);
        }
        return list;
    }

    public static class SimpleStringRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleStringRecyclerViewAdapter.ViewHolder> {

        private final TypedValue mTypedValue = new TypedValue();
        private int mBackground;
        private List<Post> mValues;

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public String mBoundString;

            public final View mView;
            public final ImageView mImageView;
            public final TextView mTextView;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mImageView = (ImageView) view.findViewById(R.id.avatar);
                mTextView = (TextView) view.findViewById(android.R.id.text1);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mTextView.getText();
            }
        }

        public Post getValueAt(int position) {
            return mValues.get(position);
        }

        public SimpleStringRecyclerViewAdapter(Context context, List<Post> items) {
            context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
            mBackground = mTypedValue.resourceId;
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item, parent, false);
            view.setBackgroundResource(mBackground);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mBoundString = mValues.get(position).getTitle();
            holder.mTextView.setText(mValues.get(position).getTitle());

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, CheeseDetailActivity.class);
                    intent.putExtra(CheeseDetailActivity.EXTRA_NAME, holder.mBoundString);

                    context.startActivity(intent);
                }
            });

            Picasso.with(holder.mImageView.getContext())
                    .load(mValues.get(position).getImage_url())
                    .placeholder(R.drawable.ic_menu)
                    .error(R.drawable.ic_menu)
                    .into(holder.mImageView);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }
    }


    class GetDataTask extends AsyncTask<String, String, String> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /**
             * Progress Dialog for User Interaction
             */
            dialog = new ProgressDialog(getContext());
            dialog.setMessage("Chargement..");
            dialog.show();
        }


        @Override
        protected String doInBackground(String... params) {


            //creating request
            OkHttpClient client = new OkHttpClient();
            HttpUrl.Builder urlBuilder = HttpUrl.parse("http://korama.net").newBuilder();
            urlBuilder.addQueryParameter("json", "get_recent_posts");
            loadedPosts+=10;
            urlBuilder.addQueryParameter("count", loadedPosts+"");
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
                mPosts = new LinkedList<>();
                Log.d("RQ","response posts : "+articles);
                Log.d("RQ","--------End response posts");


                //parsing json to model (Post)
                for(int i=0 ; i<articles.length();i++){
                    JSONObject article = new JSONObject(articles.get(i).toString());
                    Post p =new Post();
                    p.setTitle(article.getString("title"));
                    JSONObject image = article.getJSONObject("thumbnail_images");
                    JSONObject image_full = image.getJSONObject("full");

                    p.setImage_url(image_full.getString("url"));
                    Log.d("RQ","image full url : "+image_full.getString("url"));

                    mPosts.add(p);

                }
                Log.d("RQ","model posts : "+Util.posts);

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
            rv.setAdapter(new SimpleStringRecyclerViewAdapter(getActivity(), mPosts));
            rv.scrollToPosition(loadedPosts-12);
            dialog.dismiss();

        }
    }
}
