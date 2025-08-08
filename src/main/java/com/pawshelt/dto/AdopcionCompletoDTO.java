package com.pawshelt.dto;

import java.time.LocalDate;

public class AdopcionCompletoDTO {

    private Long id;
    private LocalDate fechaAdopcion;
    private String observaciones;
    private AnimalDTO animal;
    private PersonaAdoptanteDTO adoptante;

    public AdopcionCompletoDTO(Long id, LocalDate fechaAdopcion, String observaciones, AnimalDTO animal, PersonaAdoptanteDTO adoptante) {
        this.id = id;
        this.fechaAdopcion = fechaAdopcion;
        this.observaciones = observaciones;
        this.animal = animal;
        this.adoptante = adoptante;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFechaAdopcion() {
        return fechaAdopcion;
    }

    public void setFechaAdopcion(LocalDate fechaAdopcion) {
        this.fechaAdopcion = fechaAdopcion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public AnimalDTO getAnimal() {
        return animal;
    }

    public void setAnimal(AnimalDTO animal) {
        this.animal = animal;
    }

    public PersonaAdoptanteDTO getAdoptante() {
        return adoptante;
    }

    public void setAdoptante(PersonaAdoptanteDTO adoptante) {
        this.adoptante = adoptante;
    }
}
