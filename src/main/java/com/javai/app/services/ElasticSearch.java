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

public class ElasticSearch {
    public static void get() {
        RestClient restClient = RestClient.builder(
                new HttpHost("localhost", 9200)).build();

        // Create the transport with a Jackson mapper
        ElasticsearchTransport transport = new RestClientTransport(
                restClient, new JacksonJsonpMapper());

        // And create the API client
        ElasticsearchClient client = new ElasticsearchClient(transport);

        // SearchResponse<Product> search = client.search(s -> s
        // .index("products")
        // .query(q -> q
        // .term(t -> t
        // .field("name")
        // .value(v -> v.stringValue("banana")))),
        // Product.class);

        try {
            SearchResponse<Produto> search = client.search(s -> s.index("produtos"), Produto.class);

            for (Hit<Produto> hit : search.hits().hits()) {
                processarProduto(hit.source());
            }
        } catch (ElasticsearchException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static void processarProduto(Produto produto) {
        System.out.println("-------------------------------");
        System.out.println("Nome: " + produto.getNome());
        System.out.println("Descricao: " + produto.getDescricao());
        System.out.println("Preco: " + produto.getPreco());
        System.out.println("-------------------------------");
    }
}
