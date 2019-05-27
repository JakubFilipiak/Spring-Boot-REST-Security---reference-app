package com.jakubfilipiak.restthymeleaf.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Jakub Filipiak on 08.05.2019.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlanetDto {

    private String planetName;
    private long distanceFromSun;
    private double oneWayLightTimeToTheSun;
    private long lengthOfYear;
    private String planetType;
    private String planetInfo;
    private String planetImage;
}
