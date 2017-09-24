package com.example.katarzkubat.newsapp;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;


public class NewsRecyclerAdapter extends RecyclerView.Adapter<NewsRecyclerAdapter.ViewHolder> {

    private ArrayList<News> nNews;
    private Context nContext;

    public NewsRecyclerAdapter(Context context, ArrayList<News> news) {
        nContext = context;
        nNews = news;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        protected TextView section;
        protected TextView title;
        protected TextView date;
        protected LinearLayout nItemV;

        public ViewHolder(View itemView) {
            super(itemView);

            nItemV = (LinearLayout) itemView;
            section = (TextView) itemView.findViewById(R.id.section);
            title = (TextView) itemView.findViewById(R.id.title);
            date = (TextView) itemView.findViewById(R.id.date);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_list_item, parent, false);
        ViewHolder holder = new ViewHolder(listItem);
        return holder;
    }

    @Override
    public void onBindViewHolder(NewsRecyclerAdapter.ViewHolder holder, int position) {

        final News currentNews = nNews.get(position);
        holder.section.setText(currentNews.getSection());
        holder.title.setText(currentNews.getTitle());
        holder.date.setText(currentNews.getDate());
        holder.nItemV.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                Uri newsUri = Uri.parse(currentNews.getUrl());
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsUri);
                nContext.startActivity(websiteIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return nNews.size();
    }
}
