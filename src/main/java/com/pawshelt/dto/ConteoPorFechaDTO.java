package com.pawshelt.dto;

/**
 * Punto de serie temporal: una fecha y un conteo.
 * Usado para: adopciones, citas.
 */
public record ConteoPorFechaDTO(
        String fecha,   // "YYYY-MM-DD" o "YYYY-MM"
        long cantidad
) {}
