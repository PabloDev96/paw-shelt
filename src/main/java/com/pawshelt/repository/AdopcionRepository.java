package com.pawshelt.repository;

import com.pawshelt.model.Adopcion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdopcionRepository extends JpaRepository<Adopcion, Long> {
}
