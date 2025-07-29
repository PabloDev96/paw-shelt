package com.pawshelt.repository;

import com.pawshelt.model.Animal;
import com.pawshelt.model.TipoAnimal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {
    List<Animal> findByTipo(TipoAnimal tipo);
}
