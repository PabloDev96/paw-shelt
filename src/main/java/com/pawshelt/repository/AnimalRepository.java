package com.pawshelt.repository;

import com.pawshelt.model.Animal;
import com.pawshelt.model.TipoAnimal;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {

    List<Animal> findByTipo(TipoAnimal tipo);

    // ===== Categóricos actuales (quesos) =====
    @Query(value = """
      SELECT a.sexo AS categoria, COUNT(*) AS cantidad
      FROM animal a
      GROUP BY a.sexo
      """, nativeQuery = true)
    List<Object[]> countPorSexoRaw();

    @Query(value = """
      SELECT a.tipo AS categoria, COUNT(*) AS cantidad
      FROM animal a
      GROUP BY a.tipo
      """, nativeQuery = true)
    List<Object[]> countPorEspecieRaw();

    // ===== Edades -> lo calcularemos en Java con (edad_cantidad, unidad_edad) =====
    @Query("SELECT a.edadCantidad, a.unidadEdad FROM Animal a")
    List<Object[]> findEdadCantidadYUnidad();

  @Query(value = """
      SELECT k, SUM(gatos) AS gatos, SUM(perros) AS perros FROM (
        SELECT to_char(a.fecha_ingreso, 'YYYY-MM-DD') AS k,
               CASE WHEN a.tipo = 'GATO'  THEN 1 ELSE 0 END AS gatos,
               CASE WHEN a.tipo = 'PERRO' THEN 1 ELSE 0 END AS perros
        FROM animal a
        WHERE a.fecha_ingreso >= :desde AND a.fecha_ingreso <= :hasta
      ) t
      GROUP BY k ORDER BY k
      """, nativeQuery = true)
  List<Object[]> countAltasPorDiaTipoRaw(@Param("desde") LocalDate desde, @Param("hasta") LocalDate hasta);

  @Query(value = """
      SELECT k, SUM(gatos) AS gatos, SUM(perros) AS perros FROM (
        SELECT to_char(a.fecha_ingreso, 'YYYY-MM') AS k,
               CASE WHEN a.tipo = 'GATO'  THEN 1 ELSE 0 END AS gatos,
               CASE WHEN a.tipo = 'PERRO' THEN 1 ELSE 0 END AS perros
        FROM animal a
        WHERE a.fecha_ingreso >= :desde AND a.fecha_ingreso <= :hasta
      ) t
      GROUP BY k ORDER BY k
      """, nativeQuery = true)
  List<Object[]> countAltasPorMesTipoRaw(@Param("desde") LocalDate desde, @Param("hasta") LocalDate hasta);

  default Map<String, long[]> toStackMap(List<Object[]> rows) {
    Map<String, long[]> map = new LinkedHashMap<>();
    for (Object[] r : rows) {
      map.put(String.valueOf(r[0]),
              new long[]{ ((Number) r[1]).longValue(), ((Number) r[2]).longValue() });
    }
    return map;
  }

    // Helpers comunes (útiles en el Service)
    default Map<String, Long> toMap(List<Object[]> rows) {
        Map<String, Long> map = new LinkedHashMap<>();
        for (Object[] r : rows) {
            map.put(String.valueOf(r[0]), ((Number) r[1]).longValue());
        }
        return map;
    }
}
