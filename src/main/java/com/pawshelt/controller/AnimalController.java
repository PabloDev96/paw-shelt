package com.pawshelt.controller;

import com.pawshelt.model.Animal;
import com.pawshelt.repository.AnimalRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/animales")
public class AnimalController {

    private final AnimalRepository repository;

    public AnimalController(AnimalRepository repository) {
        this.repository = repository;
    }

    /* METODO PARA OBTENER TODOS LOS DATOS DE LA ENTIDAD ANIMAL */
    @GetMapping
    public List<Animal> getAllAnimales() {
        return repository.findAll();
    }

    /* METODO PARA CREAR UN ANIMAL */
    @PostMapping
    public Animal crearAnimal(@RequestBody Animal animal) {
        return repository.save(animal);
    }

    /* METODO PARA ELIMINAR UN ANIMAL */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAnimal(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            return ResponseEntity.notFound().build();  // 404 Not Found
        }
    }

    /* METODO PARA EDITAR UN ANIMAL POR ID */
    @PutMapping("/{id}")
    public ResponseEntity<Animal> actualizarAnimal(@PathVariable Long id, @RequestBody Animal animalActualizado) {
        return repository.findById(id)
                .map(animal -> {
                    animal.setNombre(animalActualizado.getNombre());
                    animal.setRaza(animalActualizado.getRaza());
                    animal.setEdadCantidad(animalActualizado.getEdadCantidad());
                    animal.setUnidadEdad(animalActualizado.getUnidadEdad());
                    animal.setTipo(animalActualizado.getTipo());
                    animal.setEstado(animalActualizado.getEstado());
                    animal.setFotoPerfilUrl(animalActualizado.getFotoPerfilUrl());
                    animal.setDescripcion(animalActualizado.getDescripcion());
                    Animal actualizado = repository.save(animal);
                    return ResponseEntity.ok(actualizado);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    /* METODO PARA OBTENER TODOS LOS DATOS DE UN ANIMAL POR ID */
    @GetMapping("/{id}")
    public ResponseEntity<Animal> getAnimalPorId(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /* METODO PARA FILTRAR ANIMALES POR TIPO (GATO, PERRO, ETC.) */
    @GetMapping("/tipo/{tipo}")
    public List<Animal> getAnimalesPorTipo(@PathVariable String tipo) {
        try {
            return repository.findByTipo(Enum.valueOf(com.pawshelt.model.TipoAnimal.class, tipo.toUpperCase()));
        } catch (IllegalArgumentException e) {
            return List.of(); // Devuelve una lista vacía si el tipo no es válido
        }
    }

}