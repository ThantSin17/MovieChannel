package com.stone.moviechannel.data;

public class Movie {
    private String title,description,movieLink,imageLink,movieType,time;
    private String country,year;
    private int downloadCount,viewCount;

    public Movie(String title, String description, String movieLink, String imageLink, String movieType, String time, String country, String year, int downloadCount, int viewCount) {
        this.title = title;
        this.description = description;
        this.movieLink = movieLink;
        this.imageLink = imageLink;
        this.movieType = movieType;
        this.time = time;
        this.country = country;
        this.year = year;
        this.downloadCount = downloadCount;
        this.viewCount = viewCount;
    }

    public Movie() {

    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(int downloadCount) {
        this.downloadCount = downloadCount;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMovieLink() {
        return movieLink;
    }

    public void setMovieLink(String movieLink) {
        this.movieLink = movieLink;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getMovieType() {
        return movieType;
    }

    public void setMovieType(String movieType) {
        this.movieType = movieType;
    }
}
