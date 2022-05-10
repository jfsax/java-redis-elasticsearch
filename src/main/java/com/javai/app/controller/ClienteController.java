package com.javai.app.controller;

import java.util.List;

import com.javai.app.dao.ClienteDAO;
import com.javai.app.model.Cliente;
import com.javai.app.services.Redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClienteController {
    @Autowired
    private ClienteDAO dao;

    Redis redis = new Redis();

    @GetMapping("/clientes/{email}")
    public ResponseEntity<?> buscarClientes(@PathVariable String email) {
        var key = "cliente";
        var cliente = redis.read(key) != null ? redis.read(key) : dao.findByEmail(email);

        return ResponseEntity.ok(cliente);
    }

    @PostMapping("/clientes")
    public ResponseEntity<?> cadastrarCliente(@RequestBody Cliente cliente) {
        dao.save(cliente);

        String key = "cliente";
        String email = cliente.getEmail();

        redis.write(key, email, 120);

        String value = redis.read(key);
        System.out.println("Lendo valor do Cache: " + value);

        redis.close();
        return ResponseEntity.ok(cliente);
    }
}