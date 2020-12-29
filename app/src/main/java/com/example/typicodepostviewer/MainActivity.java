package com.example.typicodepostviewer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;


public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

    // @TODO: Configuration changes should be handled...don't let users state to be lost!


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        AppCacheManager.Install(getCacheDir(), "typicodeCache");
    }

    @Override
    protected void onStop() {
        super.onStop();

        AppCacheManager.WriteToDisk();
    }
}