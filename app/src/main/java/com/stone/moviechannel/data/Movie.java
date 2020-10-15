package com.stone.moviechannel.data;

public class Movie {
    private String title,description,movieLink,imageLink,movieType;

    public Movie(String title, String description, String movieLink, String imageLink, String movieType) {
        this.title = title;
        this.description = description;
        this.movieLink = movieLink;
        this.imageLink = imageLink;
        this.movieType = movieType;
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
