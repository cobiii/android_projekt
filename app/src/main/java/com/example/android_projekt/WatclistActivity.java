package com.example.android_projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.lib.Movies;
import com.example.lib.WatchList;

public class WatclistActivity extends AppCompatActivity {
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watclist);

        listView = (ListView)findViewById(R.id.list_view);
        Movies movies = new Movies();
        WatchList watchList = ((ApplicationMy) getApplication()).getWatchList();
        movies.setList(watchList.getList());
        //Log.e("gdsadSADASDASDSADSASd",movies1.getList().toString());
        movieListAdapter movieListAdapter = new movieListAdapter(getApplicationContext(), movies);
        listView.setAdapter(movieListAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(view.getContext(),MovieActivity.class);
                i.putExtra("position",position);
                i.putExtra("watchlist",1);
                startActivity(i);
            }
        });

        this.setTitle("What's Next");
    }
}
