package com.pawshelt.controller;

import com.pawshelt.dto.AdopcionCompletoDTO;
import com.pawshelt.dto.AdopcionDTO;
import com.pawshelt.dto.AdopcionRequest;
import com.pawshelt.service.AdopcionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/adopciones")
@CrossOrigin(origins = "*") // Asegura acceso desde React
public class AdopcionController {

    @Autowired
    private AdopcionService adopcionService;

    @GetMapping
    public List<AdopcionCompletoDTO> listarAdopciones() {
        return adopcionService.obtenerTodasLasAdopcionesConDetalle();
    }


    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> crearAdopcion(@RequestBody AdopcionRequest request) {
        try {
            adopcionService.crearAdopcion(request);
            return ResponseEntity.ok("Adopción registrada con éxito");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al registrar la adopción: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminarAdopcion(@PathVariable Long id) {
        boolean eliminado = adopcionService.eliminarAdopcion(id);
        return eliminado ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
