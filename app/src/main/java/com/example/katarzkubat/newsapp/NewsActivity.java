package com.example.katarzkubat.newsapp;

import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;

import static android.view.View.GONE;


public class NewsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<News>> {

    private static final String USGS_REQUEST_URL = "https://content.guardianapis.com/search?api-key=test";

    private static final int NEWS_LOADER_ID = 1;
    private NewsRecyclerAdapter nAdapter;
    private RecyclerView recyclerView;
    private boolean resultsArePresent = false;
    private String resultsString = "resultsString";
    private RecyclerView.LayoutManager nLayoutManager;
    private NewsLoader newsLoader;
    private TextView nEmptyStateTextView;
    private ArrayList<News> newsArrayList = new ArrayList<News>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_news);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);

        nLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(nLayoutManager);

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            getLoaderManager().initLoader(NEWS_LOADER_ID, null, NewsActivity.this);

        } else {

            LinearLayout progressLayout = (LinearLayout) findViewById(R.id.progress_layout);
            progressLayout.setVisibility(View.VISIBLE);

            TextView nEmptyStateTextView = (TextView) findViewById(R.id.error);
            nEmptyStateTextView.setText(R.string.error);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outSave) {
        outSave.putBoolean(resultsString, resultsArePresent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<ArrayList<News>> onCreateLoader(int i, Bundle bundle) {

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String section = sharedPrefs.getString(
                getString(R.string.section), "");
        Uri baseUri = Uri.parse(USGS_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("q", section);

        LinearLayout progressLayout = (LinearLayout) findViewById(R.id.progress_layout);
        progressLayout.setVisibility(View.VISIBLE);

        return new NewsLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<News>> loader, ArrayList<News> news) {

        LinearLayout progressLayout = (LinearLayout) findViewById(R.id.progress_layout);
        progressLayout.setVisibility(GONE);

        nAdapter = new NewsRecyclerAdapter(this, new ArrayList<News>());
        if (news != null && !news.isEmpty()) {

            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);

            nAdapter = new NewsRecyclerAdapter(this, news);
            recyclerView.setAdapter(nAdapter);

            ((LinearLayout) findViewById(R.id.progress_layout)).setVisibility(GONE);
        } else {

            TextView bEmptyStateTextView = (TextView) findViewById(R.id.error);
            bEmptyStateTextView.setText(R.string.no_news);
        }

        resultsArePresent = true;
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<News>> loader) {
    }
}