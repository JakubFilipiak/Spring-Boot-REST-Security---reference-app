package com.jakubfilipiak.restthymeleaf.repositories;

import com.jakubfilipiak.restthymeleaf.models.Planet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Created by Jakub Filipiak on 08.05.2019.
 */
@Repository
public interface PlanetRepository extends JpaRepository<Planet, Long> {

    // JPA samo by ogarnęło, dzięki By..., ale zrobimy ręcznie
    // Poniżej w query jest JPQL. Jeśli chcemy czysty sql to trzeba dodać drugi
    // parametr nativeQuery = true
    // ?1 to wildcard, dzika karta - odnośnik to pierwszego argumentu metody
    // począwszy od lewej strony, czyli String planetName

    @Query("select p from Planet p where p.planetName = ?1")
    Optional<Planet> findPlanetByPlanetName(String planetName);

    // Domyślnie @Query nie ma wpływu na zmiany w bazie danych, tylko select
    // Trzeba dodać adnotacje, jak poniżej

    @Transactional
    @Modifying
    @Query("delete from Planet p where p.planetName = ?1")
    void deletePlanetByPlanetName(String planetName);

    @Query("select p from Planet p where p.distanceFromSun <= ?1")
    List<Planet> findPlanetsByDistanceFromSun(Long distance);
}
