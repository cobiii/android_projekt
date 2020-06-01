package com.example.android_projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.android_projekt.events.MyEventError;
import com.example.android_projekt.events.MyEventInfo;
import com.example.lib.Movie;
import com.example.lib.Movies;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.ProgressDialog;

public class MainActivity extends AppCompatActivity {


    private Integer[] moviesImages = new Integer[]{
            R.drawable.theshawshankredemption,R.drawable.thegodfather,
            R.drawable.thedarkknight,R.drawable.logan,
            R.drawable.fightclub,R.drawable.forrestgump,R.drawable.inception,
            R.drawable.logan,R.drawable.geminiman};
    private String[] moviesTitles = new String[]{
            "The Shawshank Redemption","The Godfather","The Dark Knight",
            "Schindler's List","Fight Club","Forrest Gump","Inception","Logan","Gemini Man"};
    private String[] moviesYears = new String[]{
            "1994","1972","2008","1993","1999","1994","2010","2017","2019"};
    private String[] moviesGenres = new String[]{
            "Drama","Crime","Action","Biography","Drama","Drama","Sci-Fi","Action","Sci-Fi"};
    private String[] moviesRates = new String[]{
            "9,3","9,2","9,0","8,9","8,8","8,8","8,8","8,1","-,-"};
    ListView listView;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://192.168.1.111:3000/movies";

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest
        (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            Gson gson = new Gson();
            @Override
            public void onResponse(JSONArray response) {
                // display response
                new AsyncTaskMovies().execute(response);
                Movies movies = new Movies();/*
                for(int x = 0; x < response.length(); x++) {
                    try {
                        JSONObject temp = response.getJSONObject(x);
                        Movie movie = gson.fromJson(String.valueOf(temp), Movie.class);
                        movies.addMovie(movie);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        EventBus.getDefault().post(new MyEventError(3,e.toString()));
                    }
                }
                */
                //textView.setText(movies.getList().get(0).getTitle());
                //((ApplicationMy) getApplication()).setMovies(movies);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //textView.setText(error.toString());
            }
        });

        queue.add(jsonObjectRequest);



        this.setTitle("Movies List");
/*
        listView = (ListView)findViewById(R.id.list_view);
        Movies movies1 = ((ApplicationMy) getApplication()).getMovies();
        //Log.e("gdsadSADASDASDSADSASd",movies1.getList().toString());
        movieListAdapter movieListAdapter = new movieListAdapter(
                moviesImages,moviesTitles,moviesYears,moviesGenres,moviesRates,this, movies1);
        listView.setAdapter(movieListAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(view.getContext(),MovieActivity.class);
                i.putExtra("position",position);
                startActivity(i);
            }
        }); */
    }

    public class AsyncTaskMovies extends AsyncTask<JSONArray, Integer, Integer> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // display a progress dialog for good user experiance
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Please Wait");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        protected Integer doInBackground(JSONArray... response) {
            Gson gson = new Gson();
            Movies movies = new Movies();
            for(int x = 0; x < response[0].length(); x++) {
                try {
                    Log.e("gal",String.valueOf(response[0].getJSONObject(x).toString()));
                    JSONObject temp = response[0].getJSONObject(x);
                    Movie movie = gson.fromJson(String.valueOf(temp), Movie.class);
                    movies.addMovie(movie);
                } catch (JSONException e) {
                    e.printStackTrace();
                    EventBus.getDefault().post(new MyEventError(3,e.toString()));
                }
            }
            ((ApplicationMy) getApplication()).setMovies(movies);
            return 1;
        }

        protected void onPostExecute(Integer result) {
            Log.e("gal","KONCAL");
            progressDialog.dismiss();
            listView = (ListView)findViewById(R.id.list_view);
            Movies movies = ((ApplicationMy) getApplication()).getMovies();
            //Log.e("gdsadSADASDASDSADSASd",movies1.getList().toString());
            movieListAdapter movieListAdapter = new movieListAdapter(getApplicationContext(), movies);
            listView.setAdapter(movieListAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent i = new Intent(view.getContext(),MovieActivity.class);
                    i.putExtra("position",position);
                    startActivity(i);
                }
            });
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        //EventBus.getDefault().post(new MyEventInfo("Welcome back "));
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void infoData(MyEventInfo info) {
        Toast.makeText(this,info.getMsg(),Toast.LENGTH_LONG).show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void infoError(MyEventError info) {
        Toast.makeText(this,info.toString(),Toast.LENGTH_SHORT).show();
    }
}
