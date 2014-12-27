package com.demadev.maikel.reddit.Domain;

import java.io.Serializable;

/**
 * Created by Maikel on 26/12/2014.
 */
public class RedditPost implements Serializable{

    private String id;
    private String subreddit;
    private String author;
    private int score;
    private String thumbnail;
    private String permalink;
    private int created;
    private String url;
    private String title;
    private int created_utc;
    private int num_comments;

    public RedditPost(String id, String subreddit, String author, int score, String thumbnail, String permalink, int created, String url, String title, int created_utc, int num_comments) {
        this.id = id;
        this.subreddit = subreddit;
        this.author = author;
        this.score = score;
        this.thumbnail = thumbnail;
        this.permalink = permalink;
        this.created = created;
        this.url = url;
        this.title = title;
        this.created_utc = created_utc;
        this.num_comments = num_comments;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubreddit() {
        return subreddit;
    }

    public void setSubreddit(String subreddit) {
        this.subreddit = subreddit;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getPermalink() {
        return permalink;
    }

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    public int getCreated() {
        return created;
    }

    public void setCreated(int created) {
        this.created = created;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCreated_utc() {
        return created_utc;
    }

    public void setCreated_utc(int created_utc) {
        this.created_utc = created_utc;
    }

    public int getNum_comments() {
        return num_comments;
    }

    public void setNum_comments(int num_comments) {
        this.num_comments = num_comments;
    }

    public int hoursAgo(){
        return (created-created_utc)/3600;
    }
}
