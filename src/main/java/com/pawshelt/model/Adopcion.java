package com.pawshelt.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Adopcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fechaAdopcion;
    private String observaciones;

    @ManyToOne
    @JoinColumn(name = "animal_id")
    private Animal animal;

    @ManyToOne
    @JoinColumn(name = "persona_adoptante_id")
    private PersonaAdoptante personaAdoptante;

    public Adopcion() {}

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

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public PersonaAdoptante getPersonaAdoptante() {
        return personaAdoptante;
    }

    public void setPersonaAdoptante(PersonaAdoptante personaAdoptante) {
        this.personaAdoptante = personaAdoptante;
    }

}
