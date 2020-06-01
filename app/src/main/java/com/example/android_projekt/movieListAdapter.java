package com.example.android_projekt;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lib.Movie;
import com.example.lib.Movies;
import com.squareup.picasso.Picasso;

public class movieListAdapter extends BaseAdapter {
    private LayoutInflater inflter;
    private Context context;
    private ImageView movieImg;
    private TextView movieTitle, yearMovie, genresMovie, rateMovie;
    private Movies movies;

    public movieListAdapter(Context context, Movies movies) {
        this.movies = movies;
        this.context = context;
        inflter = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return movies.getList().size();
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

        Picasso.get().load(movies.getList().get(position).getPoster()).into(movieImg);
        //movieImg.setImageResource(movies.getList().get(position).getPoster());
        movieTitle.setText(movies.getList().get(position).getTitle());
        //yearMovie.setText(moviesYears[position]);
        genresMovie.setText(movies.getList().get(position).getGenre());
        //rateMovie.setText(moviesRates[position]);
        return view;
    }
}
