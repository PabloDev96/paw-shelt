package com.pawshelt.controller;

import com.pawshelt.repository.UsuarioRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioRepository repository;

    public UsuarioController(UsuarioRepository repository) {
        this.repository = repository;
    }

    // Futuros métodos (getAllUsuarios, updateUsuario, etc.)
}
