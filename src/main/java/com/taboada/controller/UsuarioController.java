package com.taboada.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taboada.model.Usuario;
import com.taboada.repository.UsuarioRepository;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevoUsuario(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "formulario-usuario";
    }

    @PostMapping("/crear")
    public String crearUsuario(@ModelAttribute Usuario usuario) {
        usuarioRepository.save(usuario); // Guarda el nuevo usuario en la base de datos
        return "redirect:/usuarios/lista";
    }
}