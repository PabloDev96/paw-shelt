package com.pawshelt.controller;

import com.pawshelt.dto.GraficosRespuestaDTO;
import com.pawshelt.service.GraficosService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/graficos")
@RequiredArgsConstructor
public class GraficosController {

    private final GraficosService service;

    @GetMapping
    public GraficosRespuestaDTO obtener(@RequestParam String periodo) {
        return service.obtener(periodo); // "semana" | "mes" | "anio"
    }
}
