package com.example.android_projekt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                EventBus.getDefault().post(new MyEventError(3,error.toString()));
            }
        });

        queue.add(jsonObjectRequest);

        this.setTitle("Movies List");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(this, WatclistActivity.class);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
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
