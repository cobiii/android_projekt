package com.example.lib;

import java.util.ArrayList;

public class Movies {
    protected ArrayList<Movie> list;

    public Movies() {
        list = new ArrayList<Movie>();
    }

    public ArrayList<Movie> getList() {
        return list;
    }

    public void setList(ArrayList<Movie> list) {
        this.list = list;
    }

    public void addMovie(Movie movie) {
        this.list.add(movie);
    }
}
