package com.javai.app.util;

import com.javai.app.dao.ClienteDAO;
import com.javai.app.model.Cliente;
import com.javai.app.services.Redis;

import org.springframework.beans.factory.annotation.Autowired;

public class ClienteUtil {
    Redis redis = new Redis();

    @Autowired
    private ClienteDAO dao;

    public Cliente getCliente(int id) {
        Cliente cliente;

        if (redis.read("id") != null) {
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

            cliente = dao.findById(id);
            System.out.println(cliente.getEmail());

            redis.write("id", cliente.getId().toString(), 30);
            redis.write("nome", cliente.getNome(), 30);
            redis.write("email", cliente.getEmail(), 30);
        }

        return cliente;
    }
}
