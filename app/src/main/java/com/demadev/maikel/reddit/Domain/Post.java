package com.demadev.maikel.reddit.Domain;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Maikel on 26/12/2014.
 */
public class Post implements Serializable{

    @SerializedName("data")
    private RedditPost redditPost;

    public Post(RedditPost redditPost) {
        this.redditPost = redditPost;
    }

    public RedditPost getRedditPost() {
        return redditPost;
    }

    public void setRedditPost(RedditPost redditPost) {
        this.redditPost = redditPost;
    }
}
