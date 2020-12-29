package com.example.typicodepostviewer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class PostsApiRequest extends ApiRequest<ArrayList<Post>> {
    public PostsApiRequest() {
        super();
    }

    @Override
    public ArrayList<Post> getParsedResponse(String url) {
        ArrayList<Post> posts = new ArrayList<>();
        HttpsWorker worker = new HttpsWorker(url);
        Future<String> fRes = sExecutorService.submit(worker);

        try {
            String response = fRes.get();
            JSONArray jArray = new JSONArray(response);
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject obj = jArray.getJSONObject(i);
                String title = obj.getString("title");
                String body = obj.getString("body");
                int userId = obj.getInt("userId");
                int id = obj.getInt("id");
                posts.add(new Post(title, body, userId, id));
            }
            return posts;
        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
