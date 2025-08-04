package com.pawshelt.service;

import com.pawshelt.model.PersonaAdoptante;
import com.pawshelt.repository.PersonaAdoptanteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonaAdoptanteService {

    private final PersonaAdoptanteRepository repo;

    public PersonaAdoptanteService(PersonaAdoptanteRepository repo) {
        this.repo = repo;
    }

    public List<PersonaAdoptante> listar() {
        return repo.findAll();
    }

    public PersonaAdoptante obtenerPorId(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("No encontrado"));
    }

    public PersonaAdoptante crear(PersonaAdoptante p) {
        return repo.save(p);
    }

    public PersonaAdoptante actualizar(Long id, PersonaAdoptante p) {
        PersonaAdoptante existente = obtenerPorId(id);
        existente.setNombre(p.getNombre());
        existente.setEmail(p.getEmail());
        existente.setTelefono(p.getTelefono());
        existente.setDireccion(p.getDireccion());
        return repo.save(existente);
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}
