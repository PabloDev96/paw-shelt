package com.pawshelt.dto;

import com.pawshelt.model.EstadoAnimal;
import com.pawshelt.model.Sexo;
import com.pawshelt.model.TipoAnimal;
import com.pawshelt.model.UnidadEdad;
import java.time.LocalDate;

public class AnimalDTO {

    private Long id;
    private String nombre;
    private String raza;
    private Integer edadCantidad;
    private UnidadEdad unidadEdad;
    private TipoAnimal tipo;
    private EstadoAnimal estado;
    private Sexo sexo;
    private String descripcion;
    private String fotoPerfilUrl;
    private LocalDate fechaIngreso;

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

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFotoPerfilUrl() {
        return fotoPerfilUrl;
    }

    public void setFotoPerfilUrl(String fotoPerfilUrl) {
        this.fotoPerfilUrl = fotoPerfilUrl;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }
}
