package com.pawshelt.controller;

import com.pawshelt.model.Animal;
import com.pawshelt.repository.AnimalRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/animales")
public class AnimalController {

    private final AnimalRepository repository;

    public AnimalController(AnimalRepository repository) {
        this.repository = repository;
    }

    /* METODO PARA OBTENER LOS DATOS DE LA ENTIDAD ANIMAL */
    @GetMapping
    public List<Animal> getAllAnimales() {
        return repository.findAll();
    }

    @PostMapping
    public Animal crearAnimal(@RequestBody Animal animal) {
        return repository.save(animal);
    }

}