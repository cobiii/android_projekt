package com.example.android_projekt;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lib.Movie;
import com.example.lib.Movies;
import com.example.lib.WatchList;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.squareup.picasso.Picasso;

public class MovieActivity<LocationRequest> extends AppCompatActivity {
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
    Button movieAddToWatchlist;

    // Location properties
    private FusedLocationProviderClient fusedLocationClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_movie);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onResume() {
        super.onResume();
        createNotificationChannel();
        Movies movies = new Movies();
        int position = getIntent().getIntExtra("position",10);
        Movie movieCurrent;
        if(getIntent().hasExtra("watchlist")) {
            WatchList watchList = ((ApplicationMy)getApplication()).getWatchList();
            movies.setList(watchList.getList());
        } else {
            movies = ((ApplicationMy)getApplication()).getMovies();
        }
        movieCurrent = movies.getList().get(position);

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

        Log.e("gal",String.valueOf(movies.getList().size() +" "+position));

        movieAddToWatchlist = findViewById(R.id.movieAddToWatchlist);
        final Movie finalMovieCurrent = movieCurrent;
        movieAddToWatchlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WatchList watchList = ((ApplicationMy) getApplication()).getWatchList();
                watchList.addMovie(finalMovieCurrent);
                ((ApplicationMy) getApplication()).setWatchList(watchList);
                addNotification();
                fetchLastLocation();
                Log.e("GASDLSADASLDA",String.valueOf(((ApplicationMy) getApplication()).getWatchList().getList().size()));
            }
        });

    }
    private void fetchLastLocation() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                showPermissionAlert();
                return;
            }
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                            Log.e("LAST LOCATION: ", location.toString());
                        }
                    }
                });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 123: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    // permission was denied, show alert to explain permission
                    showPermissionAlert();
                }else{
                    //permission is granted now start a background service
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        fetchLastLocation();
                    }
                }
            }
        }
    }

    private void showPermissionAlert(){
        if (ActivityCompat.checkSelfPermission(MovieActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MovieActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 123);
        }
    }

    private void addNotification() {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this,"notif")
                        .setSmallIcon(R.drawable.ic_notifications_none_black_24dp)
                        .setContentTitle("Don't Forget To Watch")
                        .setContentText("Successfully added to your watchlist.")
                        .setPriority(NotificationCompat.PRIORITY_HIGH);

        Intent notifyIntent = new Intent(this, WatclistActivity.class);
        // Set the Activity to start in a new, empty task
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        // Create the PendingIntent
        PendingIntent notifyPendingIntent = PendingIntent.getActivity(
                this, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT
        );

        builder.setContentIntent(notifyPendingIntent);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        notificationManager.notify(10,builder.build());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel() {
        CharSequence name = "ime";
        String description = "desc";
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel channel = new NotificationChannel("notif",name,importance);
        channel.setDescription(description);

        NotificationManager manager = getSystemService(NotificationManager.class);
        manager.createNotificationChannel(channel);
    }
}
