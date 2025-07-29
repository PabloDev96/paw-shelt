package com.pawshelt.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private LocalDateTime fechaHoraInicio;
    private LocalDateTime fechaHoraFin;
    private String descripcion;

    @Enumerated(EnumType.STRING)
    private TipoCita tipo;

    @ManyToOne
    @JoinColumn(name = "persona_adoptante_id")
    private PersonaAdoptante personaAdoptante;

    public Cita() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public LocalDateTime getFechaHoraInicio() {
        return fechaHoraInicio;
    }

    public void setFechaHoraInicio(LocalDateTime fechaHoraInicio) {
        this.fechaHoraInicio = fechaHoraInicio;
    }

    public LocalDateTime getFechaHoraFin() {
        return fechaHoraFin;
    }

    public void setFechaHoraFin(LocalDateTime fechaHoraFin) {
        this.fechaHoraFin = fechaHoraFin;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public TipoCita getTipo() {
        return tipo;
    }

    public void setTipo(TipoCita tipo) {
        this.tipo = tipo;
    }

    public PersonaAdoptante getPersonaAdoptante() {
        return personaAdoptante;
    }

    public void setPersonaAdoptante(PersonaAdoptante personaAdoptante) {
        this.personaAdoptante = personaAdoptante;
    }

}
