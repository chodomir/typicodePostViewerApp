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

public abstract class ApiRequest<T> {
    protected static ExecutorService sExecutorService;

    public ApiRequest() {
        if (sExecutorService == null) {
            sExecutorService = Executors.newFixedThreadPool(1);
        }
    }

    public abstract T getParsedResponse(String url);
}
