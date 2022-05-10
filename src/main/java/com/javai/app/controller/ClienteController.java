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
        Cliente cliente;

        if (redis.read("email") != null) {
            System.out.println("from redis");

            cliente = new Cliente();

            cliente.setId(Integer.parseInt(redis.read("id")));
            cliente.setNome(redis.read("nome"));
            cliente.setEmail(redis.read("email"));

            System.out.println("ID: " + cliente.getId() + "\n"
                    + "Nome: " + cliente.getNome() + "\n"
                    + "Email: " + cliente.getEmail() + "\n");
        } else {
            System.out.println("from bd");

            cliente = dao.findByEmail(email);

            redis.write("id", cliente.getId().toString(), 120);
            redis.write("nome", cliente.getNome(), 120);
            redis.write("email", cliente.getEmail(), 120);
        }

        return ResponseEntity.ok(cliente);
    }

    @PostMapping("/clientes")
    public ResponseEntity<?> cadastrarCliente(@RequestBody Cliente c) {
        Cliente cliente = dao.save(c);

        // String key = "cliente";
        // String email = cliente.getEmail();

        // redis.write(key, email, 120);

        // String value = redis.read(key);
        // System.out.println("Lendo valor do Cache: " + value);
        return ResponseEntity.ok(cliente);
    }
}