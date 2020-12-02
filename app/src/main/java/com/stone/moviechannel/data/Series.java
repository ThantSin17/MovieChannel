package com.stone.moviechannel.data;

public class Series {
    public String id,link;

    public Series(String id, String link) {
        this.id = id;
        this.link = link;
    }

    @Override
    public String toString() {
        return id;
    }
}
