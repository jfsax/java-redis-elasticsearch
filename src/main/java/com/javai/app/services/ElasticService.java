package com.javai.app.services;

import java.io.IOException;

import com.javai.app.model.Produto;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.ElasticsearchException;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;

public class ElasticService {
    public static void get() throws ElasticsearchException, IOException {
        RestClient restClient = RestClient.builder(
                new HttpHost("localhost", 9200)).build();

        var restClientTransport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        ElasticsearchTransport transport = restClientTransport;

        ElasticsearchClient client = new ElasticsearchClient(transport);

        // SearchResponse<Product> search = client.search(s -> s
        // .index("products")
        // .query(q -> q
        // .term(t -> t
        // .field("name")
        // .value(v -> v.stringValue("banana"))
        // )),
        // Product.class);

        SearchResponse<Produto> search = client.search(s -> s.index("produtos"), Produto.class);

        for (Hit<Produto> hit : search.hits().hits()) {
            processarProduto(hit.source());
        }
    }

    private static void processarProduto(Produto produto) {
        System.out.println("-------------------------------");
        System.out.println("Nome: " + produto.getNome());
        System.out.println("Descricao: " + produto.getDescricao());
        System.out.println("-------------------------------");
    }
}
