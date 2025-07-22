package com.pawshelt.model;

import jakarta.persistence.*;

@Entity
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String raza;
    private Integer edad;

    @Enumerated(EnumType.STRING)
    private TipoAnimal tipo; // GATO, PERRO, etc.

    @Enumerated(EnumType.STRING)
    private EstadoAnimal estado; // EN_ADOPCION, ADOPTADO, etc.

    private String fotoPerfilUrl;

    // Constructor vacío (obligatorio para JPA)
    public Animal() {}

    // Getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setFotoPerfilUrl(String raza) {
        this.fotoPerfilUrl = fotoPerfilUrl;
    }

    public String getFotoPerfilUrl() {
        return fotoPerfilUrl;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public TipoAnimal getTipo() {
        return tipo;
    }

    public void setTipo(TipoAnimal tipo) {
        this.tipo = tipo;
    }

    public EstadoAnimal getEstado() {
        return estado;
    }

    public void setEstado(EstadoAnimal estado) {
        this.estado = estado;
    }
}