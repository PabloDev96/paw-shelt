package com.pawshelt.service;

import com.pawshelt.dto.ConteoPorCategoriaDTO;
import com.pawshelt.dto.ConteoPorFechaDTO;
import com.pawshelt.dto.ConteoPorFechaGatosPerrosDTO;
import com.pawshelt.dto.GraficosRespuestaDTO;
import com.pawshelt.model.UnidadEdad;
import com.pawshelt.repository.AdopcionRepository;
import com.pawshelt.repository.AnimalRepository;
import com.pawshelt.repository.CitaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.*;

@Service
public class GraficosService {

    private final AdopcionRepository adopRepo;
    private final CitaRepository citaRepo;
    private final AnimalRepository animalRepo;

    // Constructor explícito (sin Lombok)
    public GraficosService(AdopcionRepository adopRepo,
                           CitaRepository citaRepo,
                           AnimalRepository animalRepo) {
        this.adopRepo = adopRepo;
        this.citaRepo = citaRepo;
        this.animalRepo = animalRepo;
    }

    public GraficosRespuestaDTO obtener(String periodo) {
        LocalDate hoy = LocalDate.now(ZoneOffset.UTC);
        boolean porMes = "anio".equalsIgnoreCase(periodo);
        LocalDate desde =
                "semana".equalsIgnoreCase(periodo) ? hoy.minusDays(6) :
                        "mes".equalsIgnoreCase(periodo)    ? hoy.minusDays(29) :
                                hoy.minusMonths(11).withDayOfMonth(1);

        // Series: adopciones y citas
        List<ConteoPorFechaDTO> adopciones = serieDesdeRepo(
                desde, hoy, porMes,
                porMes ? adopRepo.countAdopcionesPorMes(desde, hoy)
                        : adopRepo.countAdopcionesPorDia(desde, hoy)
        );

        List<ConteoPorFechaDTO> citas = serieDesdeRepo(
                desde, hoy, porMes,
                porMes ? citaRepo.countCitasPorMes(desde, hoy)
                        : citaRepo.countCitasPorDia(desde, hoy)
        );

        // Serie gatos/perros (nuevos animales) — deja vacío si aún no tienes fecha_ingreso + queries
        List<ConteoPorFechaGatosPerrosDTO> nuevosAnimales = Collections.emptyList();
        try {
            var rows = porMes
                 ? animalRepo.countAltasPorMesTipoRaw(desde, hoy)
                : animalRepo.countAltasPorDiaTipoRaw(desde, hoy);
            var stackMap = toStackMap(rows);
            nuevosAnimales = serieStackDesdeMap(desde, hoy, porMes, stackMap);
        } catch (Exception ignored) {}

        // Categóricos actuales
        var sexo      = categoriasDesdePairs(animalRepo.countPorSexoRaw(), this::normalizarSexo);
        var especies  = categoriasDesdePairs(animalRepo.countPorEspecieRaw(), this::normalizarEspecie);

        // Buckets de edad desde (edadCantidad, unidadEdad) = ANIOS | MESES
        var edades    = bucketsDesdeEdadCantidad(animalRepo.findEdadCantidadYUnidad());

        return new GraficosRespuestaDTO(adopciones, citas, nuevosAnimales, edades, sexo, especies);
    }

    /* ========================= Helpers ========================= */

    private List<ConteoPorFechaDTO> serieDesdeRepo(LocalDate desde, LocalDate hasta, boolean porMes, List<Object[]> crudos) {
        Map<String, Long> map = toMap(crudos);
        List<ConteoPorFechaDTO> out = new ArrayList<>();
        for (LocalDate d = desde; !d.isAfter(hasta); d = porMes ? d.plusMonths(1) : d.plusDays(1)) {
            String k = porMes ? d.withDayOfMonth(1).toString().substring(0, 7) : d.toString(); // YYYY-MM | YYYY-MM-DD
            out.add(new ConteoPorFechaDTO(k, map.getOrDefault(k, 0L)));
        }
        return out;
    }

    private List<ConteoPorFechaGatosPerrosDTO> serieStackDesdeMap(LocalDate desde, LocalDate hasta, boolean porMes, Map<String, long[]> raw) {
        List<ConteoPorFechaGatosPerrosDTO> out = new ArrayList<>();
        for (LocalDate d = desde; !d.isAfter(hasta); d = porMes ? d.plusMonths(1) : d.plusDays(1)) {
            String k = porMes ? d.withDayOfMonth(1).toString().substring(0, 7) : d.toString();
            long[] v = raw.getOrDefault(k, new long[]{0, 0});
            out.add(new ConteoPorFechaGatosPerrosDTO(k, v[0], v[1]));
        }
        return out;
    }

    private Map<String, Long> toMap(List<Object[]> rows) {
        Map<String, Long> map = new LinkedHashMap<>();
        for (Object[] r : rows) map.put(String.valueOf(r[0]), ((Number) r[1]).longValue());
        return map;
    }

    private Map<String, long[]> toStackMap(List<Object[]> rows) {
        Map<String, long[]> map = new LinkedHashMap<>();
        for (Object[] r : rows) {
            map.put(String.valueOf(r[0]),
                    new long[]{ ((Number) r[1]).longValue(), ((Number) r[2]).longValue() });
        }
        return map;
    }

    private List<ConteoPorCategoriaDTO> categoriasDesdePairs(List<Object[]> rows, java.util.function.Function<String, String> normalize) {
        List<ConteoPorCategoriaDTO> out = new ArrayList<>();
        for (Object[] r : rows) {
            out.add(new ConteoPorCategoriaDTO(normalize.apply(String.valueOf(r[0])), ((Number) r[1]).longValue()));
        }
        return out;
    }

    private String normalizarSexo(String raw) {
        String s = raw == null ? "" : raw.toUpperCase();
        return switch (s) {
            case "MACHO" -> "Macho";
            case "HEMBRA" -> "Hembra";
            default -> raw;
        };
    }

    private String normalizarEspecie(String raw) {
        String s = raw == null ? "" : raw.toUpperCase();
        return switch (s) {
            case "GATO", "GATOS" -> "Gatos";
            case "PERRO", "PERROS" -> "Perros";
            default -> raw;
        };
    }

    private List<ConteoPorCategoriaDTO> bucketsDesdeEdadCantidad(List<Object[]> crudos) {
        long c01 = 0, c25 = 0, c69 = 0, c10 = 0;

        for (Object[] r : crudos) {
            Number cantNum = (Number) r[0];
            UnidadEdad unidad = (UnidadEdad) r[1];
            if (cantNum == null || unidad == null) continue;

            int cant = cantNum.intValue();
            double years = switch (unidad) {
                case ANIOS -> cant;
                case MESES -> cant / 12.0;
                default     -> cant; // por si añadís otro valor en el enum en el futuro
            };

            if (years < 2) c01++;
            else if (years <= 5) c25++;
            else if (years <= 9) c69++;
            else c10++;
        }

        return List.of(
                new ConteoPorCategoriaDTO("0-1", c01),
                new ConteoPorCategoriaDTO("2-5", c25),
                new ConteoPorCategoriaDTO("6-9", c69),
                new ConteoPorCategoriaDTO("10+", c10)
        );
    }
}
