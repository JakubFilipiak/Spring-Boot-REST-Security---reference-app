package com.jakubfilipiak.restthymeleaf.services;

import com.jakubfilipiak.restthymeleaf.mappers.PlanetMapper;
import com.jakubfilipiak.restthymeleaf.models.Planet;
import com.jakubfilipiak.restthymeleaf.models.dtos.PlanetDto;
import com.jakubfilipiak.restthymeleaf.repositories.PlanetRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Jakub Filipiak on 08.05.2019.
 */
@Service
public class PlanetService {

    private PlanetRepository planetRepository;
    private PlanetMapper planetMapper;

    public PlanetService(PlanetRepository planetRepository, PlanetMapper planetMapper) {
        this.planetRepository = planetRepository;
        this.planetMapper = planetMapper;
    }

    // warstwa DAO - data access object
    // DAO - czyste dane z bazy danych
    // my zrobimy sobie mapper i będziemy korzystać z DTO

    public List<Planet> getPlanets() {
        return planetRepository.findAll();
    }

    // warstwa DTO - data transfer object
    // my nie będziemy korzystać z DAO tylko wykorzystamy mappery

    public List<PlanetDto> getPlanetsDto() {
        return planetRepository
                .findAll()
                .stream()
                .map(planetMapper::map)
                .collect(Collectors.toList());
    }

    public Planet addPlanet(PlanetDto planetDto) {
        return planetRepository.save(planetMapper.reverseMap(planetDto));
    }

    public void updatePlanet(PlanetDto planetDto) {
        planetRepository
                .findPlanetByPlanetName(planetDto.getPlanetName())
                .ifPresent(planet -> {
                    planet.setDistanceFromSun(planetDto.getDistanceFromSun());
                    planet.setOneWayLightTimeToTheSun(planetDto.getOneWayLightTimeToTheSun());
                    planet.setLengthOfYear(planetDto.getLengthOfYear());
                    planet.setPlanetType(planetDto.getPlanetType());
                    planet.setPlanetInfo(planetDto.getPlanetInfo());
                    planet.setPlanetImage(planetDto.getPlanetImage());

                    planetRepository.save(planet);
                });
    }

    public void deletePlanet(String planetName) {
        planetRepository.deletePlanetByPlanetName(planetName);
    }

    public List<PlanetDto> getPlanetByDistanceFromSun(Long distance) {
        return planetRepository
                .findPlanetsByDistanceFromSun(distance)
                .stream()
                .map(planetMapper::map)
                .collect(Collectors.toList());
    }
}
