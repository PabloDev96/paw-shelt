package com.pawshelt.dto;

import java.time.LocalDate;

public class AdopcionDTO {
    private Long id;
    private LocalDate fechaAdopcion;
    private String observaciones;
    private String nombreAnimal;
    private String nombreAdoptante;

    // Constructor
    public AdopcionDTO(Long id, LocalDate fechaAdopcion, String observaciones, String nombreAnimal, String nombreAdoptante) {
        this.id = id;
        this.fechaAdopcion = fechaAdopcion;
        this.observaciones = observaciones;
        this.nombreAnimal = nombreAnimal;
        this.nombreAdoptante = nombreAdoptante;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public LocalDate getFechaAdopcion() {
        return fechaAdopcion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public String getNombreAnimal() {
        return nombreAnimal;
    }

    public String getNombreAdoptante() {
        return nombreAdoptante;
    }
}
