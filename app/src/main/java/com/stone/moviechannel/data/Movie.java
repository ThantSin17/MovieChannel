package com.stone.moviechannel.data;

import com.bumptech.glide.Glide;
import com.stone.moviechannel.R;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.BindingAdapter;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Movie {
    @PrimaryKey
    public int id;
    @ColumnInfo
    public String title;
    @ColumnInfo
    public String description,movieLink,imageLink,movieType,time;
    @ColumnInfo
    public String country,year;

    //private int downloadCount,viewCount;


    public Movie(int id, String title, String description, String movieLink, String imageLink, String movieType, String time, String country, String year) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.movieLink = movieLink;
        this.imageLink = imageLink;
        this.movieType = movieType;
        this.time = time;
        this.country = country;
        this.year = year;
    }

    @Ignore
    public Movie() {

    }
    @BindingAdapter("profileImage")
    public static void loadImageView(AppCompatImageView imageView,String url){
        Glide.with(imageView.getContext())
                .load(url).placeholder(R.drawable.progress_animation)
                .into(imageView);
    }

//    public String getCountry() {
//        return country;
//    }
//
//    public void setCountry(String country) {
//        this.country = country;
//    }
//
//    public String getYear() {
//        return year;
//    }
//
//    public void setYear(String year) {
//        this.year = year;
//    }
//
//    public String getTime() {
//        return time;
//    }
//
//    public void setTime(String time) {
//        this.time = time;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public String getMovieLink() {
//        return movieLink;
//    }
//
//    public void setMovieLink(String movieLink) {
//        this.movieLink = movieLink;
//    }
//
//    public String getImageLink() {
//        return imageLink;
//    }
//
//    public void setImageLink(String imageLink) {
//        this.imageLink = imageLink;
//    }
//
//    public String getMovieType() {
//        return movieType;
//    }
//
//    public void setMovieType(String movieType) {
//        this.movieType = movieType;
//    }
}
