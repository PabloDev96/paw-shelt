package com.pawshelt.controller;

import com.pawshelt.dto.AnimalDTO;
import com.pawshelt.model.Animal;
import com.pawshelt.repository.AnimalRepository;
import com.pawshelt.service.CloudinaryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/animales")
public class AnimalController {

    private final AnimalRepository repository;
    private final CloudinaryService cloudinaryService;

    public AnimalController(AnimalRepository repository, CloudinaryService cloudinaryService) {
        this.repository = repository;
        this.cloudinaryService = cloudinaryService;
    }

    /* ===== LISTADOS ===== */

    @GetMapping
    public List<AnimalDTO> getAllAnimales() {
        return repository.findAll().stream().map(this::toDTO).toList();
    }

    @GetMapping("/disponibles-para-adopcion")
    public List<AnimalDTO> getAnimalesDisponiblesParaAdopcion() {
        return repository.findAll().stream()
                .filter(a -> a.getEstado() == com.pawshelt.model.EstadoAnimal.EN_ADOPCION
                        || a.getEstado() == com.pawshelt.model.EstadoAnimal.EN_CASA_DE_ACOGIDA)
                .map(this::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnimalDTO> getAnimalPorId(@PathVariable Long id) {
        return repository.findById(id)
                .map(this::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/tipo/{tipo}")
    public List<AnimalDTO> getAnimalesPorTipo(@PathVariable String tipo) {
        try {
            return repository.findByTipo(Enum.valueOf(com.pawshelt.model.TipoAnimal.class, tipo.toUpperCase()))
                    .stream().map(this::toDTO).toList();
        } catch (IllegalArgumentException e) {
            return List.of();
        }
    }

    /* ===== CREACIÓN ===== */

    /** A) Crear SOLO con JSON (compatibilidad con tu front actual) */
    @PostMapping(consumes = "application/json")
    public AnimalDTO crearAnimalJson(@RequestBody AnimalDTO animalDTO) {
        Animal animal = repository.save(toEntity(animalDTO));
        return toDTO(animal);
    }

    /** B) Crear con JSON + FOTO (multipart) */
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<AnimalDTO> crearAnimalMultipart(
            @RequestPart("animal") AnimalDTO animalDTO,
            @RequestPart(value = "foto", required = false) MultipartFile foto
    ) throws IOException {
        // 1) Guardamos el animal para obtener ID
        Animal a = repository.save(toEntity(animalDTO));

        // 2) Si viene foto, la subimos a Cloudinary y guardamos la URL
        if (foto != null && !foto.isEmpty()) {
            String url = cloudinaryService.uploadAnimalPhoto(a.getId(), foto);
            a.setFotoPerfilUrl(url);
            a = repository.save(a);
        }

        return ResponseEntity.ok(toDTO(a));
    }

    /* ===== ACTUALIZACIÓN ===== */

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

    /** Subir/actualizar SOLO la foto de un animal existente */
    @PutMapping(value = "/{id}/foto", consumes = "multipart/form-data")
    public ResponseEntity<AnimalDTO> actualizarFoto(
            @PathVariable Long id,
            @RequestPart("foto") MultipartFile foto
    ) throws IOException {
        Animal a = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Animal no encontrado"));

        String url = cloudinaryService.uploadAnimalPhoto(a.getId(), foto);
        a.setFotoPerfilUrl(url);
        a = repository.save(a);

        return ResponseEntity.ok(toDTO(a));
    }

    /* ===== ELIMINACIÓN ===== */

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAnimal(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /* ===== MAPEOS ===== */

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
