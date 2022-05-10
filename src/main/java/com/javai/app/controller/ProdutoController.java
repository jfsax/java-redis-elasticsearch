package com.javai.app.controller;

import com.javai.app.dao.ProdutoDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

public class ProdutoController {
    @Autowired
    private ProdutoDAO dao;
    // TODO: elasticsearch
    // post - cadastrar em postgres e elasticsearch

    /*
     * get busca e nome
     * if (busca == postgres) ajsdsadsa
     * if (busca == elasticsearch) ajsdsadsa
     */

    // @GetMapping("/produtos")
    // public String showUserList(Produto produto) {
    // produto.addAttribute("users", userRepository.findAll());
    // return "index";
    // }
}
