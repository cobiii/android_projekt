package com.example.android_projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lib.Movie;
import com.example.lib.Movies;
import com.squareup.picasso.Picasso;

public class MovieActivity extends AppCompatActivity {
    TextView movieTitle;
    TextView movieGenre;
    TextView movieRating;
    TextView movieRatingCount;
    TextView movieSynopsis;
    TextView movieDirector;
    TextView movieWriter;
    TextView movieStartTime;
    TextView movieStudio;
    TextView movieBoxOffice;
    ImageView moviePoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_movie);
        Movies movies = ((ApplicationMy)getApplication()).getMovies();
        int position = getIntent().getIntExtra("position",10);
        Movie movieCurrent = movies.getList().get(position);
        movieTitle = findViewById(R.id.movieTitle);
        movieGenre = findViewById(R.id.movieGenre);
        movieRating = findViewById(R.id.movieRating);
        movieRatingCount = findViewById(R.id.movieRatingCount);
        movieSynopsis = findViewById(R.id.movieSynopsis);
        movieDirector = findViewById(R.id.movieDirector);
        movieWriter = findViewById(R.id.movieWriter);
        movieStartTime = findViewById(R.id.movieStartTime);
        movieStudio = findViewById(R.id.movieStudio);
        movieBoxOffice = findViewById(R.id.movieBoxOffice);
        moviePoster = findViewById(R.id.moviePoster);

        movieTitle.setText(movieCurrent.getTitle() + "("+movieCurrent.getYear()+")");
        movieGenre.setText(movieCurrent.getGenre());
        movieRating.setText(String.format("%.1f",movieCurrent.getRating()));
        movieRatingCount.setText(movieCurrent.getRatingCount().toString());
        movieSynopsis.setText(movieCurrent.getSynopsis());
        movieDirector.append(movieCurrent.getDirector());
        movieWriter.append(movieCurrent.getWriter());
        movieStartTime.append(movieCurrent.getRunningTimeStr());
        movieStudio.append(movieCurrent.getStudio());
        Picasso.get().load(movieCurrent.getPoster()).into(moviePoster);
        //movieBoxOffice.append(movieCurrent.getBoxoffice().toString());

        Log.i("gal",String.valueOf(movies.getList().size()));
        //this.setTitle(movieCurrent.getTitle());
    }
}
