package com.example.android_projekt;

import android.app.Application;
import android.util.Log;

import com.example.lib.WatchList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class ApplicationMy extends Application {
    public static final String TAG = ApplicationMy.class.getName();
    public static final String MY_FILE_NAME = "movie.json";
    public WatchList watchList; //data object
    public String idAPP;
    static private Gson gson;
    static private File file;

    @Override
    public void onCreate() {
        super.onCreate();
        watchList = new WatchList(); //init data object
        init();
    }

    private  File getFile() {
        if (file == null) {
            File filesDir = getFilesDir();
            file = new File(filesDir, MY_FILE_NAME);
        }
        Log.i(TAG, file.getPath());
        return file;
    }


    public static Gson getGson() {
        if (gson==null)
            gson = new GsonBuilder().setPrettyPrinting().create();
        return gson;
    }

    private void init() {
        if (!readFromFile())
            watchList = new WatchList(); //init data object
        String tmp = getGson().toJson(watchList);
        Log.d(TAG, tmp);
        WatchList b = getGson().fromJson(tmp,WatchList.class);
        Log.d(TAG, b.toString());


    }

    public void saveToFile() {
        try {
            FileUtils.writeStringToFile(getFile(), getGson().toJson(watchList));
        } catch (IOException e) {
            Log.d(TAG, "Can't save "+file.getPath());
        }
    }

    private boolean readFromFile() {
        if (!getFile().exists())  return false;
        try {
            watchList = getGson().fromJson(FileUtils.readFileToString(getFile()), WatchList.class);
        } catch (IOException e) {
            return false;
        }
        return true;
    }
}
