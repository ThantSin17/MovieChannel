package com.stone.moviechannel.data;

public class VideoDetail {
    private String url,quality,type;

    public VideoDetail(String url, String quality, String type) {
        this.url = url;
        this.quality = quality;
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "VideoDetail{" +
                " type='" + type + '\'' +"          "+
                ", quality='" + quality + '\'' +

                '}';
    }
}
