package com.javai.app.controller;

import com.javai.app.dao.ClienteDAO;
import com.javai.app.model.Cliente;
import com.javai.app.services.Redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ClienteController {
    @Autowired
    private ClienteDAO dao;

    // TODO: arrumar essa bagunca sos

    Redis redis = new Redis();

    @GetMapping("/cliente/{id}")
    public String buscarCliente(@PathVariable String id, Model model) {
        Cliente cliente;

        try {
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

                cliente = dao.findById(Integer.parseInt(id));

                redis.write("id", cliente.getId().toString(), 120);
                redis.write("nome", cliente.getNome(), 120);
                redis.write("email", cliente.getEmail(), 120);
            }

            model.addAttribute("cliente", cliente);
            return "cliente";
        } catch (Exception ex) {
            return "redirect:/error";
        }
    }

    @GetMapping("/cadastro")
    public String cadastro(Cliente cliente) {
        return "cadastro";
    }

    @GetMapping("/clientes")
    public String exibirClientes(Model model) {
        model.addAttribute("clientes", dao.findAll());
        return "clientes";
    }

    @PostMapping("/clientes")
    public String cadastrarCliente(@Validated Cliente cliente, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "cadastro";
        }

        dao.save(cliente);

        return "redirect:/clientes";
    }

    @GetMapping("/delete/{id}")
    public String excluirCliente(@PathVariable("id") Integer id, Model model) {
        Cliente cliente = dao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

        dao.delete(cliente);

        return "redirect:/clientes";
    };
}