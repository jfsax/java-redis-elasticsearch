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
    public String buscarCliente(@PathVariable int id, Model model) {
        Cliente cliente;
        String idCliente = "cliente_id_" + id;
        String nomeCliente = "cliente_nome_" + id;
        String emailCliente = "cliente_email_" + id;

        try {
            if (redis.read(idCliente) != null) {
                cliente = new Cliente();
                cliente.setId(Integer.parseInt(redis.read(idCliente)));
                cliente.setNome(redis.read(nomeCliente));
                cliente.setEmail(redis.read(emailCliente));
            } else {
                cliente = dao.findById(id);

                redis.write(idCliente, cliente.getId().toString(), 30);
                redis.write(nomeCliente, cliente.getNome(), 30);
                redis.write(emailCliente, cliente.getEmail(), 30);
            }

            model.addAttribute("cliente", cliente);
            return "cliente";
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
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
        if (result.hasErrors() || cliente.getEmail().isEmpty()) {
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