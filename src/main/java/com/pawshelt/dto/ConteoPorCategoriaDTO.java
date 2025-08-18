package com.pawshelt.dto;

/**
 * Par (categoría, cantidad).
 * Usado para: edades (buckets), sexo, especies.
 */
public record ConteoPorCategoriaDTO(
        String categoria,  // p.ej. "0-1", "Macho", "Gatos"
        long cantidad
) {}
