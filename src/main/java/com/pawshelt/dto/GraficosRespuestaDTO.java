package com.pawshelt.dto;

import java.util.List;

/**
 * Respuesta completa para /graficos?periodo=...
 * Estructura exacta que espera el frontend.
 */
public record GraficosRespuestaDTO(
        List<ConteoPorFechaDTO> adopciones,
        List<ConteoPorFechaDTO> citas,
        List<ConteoPorFechaGatosPerrosDTO> nuevosAnimales,
        List<ConteoPorCategoriaDTO> edades,
        List<ConteoPorCategoriaDTO> sexo,
        List<ConteoPorCategoriaDTO> especies
) {}
