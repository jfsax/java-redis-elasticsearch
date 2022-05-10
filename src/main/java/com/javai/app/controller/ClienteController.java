package com.javai.app.controller;

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
        String key = "cliente";

        Cliente cliente;

        if (redis.read(key) != null) {
            cliente = new Cliente();
            cliente.setEmail(redis.read(key));
        } else {
            cliente = dao.findByEmail(email);

            redis.write(key, cliente.getEmail(), 120);
        }

        // redis retorna apenas o email por enquanto

        return ResponseEntity.ok(cliente);
    }

    @PostMapping("/clientes")
    public ResponseEntity<?> cadastrarCliente(@RequestBody Cliente cliente) {
        dao.save(cliente);

        // String key = "cliente";
        // String email = cliente.getEmail();

        // redis.write(key, email, 120);

        // String value = redis.read(key);
        // System.out.println("Lendo valor do Cache: " + value);
        return ResponseEntity.ok(cliente);
    }
}