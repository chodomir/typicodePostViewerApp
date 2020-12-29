package com.example.typicodepostviewer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    public static final String USER_API_URL = "https://jsonplaceholder.typicode.com/users/";
    public static final String POST_API_URL = "https://jsonplaceholder.typicode.com/posts/";

    // TODO: These values should be final
    public static UserApiRequest sUserRequest;
    public static PostsApiRequest sPostsRequest;
    public static ApiResponseCache<ArrayList<Post>> sPostsCache;
    public static ApiResponseCache<User> sUsersCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sUserRequest = new UserApiRequest();
        sPostsRequest = new PostsApiRequest();
        sPostsCache = new ApiResponseCache<>(getCacheDir(), "posts.ser",
                sPostsRequest, 300);
        sUsersCache = new ApiResponseCache<>(getCacheDir(), "users.ser",
                sUserRequest, 300);
    }

    @Override
    protected void onStop() {
        super.onStop();

        // TODO: saves are to slow.
        sPostsCache.save();
        sUsersCache.save();
    }
}