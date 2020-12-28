package com.example.typicodepostviewer;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ApiRequest {
    private static ExecutorService executorService;

    public static final String API_URL = "https://jsonplaceholder.typicode.com/";
    public static final String TAG = "ApiRequest";

    private static void LazyServiceInit() {
        if (executorService == null) {
            executorService = Executors.newFixedThreadPool(1);
        }
    }

    public static ArrayList<Post> GetPosts() {
        LazyServiceInit();

        ArrayList<Post> posts = new ArrayList<>();
        HttpsWorker worker = new HttpsWorker(API_URL + "posts");
        Future<String> futureResponse = executorService.submit(worker);
        try {
            String response = futureResponse.get();
            JSONArray jArray = new JSONArray(response);

            for (int i = 0; i < jArray.length(); i++) {
                JSONObject jObject = jArray.getJSONObject(i);
                String title = jObject.getString("title");
                String body = jObject.getString("body");
                int userId = jObject.getInt("userId");
                int id = jObject.getInt("id");

                posts.add(new Post(title, body, userId, id));
            }

            Log.d(TAG, "GetPosts():: API call successful!");
            return posts;
        } catch (InterruptedException | ExecutionException | JSONException e) {
            Log.d(TAG, "ERROR:: GetPosts():: " + e.getMessage());
        }

        return null;
    }

    /**
     * Gets user data from the API
     * @param id of a user which to fetch from the API
     * @return User fetched from the API
     */
    public static User GetUser(int id) {
        LazyServiceInit();

        HttpsWorker worker = new HttpsWorker(API_URL + "users?id=" + id);
        Future<String> futureResponse = executorService.submit(worker);
        try {
            String response = futureResponse.get();
            JSONArray jArray = new JSONArray(response);
            JSONObject jObject = jArray.getJSONObject(0);
            String name = jObject.getString("name");
            String email = jObject.getString("email");
            int userId = jObject.getInt("id");

            Log.d(TAG, "GetUser():: API call successful!");
            return new User(name, email, userId);
        } catch (ExecutionException | InterruptedException | JSONException e) {
            Log.d(TAG, "ERROR:: GetUser()::" + e.getMessage());
        }

        return null;
    }

}
