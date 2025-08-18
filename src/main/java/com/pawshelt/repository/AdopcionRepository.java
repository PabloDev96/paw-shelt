package com.pawshelt.repository;

import com.pawshelt.model.Adopcion;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AdopcionRepository extends JpaRepository<Adopcion, Long> {

    @Query("SELECT a FROM Adopcion a JOIN FETCH a.animal JOIN FETCH a.personaAdoptante")
    List<Adopcion> findAllConRelaciones();

    @Query(value = """
      SELECT to_char(a.fecha_adopcion, 'YYYY-MM-DD') AS k, COUNT(*) AS v
      FROM adopcion a
      WHERE a.fecha_adopcion >= :desde AND a.fecha_adopcion <= :hasta
      GROUP BY k ORDER BY k
      """, nativeQuery = true)
    List<Object[]> countAdopcionesPorDia(@Param("desde") LocalDate desde, @Param("hasta") LocalDate hasta);

    @Query(value = """
      SELECT to_char(a.fecha_adopcion, 'YYYY-MM') AS k, COUNT(*) AS v
      FROM adopcion a
      WHERE a.fecha_adopcion >= :desde AND a.fecha_adopcion <= :hasta
      GROUP BY k ORDER BY k
      """, nativeQuery = true)
    List<Object[]> countAdopcionesPorMes(@Param("desde") LocalDate desde, @Param("hasta") LocalDate hasta);
}
