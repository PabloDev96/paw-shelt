package com.pawshelt.controller;

import com.pawshelt.dto.CitaDTO;
import com.pawshelt.service.CitaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/citas")
@CrossOrigin(origins = "http://localhost:5173")
public class CitaController {

    private final CitaService citaService;

    public CitaController(CitaService citaService) {
        this.citaService = citaService;
    }

    @GetMapping
    public List<CitaDTO> obtenerTodas() {
        return citaService.obtenerTodas();
    }

    @GetMapping("/{id}")
    public CitaDTO obtenerPorId(@PathVariable Long id) {
        return citaService.obtenerPorId(id);
    }

    @PostMapping
    public CitaDTO crear(@RequestBody CitaDTO dto) {
        return citaService.crear(dto);
    }

    @PutMapping("/{id}")
    public CitaDTO actualizar(@PathVariable Long id, @RequestBody CitaDTO dto) {
        return citaService.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        citaService.eliminar(id);
    }
}
