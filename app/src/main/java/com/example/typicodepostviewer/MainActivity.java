package com.example.typicodepostviewer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


// @TODO: Read this carefully
// https://stackoverflow.com/questions/9605913/how-do-i-parse-json-in-android
public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

    // @TODO: Implement a simple UI

    // @TODO: Make GetPosts() & GetUser(String query) functions

    // @TODO: Implement Caching functionality with HttpResponseCache!!

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ExecutorService service = Executors.newFixedThreadPool(1);
        HttpsWorker worker = new HttpsWorker("posts");
        // Start API call in a different thread
        Future<String> responseFuture = service.submit(worker);
        // Stop executor service from getting new tasks
        service.shutdown();

        // Get response
        try {
            String response = responseFuture.get();
            JSONArray jArray = new JSONArray(response);

            // @TODO: Fragments could *possibly* be used here for UI content generation...read more
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject jObject = jArray.getJSONObject(i);
                Log.d(TAG, jObject.get("title").toString());
            }
        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
        }
    }
}