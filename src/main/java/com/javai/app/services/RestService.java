package com.javai.app.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.javai.app.model.ElasticRequestList;
import com.javai.app.model.Produto;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

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

    public static List<Produto> getList() throws IOException {
        HttpGet get = new HttpGet("http://localhost:9200/produtos/_search");
        HttpClientBuilder clientBuilder = HttpClientBuilder.create();

        CloseableHttpClient client = clientBuilder.build();
        get.addHeader("Content-Type", "application/json");
        get.addHeader("Accept", "text/plain");

        CloseableHttpResponse response = client.execute(get);

        String responseAsString = EntityUtils.toString(response.getEntity());

        ElasticRequestList elasticRequestList = new Gson().fromJson(responseAsString, ElasticRequestList.class);

        List<Produto> produtos = new ArrayList<>();

        List<LinkedTreeMap> hits = (List<LinkedTreeMap>) ((LinkedTreeMap) elasticRequestList.get_hits()).get("hits");

        for (LinkedTreeMap hit : hits) {
            String produto_json = new Gson().toJson(hit.get("_source"));
            Produto produto = new Gson().fromJson(produto_json, Produto.class);
            produtos.add(produto);
        }

        System.out.println(produtos);
        return produtos;
    }
}
