package com.javai.app.controller;

import com.javai.app.dao.ClienteDAO;
import com.javai.app.model.Cliente;
import com.javai.app.services.Redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import redis.clients.jedis.Jedis;

@RestController
public class ClienteController {
    @Autowired
    private ClienteDAO dao;

    @PostMapping("/clientes")
    public void cadastrarCliente(@RequestBody Cliente cliente) {
        dao.save(cliente);
        // var jedis = new Jedis("http://localhost:6379");

        Redis redis = new Redis();
        // redis
        // jedis.hset("cliente", "nome", cliente.getNome());
        // jedis.hset("cliente", "email", cliente.getEmail());

        String key = "cliente";
        String email = cliente.getEmail();

        redis.write(key, email, 60);

        String value = redis.read(key);
        System.out.println("Lendo valor do Cache: " + value);

        redis.close();
    }
}