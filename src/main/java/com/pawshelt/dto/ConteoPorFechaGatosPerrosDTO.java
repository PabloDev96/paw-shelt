package com.pawshelt.dto;

/**
 * Punto de serie temporal con desglose por especie.
 * Usado para: nuevosAnimales (gatos vs perros).
 */
public record ConteoPorFechaGatosPerrosDTO(
        String fecha,   // "YYYY-MM-DD" o "YYYY-MM"
        long gatos,
        long perros
) {}
