package com.javai.app.dao;

import com.javai.app.model.Cliente;

import org.springframework.data.repository.CrudRepository;

public interface ClienteDAO extends CrudRepository<Cliente, Integer> {
    public Cliente findByEmail(String email);
}
