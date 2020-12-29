package com.example.typicodepostviewer;

import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.Assert.*;

public class HttpsWorkerTest {
    @Test
    public void call_ReturnsResponse() throws ExecutionException, InterruptedException {
        final String url = "https://jsonplaceholder.typicode.com/posts/1";
        final String expected = "{  \"userId\": 1,  \"id\": 1,  \"title\": \"sunt aut facere repellat provident occaecati excepturi optio reprehenderit\",  \"body\": \"quia et suscipit\\nsuscipit recusandae consequuntur expedita et cum\\nreprehenderit molestiae ut ut quas totam\\nnostrum rerum est autem sunt rem eveniet architecto\"}";

        ExecutorService es = Executors.newFixedThreadPool(1);
        Future<String> fRes = es.submit(new HttpsWorker(url));
        String res = fRes.get();

        assertEquals(expected, res);
    }
}