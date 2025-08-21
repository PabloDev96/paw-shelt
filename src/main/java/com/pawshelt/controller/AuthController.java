package com.pawshelt.controller;

import com.pawshelt.config.JwtUtil;
import com.pawshelt.model.LoginRequest;
import com.pawshelt.model.Usuario;
import com.pawshelt.repository.UsuarioRepository;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthController(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // 🔐 LOGIN
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findByEmail(request.getEmail());

        if (optionalUsuario.isEmpty()) {
            return ResponseEntity.status(401).body("Email no registrado.");
        }

        Usuario usuario = optionalUsuario.get();

        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            return ResponseEntity.status(401).body("Contraseña incorrecta.");
        }

        String token = jwtUtil.generateToken(usuario);

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("nombre", usuario.getNombre());
        response.put("rol", usuario.getRol().toString());

        return ResponseEntity.ok(response);
    }

    // 📝 REGISTRO
    @PermitAll
    @PostMapping("/register")
    public ResponseEntity<?> registrar(@RequestBody Usuario usuario) {

        // Validación básica de campos
        if (usuario.getNombre() == null || usuario.getNombre().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("El nombre es obligatorio.");
        }

        if (usuario.getEmail() == null || !usuario.getEmail().matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            return ResponseEntity.badRequest().body("El correo electrónico no es válido.");
        }

        if (usuario.getPassword() == null ||
                !usuario.getPassword().matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")) {
            return ResponseEntity.badRequest().body("La contraseña debe tener al menos 8 caracteres, incluyendo letras y números.");
        }

        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("El email ya está registrado.");
        }

        // Hashear la contraseña antes de guardar
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        Usuario guardado = usuarioRepository.save(usuario);
        return ResponseEntity.ok(guardado);
    }
}
