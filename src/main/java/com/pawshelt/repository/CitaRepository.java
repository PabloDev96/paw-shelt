package com.pawshelt.repository;

import com.pawshelt.model.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface CitaRepository extends JpaRepository<Cita, Long> {

    @Query("SELECT c FROM Cita c WHERE (c.fechaHoraInicio < :fin AND c.fechaHoraFin > :inicio)")
    List<Cita> findCitasSuperpuestas(@Param("inicio") LocalDateTime inicio, @Param("fin") LocalDateTime fin);
}
