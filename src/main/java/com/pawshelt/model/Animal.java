package com.pawshelt.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "animal")
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String raza;
    private Integer edadCantidad;

    @Column(name = "foto_perfil_url")
    private String fotoPerfilUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "unidad_edad")
    private UnidadEdad unidadEdad; // ANIOS o MESES

    @Enumerated(EnumType.STRING)
    private TipoAnimal tipo; // GATO, PERRO, etc.

    @Enumerated(EnumType.STRING)
    private EstadoAnimal estado; // EN_ADOPCION, ADOPTADO, etc.

    @Enumerated(EnumType.STRING)
    private Sexo sexo; // MACHO o HEMBRA

    @Column(length = 1000)
    private String descripcion;

    @Column(name = "fecha_ingreso", nullable = false, updatable = false)
    private LocalDate fechaIngreso;

    public Animal() {}

    /* lifecycle: autocompletar fechaIngreso */
    @PrePersist
    protected void onCreate() {
        if (fechaIngreso == null) {
            fechaIngreso = LocalDate.now();
        }
    }

    /* getters/setters */

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getRaza() { return raza; }
    public void setRaza(String raza) { this.raza = raza; }

    public Integer getEdadCantidad() { return edadCantidad; }
    public void setEdadCantidad(Integer edadCantidad) { this.edadCantidad = edadCantidad; }

    public String getFotoPerfilUrl() { return fotoPerfilUrl; }
    public void setFotoPerfilUrl(String fotoPerfilUrl) { this.fotoPerfilUrl = fotoPerfilUrl; }

    public UnidadEdad getUnidadEdad() { return unidadEdad; }
    public void setUnidadEdad(UnidadEdad unidadEdad) { this.unidadEdad = unidadEdad; }

    public TipoAnimal getTipo() { return tipo; }
    public void setTipo(TipoAnimal tipo) { this.tipo = tipo; }

    public EstadoAnimal getEstado() { return estado; }
    public void setEstado(EstadoAnimal estado) { this.estado = estado; }

    public Sexo getSexo() { return sexo; }
    public void setSexo(Sexo sexo) { this.sexo = sexo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public LocalDate getFechaIngreso() { return fechaIngreso; }
    public void setFechaIngreso(LocalDate fechaIngreso) { this.fechaIngreso = fechaIngreso; }
}
