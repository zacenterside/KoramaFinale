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

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.korama.model.Post;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class CheeseListFragment extends Fragment {


    int loadedPosts= 5;
    RecyclerView rv;
    SimpleStringRecyclerViewAdapter mSimpleStringRecyclerViewAdapter;
    LinkedList<Post> mPosts ;
    ProgressBar p;

    int categorie;


    public CheeseListFragment setCategorie(int i,ProgressBar pb){
        mPosts = Util.getListCategorie(i);
        categorie=i;
        p=pb;
        return this;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rv = (RecyclerView) inflater.inflate(
                R.layout.fragment_cheese_list, container, false);


        setupRecyclerView(rv);
        return rv;
    }


    private void setupRecyclerView(final RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        mSimpleStringRecyclerViewAdapter = new SimpleStringRecyclerViewAdapter(getActivity(), mPosts);
        recyclerView.setAdapter(mSimpleStringRecyclerViewAdapter);


            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    if(isLastItemDisplaying(recyclerView)){

                        new LoadData.GetDataTask(rv,getContext(),loadedPosts,categorie,mPosts,false,p).execute();
                        loadedPosts+=10;

                    }

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
            public Post mPost;

            public final View mView;
            public final ImageView mImageView;
            public final TextView mTitle;
            public final TextView mPeriod;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mImageView = (ImageView) view.findViewById(R.id.avatar);
                mTitle = (TextView) view.findViewById(android.R.id.text1);
                mPeriod = (TextView) view.findViewById(R.id.period);



            }

            @Override
            public String toString() {
                return super.toString() + " '" + mTitle.getText();
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
            TextView title = (TextView) view.findViewById(android.R.id.text1);
            TextView period = (TextView) view.findViewById(R.id.period);
            Typeface font = Typeface.createFromAsset(parent.getContext().getAssets(), "fonts/Brushez.ttf");
            title.setTypeface(font);
            period.setTypeface(font);
            view.setBackgroundResource(mBackground);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mBoundString = mValues.get(position).getTitle();
            String goodTitle= mValues.get(position).getTitle();

            //----------title------------------
            mValues.get(position).getDt();
            if(goodTitle.length()>100){
                goodTitle = goodTitle.substring(0,100);
                goodTitle += "...";
            }
            holder.mTitle.setText(goodTitle);

            //----------period------------------
            String goodPeriod="";
            Date currentDate = new Date();
            Date postDate = mValues.get(position).getDt();
            long diff = currentDate.getTime() - postDate.getTime();
            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000);
            int diffInDays = (int) ((currentDate.getTime() - postDate.getTime()) / (1000 * 60 * 60 * 24));

            if (diffInDays >= 1) {
                if(diffInDays == 1)
                    goodPeriod= "منذ يوم";
                if(diffInDays == 2)
                    goodPeriod= "منذ يومين";
                if(diffInDays > 2)
                    goodPeriod= " منذ "+diffInDays+ " أيام ";
                if(diffInDays > 10)
                    goodPeriod= " منذ "+diffInDays+ " يوم ";
            } else if (diffHours >= 1) {
                if(diffHours == 1 )
                    goodPeriod = "منذ ساعة";
                if(diffHours == 2 )
                    goodPeriod= "منذ ساعتين";
                if(diffHours > 2 )
                    goodPeriod=   " منذ "+diffHours+" ساعات ";
                if(diffHours > 10 )
                    goodPeriod=   " منذ "+diffHours+" ساعة ";

            } else if (diffMinutes >= 1) {
                if(diffMinutes == 1)
                    goodPeriod= "منذ دقيقة";
                if(diffMinutes == 2)
                    goodPeriod=  "منذ دقيقتين";
                if(diffMinutes > 2)
                    goodPeriod= " منذ "+ diffMinutes +" دقائق ";
                if(diffHours > 10 )
                    goodPeriod=   " منذ "+diffHours+" دقيقة ";
            }else{
                goodPeriod= "للتو";
            }




            holder.mPeriod.setText(goodPeriod);

            holder.mPost = mValues.get(position);

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, CheeseDetailActivity.class);
                    //intent.putExtra(CheeseDetailActivity.EXTRA_NAME, holder.mBoundString);
                    intent.putExtra("Post",holder.mPost);

                    context.startActivity(intent);
                }
            });

            Picasso.with(holder.mImageView.getContext())
                    .load(mValues.get(position).getImage_url())
                    .placeholder(R.drawable.a)
                    .error(R.drawable.ic_menu)
                    .into(holder.mImageView);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }
    }





}
