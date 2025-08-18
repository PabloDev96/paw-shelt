package com.pawshelt.repository;

import com.pawshelt.model.Cita;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface CitaRepository extends JpaRepository<Cita, Long> {

    @Query("SELECT c FROM Cita c WHERE (c.fechaHoraInicio < :fin AND c.fechaHoraFin > :inicio)")
    List<Cita> findCitasSuperpuestas(@Param("inicio") LocalDateTime inicio, @Param("fin") LocalDateTime fin);

    @Query(value = """
      SELECT to_char(c.fecha_hora_inicio, 'YYYY-MM-DD') AS k, COUNT(*) AS v
      FROM cita c
      WHERE c.fecha_hora_inicio::date >= :desde AND c.fecha_hora_inicio::date <= :hasta
      GROUP BY k ORDER BY k
      """, nativeQuery = true)
    List<Object[]> countCitasPorDia(@Param("desde") LocalDate desde, @Param("hasta") LocalDate hasta);

    @Query(value = """
      SELECT to_char(c.fecha_hora_inicio, 'YYYY-MM') AS k, COUNT(*) AS v
      FROM cita c
      WHERE c.fecha_hora_inicio::date >= :desde AND c.fecha_hora_inicio::date <= :hasta
      GROUP BY k ORDER BY k
      """, nativeQuery = true)
    List<Object[]> countCitasPorMes(@Param("desde") LocalDate desde, @Param("hasta") LocalDate hasta);
}
