package com.sample.duncapham.timesreader.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by duncapham on 5/30/15.
 */
public class Story implements Serializable {

    private String title;
    private String url;
    private String thumbUrl;
    private int thumbHeight;
    private int thumbWidth;

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public int getThumbHeight() {
        return thumbHeight;
    }

    public int getThumbWidth() {
        return thumbWidth;
    }

    public Story(JSONObject obj) {
        try {
            title = obj.getString("title");
            url = obj.getString("url");
            getImgAttrs(obj.getJSONArray("multimedia"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getImgAttrs(JSONArray multimedia) {
        if (multimedia == null || multimedia.length() == 0) {
            return;
        }
        for (int i=0; i<multimedia.length(); i++) {
            try {
                JSONObject obj = multimedia.getJSONObject(i);
                if (obj.getString("format").equals("Normal")) {
                    thumbUrl = obj.getString("url");
                    thumbWidth = obj.getInt("width");
                    thumbHeight = obj.getInt("height");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


}
