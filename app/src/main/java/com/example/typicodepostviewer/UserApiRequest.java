package com.example.typicodepostviewer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class UserApiRequest extends ApiRequest<User> {
    public UserApiRequest() {
        super();
    }

    @Override
    public User getParsedResponse(String url) {
        HttpsWorker worker = new HttpsWorker(url);
        Future<String> fRes = sExecutorService.submit(worker);

        try {
            String response = fRes.get();
            JSONObject obj = new JSONObject(response);
            String name = obj.getString("name");
            String email = obj.getString("email");
            return new User(name, email);
        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
