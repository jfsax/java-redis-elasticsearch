package com.javai.app.dao;

import com.javai.app.model.Produto;

import org.springframework.data.repository.CrudRepository;

public interface ProdutoDAO extends CrudRepository<Produto, Integer> {

}
