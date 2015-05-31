package com.sample.duncapham.timesreader.network;

import android.os.AsyncTask;
import android.util.Log;

import com.sample.duncapham.timesreader.adapter.StoryListAdapter;
import com.sample.duncapham.timesreader.model.Story;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by duncapham on 5/30/15.
 */
public class StoryFetcherAsyncTask extends AsyncTask<String, Void, String> {

    StoryListAdapter mAdapter;

    public StoryFetcherAsyncTask(StoryListAdapter adapter) {
        mAdapter = adapter;
    }

    @Override
    protected String doInBackground(String... params) {
        URL url = null;
        try {
            url = new URL(params[0]);
            URLConnection urlConnection = url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(in));

            String line = "";
            StringBuilder result = new StringBuilder();
            while((line = bufferedReader.readLine()) != null) {
                result.append(line);
            }
            in.close();
            return result.toString();
        } catch (MalformedURLException e) {
            Log.e("DEBUG", "THAO", e);
        } catch (IOException e) {
            Log.e("DEBUG", "THAO", e);
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        if (result != null) {
            try {

                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("results");
                ArrayList<Story> stories = new ArrayList<>();
                for (int i=0; i<jsonArray.length(); i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    Story newStory = new Story(obj);
                    stories.add(newStory);
                }
                mAdapter.update(stories);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
