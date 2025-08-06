package com.pawshelt.controller;

import com.pawshelt.dto.AnimalDTO;
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
    public List<AnimalDTO> getAllAnimales() {
        return repository.findAll().stream().map(this::toDTO).toList();
    }

    /* METODO PARA OBTENER ANIMALES DISPONIBLES PARA ADOPCIÓN */
    @GetMapping("/disponibles-para-adopcion")
    public List<AnimalDTO> getAnimalesDisponiblesParaAdopcion() {
        return repository.findAll().stream()
                .filter(a -> a.getEstado() == com.pawshelt.model.EstadoAnimal.EN_ADOPCION
                        || a.getEstado() == com.pawshelt.model.EstadoAnimal.EN_CASA_DE_ACOGIDA)
                .map(this::toDTO)
                .toList();
    }

    /* METODO PARA CREAR UN ANIMAL */
    @PostMapping
    public AnimalDTO crearAnimal(@RequestBody AnimalDTO animalDTO) {
        Animal animal = toEntity(animalDTO);
        return toDTO(repository.save(animal));
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
    public ResponseEntity<AnimalDTO> actualizarAnimal(@PathVariable Long id, @RequestBody AnimalDTO animalDTO) {
        return repository.findById(id)
                .map(animal -> {
                    animal.setNombre(animalDTO.getNombre());
                    animal.setRaza(animalDTO.getRaza());
                    animal.setEdadCantidad(animalDTO.getEdadCantidad());
                    animal.setUnidadEdad(animalDTO.getUnidadEdad());
                    animal.setTipo(animalDTO.getTipo());
                    animal.setEstado(animalDTO.getEstado());
                    animal.setFotoPerfilUrl(animalDTO.getFotoPerfilUrl());
                    animal.setDescripcion(animalDTO.getDescripcion());
                    animal.setSexo(animalDTO.getSexo());
                    Animal actualizado = repository.save(animal);
                    return ResponseEntity.ok(toDTO(actualizado));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    /* METODO PARA OBTENER TODOS LOS DATOS DE UN ANIMAL POR ID */
    @GetMapping("/{id}")
    public ResponseEntity<AnimalDTO> getAnimalPorId(@PathVariable Long id) {
        return repository.findById(id)
                .map(this::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /* METODO PARA FILTRAR ANIMALES POR TIPO (GATO, PERRO, ETC.) */
    @GetMapping("/tipo/{tipo}")
    public List<AnimalDTO> getAnimalesPorTipo(@PathVariable String tipo) {
        try {
            return repository.findByTipo(Enum.valueOf(com.pawshelt.model.TipoAnimal.class, tipo.toUpperCase()))
                    .stream().map(this::toDTO).toList();
        } catch (IllegalArgumentException e) {
            return List.of(); // Devuelve una lista vacía si el tipo no es válido
        }
    }

    /* CONVERSIÓN DE ENTITY A DTO */
    private AnimalDTO toDTO(Animal a) {
        AnimalDTO dto = new AnimalDTO();
        dto.setId(a.getId());
        dto.setNombre(a.getNombre());
        dto.setRaza(a.getRaza());
        dto.setEdadCantidad(a.getEdadCantidad());
        dto.setUnidadEdad(a.getUnidadEdad());
        dto.setTipo(a.getTipo());
        dto.setEstado(a.getEstado());
        dto.setSexo(a.getSexo());
        dto.setDescripcion(a.getDescripcion());
        dto.setFotoPerfilUrl(a.getFotoPerfilUrl());
        return dto;
    }

    /* CONVERSIÓN DE DTO A ENTITY */
    private Animal toEntity(AnimalDTO dto) {
        Animal a = new Animal();
        a.setNombre(dto.getNombre());
        a.setRaza(dto.getRaza());
        a.setEdadCantidad(dto.getEdadCantidad());
        a.setUnidadEdad(dto.getUnidadEdad());
        a.setTipo(dto.getTipo());
        a.setEstado(dto.getEstado());
        a.setSexo(dto.getSexo());
        a.setDescripcion(dto.getDescripcion());
        a.setFotoPerfilUrl(dto.getFotoPerfilUrl());
        return a;
    }
}
