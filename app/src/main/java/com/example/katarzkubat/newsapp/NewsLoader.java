package com.example.katarzkubat.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;

public class NewsLoader extends AsyncTaskLoader<ArrayList<News>> {

    private String nUrl;

    public NewsLoader(Context context, String url) {
        super(context);
        nUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<News> loadInBackground() {
        if (nUrl == null) {
            return null;
        }

        ArrayList<News> article = QueryUtils.fetchNewsData(nUrl);
        return article;
    }
}