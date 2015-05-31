package com.sample.duncapham.timesreader.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.sample.duncapham.timesreader.R;
import com.sample.duncapham.timesreader.StoryActivity;
import com.sample.duncapham.timesreader.model.Story;

import java.util.ArrayList;

import static android.support.v4.app.ActivityCompat.startActivity;

/**
 * Created by duncapham on 5/30/15.
 */
public class StoryListAdapter extends RecyclerView.Adapter<StoryListAdapter.ViewHolder> {

    private ArrayList<Story> dataSet = new ArrayList<>();

    @Override
    public StoryListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.story_list_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(StoryListAdapter.ViewHolder viewHolder, int i) {
        ImageLoader imageLoader = ImageLoader.getInstance();
        String thumbUrl = dataSet.get(i).getThumbUrl();
        if (thumbUrl != null) {
            imageLoader.displayImage(dataSet.get(i).getThumbUrl(),
                                     viewHolder.ivThumb);
        } else {
            viewHolder.ivThumb.setImageDrawable(null);
        }
        viewHolder.tvTitle.setText(dataSet.get(i).getTitle());

        // implements onClickListener for tvTitle
        final String storyUrl = dataSet.get(i).getUrl();
        viewHolder.tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), StoryActivity.class);
                intent.putExtra("data", storyUrl);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        public ImageView ivThumb;

        public ViewHolder(View view) {
            super(view);
            tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            ivThumb = (ImageView) view.findViewById(R.id.ivThumb);
        }
    }

    public void update(ArrayList<Story> objs) {
        dataSet = objs;
        notifyDataSetChanged();
    }
}
