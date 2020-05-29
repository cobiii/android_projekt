package com.example.android_projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.lib.Movie;
import com.example.lib.Movies;

public class MovieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        Movies movies = ((ApplicationMy)getApplication()).getMovies();
        int position = getIntent().getIntExtra("position",10);
        Movie movieCurrent = movies.getList().get(position);
        Log.i("gal",String.valueOf(movies.getList().size()));
        this.setTitle(movieCurrent.getTitle());
    }
}
