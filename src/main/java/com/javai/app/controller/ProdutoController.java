package com.javai.app.controller;

import java.io.IOException;

import com.javai.app.dao.ProdutoDAO;
import com.javai.app.model.Produto;
import com.javai.app.services.RestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
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

    @GetMapping("/cadastro_produto")
    public String cadastroProduto(Produto produto) {
        return "cadastro_produto";
    }

    @PostMapping("/produtos")
    public String cadastrarProduto(@Validated Produto produto, BindingResult result, Model model) throws IOException {
        if (result.hasErrors() || produto.getNome().isEmpty()) {
            return "cadastro_produto";
        }

        dao.save(produto);
        RestService.post(produto);

        return "redirect:/produtos";
    }

    @GetMapping("/produtos")
    public String buscarProdutos(Model model) {
        
        model.addAttribute("produtos", dao.findAll());
        return "produtos";
    }
}
