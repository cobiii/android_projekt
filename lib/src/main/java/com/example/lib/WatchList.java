package com.example.lib;

import java.util.ArrayList;

public class WatchList {
    ArrayList<Movie> list;

    public WatchList() {
        list = new ArrayList<Movie>();
    }

    public ArrayList<Movie> getList() {
        return list;
    }

    public void addMovie(Movie movie) {
        this.list.add(movie);
    }
}
