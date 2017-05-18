package com.ramonem.spotysearch;

/**
 * Created by ramonem on 18-05-17.
 */

public class Artist {

    String name;
    int followers;
    String type;
    String url;

    public Artist(String name, int followers, String type, String url) {
        this.name = name;
        this.followers = followers;
        this.type = type;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
