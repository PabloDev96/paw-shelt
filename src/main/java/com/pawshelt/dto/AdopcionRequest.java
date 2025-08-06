package com.pawshelt.dto;

import java.time.LocalDate;

public class AdopcionRequest {
    private Long animalId;
    private Long personaAdoptanteId;
    private LocalDate fechaAdopcion;
    private String observaciones;

    public Long getAnimalId() {
        return animalId;
    }

    public void setAnimalId(Long animalId) {
        this.animalId = animalId;
    }

    public Long getPersonaAdoptanteId() {
        return personaAdoptanteId;
    }

    public void setPersonaAdoptanteId(Long personaAdoptanteId) {
        this.personaAdoptanteId = personaAdoptanteId;
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
}
