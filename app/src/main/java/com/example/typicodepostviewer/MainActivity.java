package com.example.typicodepostviewer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.http.HttpResponseCache;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

    // @TODO: Configuration changes should be handled...don't let users state to be lost!


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppCacheManager.Install(getCacheDir(), "typicodeCache");
    }

    @Override
    protected void onStop() {
        super.onStop();

        AppCacheManager.WriteToDisk();
    }
}