package com.pawshelt.repository;

import com.pawshelt.model.Adopcion;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface AdopcionRepository extends JpaRepository<Adopcion, Long> {

    @Query("SELECT a FROM Adopcion a JOIN FETCH a.animal JOIN FETCH a.personaAdoptante")
    List<Adopcion> findAllConRelaciones();
}

