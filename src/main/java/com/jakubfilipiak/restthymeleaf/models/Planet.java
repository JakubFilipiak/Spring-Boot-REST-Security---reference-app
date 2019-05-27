package com.jakubfilipiak.restthymeleaf.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Created by Jakub Filipiak on 08.05.2019.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "planets")
public class Planet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "planet_name", nullable = false, unique = true)
    private String planetName;

    @Column(name = "distance_from_sun")
    private long distanceFromSun;

    @Column(name = "one_way_light_time_to_the_sun")
    private double oneWayLightTimeToTheSun;

    @Column(name = "length_of_year")
    private long lengthOfYear;

    @Column(name = "planet_type")
    private String planetType;

    @Column(name = "planet_info")
    private String planetInfo;

    @Column(name = "planet_image")
    private String planetImage;


}
