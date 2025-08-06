package com.pawshelt.service;

import com.pawshelt.dto.AdopcionDTO;
import com.pawshelt.dto.AdopcionRequest;
import com.pawshelt.model.Adopcion;
import com.pawshelt.model.Animal;
import com.pawshelt.model.EstadoAnimal;
import com.pawshelt.model.PersonaAdoptante;
import com.pawshelt.repository.AdopcionRepository;
import com.pawshelt.repository.AnimalRepository;
import com.pawshelt.repository.PersonaAdoptanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdopcionService {

    @Autowired
    private AdopcionRepository adopcionRepository;

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private PersonaAdoptanteRepository personaAdoptanteRepository;

    // Listado de adopciones en formato DTO
    public List<AdopcionDTO> obtenerTodasLasAdopciones() {
        List<Adopcion> adopciones = adopcionRepository.findAll();
        return adopciones.stream().map(adopcion ->
                new AdopcionDTO(
                        adopcion.getId(),
                        adopcion.getFechaAdopcion(),
                        adopcion.getObservaciones(),
                        adopcion.getAnimal().getNombre(),
                        adopcion.getPersonaAdoptante().getNombre() // o getNombreCompleto() si lo tenés
                )
        ).collect(Collectors.toList());
    }

    // Crear nueva adopción
    public void crearAdopcion(AdopcionRequest request) {
        // Buscar el animal
        Animal animal = animalRepository.findById(request.getAnimalId())
                .orElseThrow(() -> new RuntimeException("Animal no encontrado"));

        // Buscar el adoptante
        PersonaAdoptante adoptante = personaAdoptanteRepository.findById(request.getPersonaAdoptanteId())
                .orElseThrow(() -> new RuntimeException("Adoptante no encontrado"));

        // Crear nueva adopción
        Adopcion adopcion = new Adopcion();
        adopcion.setAnimal(animal);
        adopcion.setPersonaAdoptante(adoptante);
        adopcion.setFechaAdopcion(request.getFechaAdopcion());
        adopcion.setObservaciones(request.getObservaciones());

        // Cambiar estado del animal a ADOPTADO
        animal.setEstado(EstadoAnimal.ADOPTADO);
        animalRepository.save(animal);

        // Guardar adopción
        adopcionRepository.save(adopcion);
    }
}
