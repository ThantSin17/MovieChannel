package com.stone.moviechannel.data;

public class ImageSlide {
    private String title,imageUrl,movieId;

    public ImageSlide(String title, String imageUrl, String movieId) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.movieId = movieId;
    }

    public ImageSlide() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }
}
