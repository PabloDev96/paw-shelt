package com.pawshelt.controller;

import com.pawshelt.model.PersonaAdoptante;
import com.pawshelt.service.PersonaAdoptanteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/adoptantes")
@CrossOrigin(origins = "http://localhost:5173") // si usas frontend local
public class PersonaAdoptanteController {

    private final PersonaAdoptanteService service;

    public PersonaAdoptanteController(PersonaAdoptanteService service) {
        this.service = service;
    }

    @GetMapping
    public List<PersonaAdoptante> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public PersonaAdoptante obtener(@PathVariable Long id) {
        return service.obtenerPorId(id);
    }

    @PostMapping
    public PersonaAdoptante crear(@RequestBody PersonaAdoptante p) {
        return service.crear(p);
    }

    @PutMapping("/{id}")
    public PersonaAdoptante actualizar(@PathVariable Long id, @RequestBody PersonaAdoptante p) {
        return service.actualizar(id, p);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}
