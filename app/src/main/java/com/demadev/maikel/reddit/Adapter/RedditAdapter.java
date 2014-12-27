package com.demadev.maikel.reddit.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.demadev.maikel.reddit.Domain.Post;
import com.demadev.maikel.reddit.Domain.RedditPost;
import com.demadev.maikel.reddit.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Maikel on 26/12/2014.
 */
public class RedditAdapter extends ArrayAdapter<Post> {

    private Context context;
    private int resource;
    private List<Post> items;

    private RedditPostClickListener listener;

    public RedditAdapter(Context context, int resource, List<Post> items, RedditPostClickListener listener) {
        super(context, resource, items);
        this.context = context;
        this.resource = resource;
        this.items = items;
        this.listener = listener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder viewHolder;

        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(resource, parent, false);
            viewHolder = new ViewHolder();

            viewHolder.scoreView = (TextView) row.findViewById(R.id.post_score);
            viewHolder.titleView = (TextView) row.findViewById(R.id.post_title);
            viewHolder.subtitleView = (TextView) row.findViewById(R.id.post_info);
            viewHolder.commentsView = (TextView) row.findViewById(R.id.post_comments);
            viewHolder.thumbnailView = (ImageView) row.findViewById(R.id.post_thumb);
            row.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) row.getTag();
        }

        final RedditPost post = items.get(position).getRedditPost();
        viewHolder.scoreView.setText(Integer.toString(post.getScore()));
        viewHolder.titleView.setText(post.getTitle());
        viewHolder.titleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onTitleClick(post);
            }
        });
        viewHolder.subtitleView.setText(post.hoursAgo() + " uur gelden gepost door " + post.getAuthor() + " in /r/" + post.getSubreddit());
        viewHolder.commentsView.setText(post.getNum_comments() + " reacties");
        viewHolder.commentsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onCommentClick(post);
            }
        });
        String url = post.getThumbnail();
        if(url == null || url.isEmpty() || !url.startsWith("http://")) {
            url = "http://www.redditstatic.com/icon.png";
        }else{
            viewHolder.thumbnailView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onThumbnailClick(post);
                }
            });
        }
        Picasso.with(context)
                .load(url)
                .into(viewHolder.thumbnailView);

        return row;
    }

    public interface RedditPostClickListener{
        public void onThumbnailClick(RedditPost post);
        public void onTitleClick(RedditPost post);
        public void onCommentClick(RedditPost post);
    }

    private static class ViewHolder {
        TextView scoreView;
        TextView titleView;
        TextView subtitleView;
        TextView commentsView;
        ImageView thumbnailView;
    }
}
