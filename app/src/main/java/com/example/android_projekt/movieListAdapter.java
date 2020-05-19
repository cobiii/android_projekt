package com.example.android_projekt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class movieListAdapter extends BaseAdapter {
    private Integer[] moviesImages;
    private String[] moviesTitles, moviesYears, moviesGenres, moviesRates;
    private LayoutInflater inflter;
    private Context context;
    private ImageView movieImg;
    private TextView movieTitle, yearMovie, genresMovie, rateMovie;

    public movieListAdapter(Integer[] moviesImages, String[] moviesTitles, String[] moviesYears,
                            String[] moviesGenres, String[] moviesRates, Context context) {
        this.moviesImages = moviesImages;
        this.moviesTitles = moviesTitles;
        this.moviesYears = moviesYears;
        this.moviesGenres = moviesGenres;
        this.moviesRates = moviesRates;
        this.context = context;
        inflter = (LayoutInflater.from(context));
    }
    @Override
    public int getCount() {
        return moviesTitles.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        view = inflter.inflate(R.layout.listview_row, null);
        movieImg = view.findViewById(R.id.movieImg);
        movieTitle = view.findViewById(R.id.movieTitle);
        yearMovie = view.findViewById(R.id.yearMovie);
        genresMovie = view.findViewById(R.id.genresMovie);
        rateMovie = view.findViewById(R.id.rateMovie);
        movieImg.setImageResource(moviesImages[position]);
        movieTitle.setText(moviesTitles[position]);
        yearMovie.setText(moviesYears[position]);
        genresMovie.setText(moviesGenres[position]);
        rateMovie.setText(moviesRates[position]);
        return view;
    }}
