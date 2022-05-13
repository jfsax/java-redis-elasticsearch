package com.javai.app.services;

import java.io.IOException;
import java.util.TimeZone;

import com.google.gson.Gson;
import com.javai.app.model.Produto;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

public class RestService {
    public static void post(Produto produto) throws IOException {
        String jsonFile = new Gson().toJson(produto);

        HttpEntity entity = new StringEntity(jsonFile);
        HttpPost post = new HttpPost("http://localhost:9200/produtos/_doc");
        post.setEntity(entity);

        HttpClientBuilder clientBuilder = HttpClientBuilder.create();
        CloseableHttpClient client = clientBuilder.build();

        post.addHeader("Content-Type", "application/json");
        post.addHeader("Accept", "text/plain");

        CloseableHttpResponse response = client.execute(post);
        System.out.println("Response: " + response);
    }
}
