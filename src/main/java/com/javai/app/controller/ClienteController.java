package com.javai.app.controller;

import com.javai.app.dao.ClienteDAO;
import com.javai.app.model.Cliente;

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
        // redis
        var jedis = new Jedis("http://localhost:6379");
        jedis.hset("cliente", "nome", cliente.getNome());
        jedis.hset("cliente", "email", cliente.getEmail());
    }
}