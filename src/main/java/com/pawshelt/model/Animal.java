package com.pawshelt.model;

import jakarta.persistence.*;

@Entity
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String raza;
    private Integer edadCantidad;
    private String fotoPerfilUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "unidad_edad")
    private UnidadEdad unidadEdad; // ANIOS o MESES

    @Enumerated(EnumType.STRING)
    private TipoAnimal tipo; // GATO, PERRO, etc.

    @Enumerated(EnumType.STRING)
    private EstadoAnimal estado; // EN_ADOPCION, ADOPTADO, etc.

    @Column(length = 1000)
    private String descripcion;

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

    public void setFotoPerfilUrl(String fotoPerfilUrl) {
        this.fotoPerfilUrl = fotoPerfilUrl;
    }

    public String getFotoPerfilUrl() {
        return fotoPerfilUrl;
    }

    public Integer getEdadCantidad() {
        return edadCantidad;
    }

    public void setEdadCantidad(Integer edadCantidad) {
        this.edadCantidad = edadCantidad;
    }

    public UnidadEdad getUnidadEdad() {
        return unidadEdad;
    }

    public void setUnidadEdad(UnidadEdad unidadEdad) {
        this.unidadEdad = unidadEdad;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}