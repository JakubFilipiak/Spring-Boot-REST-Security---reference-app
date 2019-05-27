package com.jakubfilipiak.restthymeleaf.mappers;

import com.jakubfilipiak.restthymeleaf.commons.Mapper;
import com.jakubfilipiak.restthymeleaf.models.Planet;
import com.jakubfilipiak.restthymeleaf.models.dtos.PlanetDto;
import org.springframework.stereotype.Component;

/**
 * Created by Jakub Filipiak on 08.05.2019.
 */
@Component
public class PlanetMapper implements Mapper<Planet, PlanetDto> {

    @Override
    public PlanetDto map(Planet dao) {
        return PlanetDto
                .builder()
                .planetName(dao.getPlanetName())
                .distanceFromSun(dao.getDistanceFromSun())
                .oneWayLightTimeToTheSun(dao.getOneWayLightTimeToTheSun())
                .lengthOfYear(dao.getLengthOfYear())
                .planetType(dao.getPlanetType())
                .planetInfo(dao.getPlanetInfo())
                .planetImage(dao.getPlanetImage())
                .build();
    }

    @Override
    public Planet reverseMap(PlanetDto dto) {
        return Planet
                .builder()
                .planetName(dto.getPlanetName())
                .distanceFromSun(dto.getDistanceFromSun())
                .oneWayLightTimeToTheSun(dto.getOneWayLightTimeToTheSun())
                .lengthOfYear(dto.getLengthOfYear())
                .planetType(dto.getPlanetType())
                .planetInfo(dto.getPlanetInfo())
                .planetImage(dto.getPlanetImage())
                .build();
    }
}
