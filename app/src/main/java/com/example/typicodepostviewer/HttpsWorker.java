package com.example.typicodepostviewer;

import android.net.http.HttpResponseCache;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.Callable;

import javax.net.ssl.HttpsURLConnection;

// @TODO: HttpsWorker should only download response from ANY given url, to make it more flexible

// @TODO: I think the boolean forceNetworkResponse should not be there
// HttpsWorker should return the downloaded response, nothing more, nothing less.
// To enable that functionality, pass it the HttpsURLConnection and modify request properties
// if needed there
public class HttpsWorker implements Callable<String> {
    private final String mUrlAddress;

    HttpsWorker(String url) {
        mUrlAddress = url;
    }

    @Override
    public String call() throws Exception {
        // Request API call
        URL url = new URL(mUrlAddress);
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        // use cache if possible
        connection.connect();

        // Read response
        InputStream in = new BufferedInputStream(connection.getInputStream());
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder response = new StringBuilder();
        String line = "";
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }

        // free resources
        connection.disconnect();
        reader.close();

        // return response
        return response.toString();
    }
}
