package com.pawshelt.service;

import com.pawshelt.dto.CitaDTO;
import com.pawshelt.model.Cita;
import com.pawshelt.model.PersonaAdoptante;
import com.pawshelt.repository.CitaRepository;
import com.pawshelt.repository.PersonaAdoptanteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CitaService {

    private final CitaRepository citaRepo;
    private final PersonaAdoptanteRepository personaRepo;

    public CitaService(CitaRepository citaRepo, PersonaAdoptanteRepository personaRepo) {
        this.citaRepo = citaRepo;
        this.personaRepo = personaRepo;
    }

    public List<CitaDTO> obtenerTodas() {
        return citaRepo.findAll().stream().map(this::toDTO).toList();
    }

    public CitaDTO obtenerPorId(Long id) {
        Cita cita = citaRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cita no encontrada con id: " + id));
        return toDTO(cita);
    }

    public CitaDTO crear(CitaDTO dto) {
        // Validación de superposición
        List<Cita> superpuestas = citaRepo.findCitasSuperpuestas(dto.getFechaHoraInicio(), dto.getFechaHoraFin());
        if (!superpuestas.isEmpty()) {
            throw new RuntimeException("Ya existe una cita en ese horario"); // o ResponseStatusException con 409
        }

        Cita cita = new Cita();
        copiarDTOaEntidad(dto, cita);
        return toDTO(citaRepo.save(cita));
    }


    public CitaDTO actualizar(Long id, CitaDTO dto) {
        Cita cita = citaRepo.findById(id).orElseThrow();
        copiarDTOaEntidad(dto, cita);
        return toDTO(citaRepo.save(cita));
    }

    public void eliminar(Long id) {
        citaRepo.deleteById(id);
    }

    private void copiarDTOaEntidad(CitaDTO dto, Cita cita) {
        cita.setTitulo(dto.getTitulo());
        cita.setDescripcion(dto.getDescripcion());
        cita.setFechaHoraInicio(dto.getFechaHoraInicio());
        cita.setFechaHoraFin(dto.getFechaHoraFin());

        PersonaAdoptante adoptante = personaRepo.findById(dto.getPersonaAdoptanteId())
                .orElseThrow(() -> new IllegalArgumentException("Persona adoptante no encontrada"));
        cita.setPersonaAdoptante(adoptante);
    }

    private CitaDTO toDTO(Cita cita) {
        CitaDTO dto = new CitaDTO();
        dto.setId(cita.getId());
        dto.setTitulo(cita.getTitulo());
        dto.setDescripcion(cita.getDescripcion());
        dto.setFechaHoraInicio(cita.getFechaHoraInicio());
        dto.setFechaHoraFin(cita.getFechaHoraFin());
        dto.setPersonaAdoptanteId(cita.getPersonaAdoptante().getId());
        return dto;
    }
}

