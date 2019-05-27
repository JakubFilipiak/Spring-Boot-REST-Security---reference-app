package com.jakubfilipiak.restthymeleaf.controllers;

import com.jakubfilipiak.restthymeleaf.models.Planet;
import com.jakubfilipiak.restthymeleaf.models.dtos.PlanetDto;
import com.jakubfilipiak.restthymeleaf.services.PlanetService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Jakub Filipiak on 08.05.2019.
 */
@CrossOrigin
@RestController
@RequestMapping("/api")
public class PlanetController {

    private PlanetService planetService;

    public PlanetController(PlanetService planetService) {
        this.planetService = planetService;
    }

    @GetMapping("/planets")
    public List<Planet> getPlanets() {
        return planetService.getPlanets();
    }

    // warstwa DTO - data transfer object

//    @GetMapping("/dto/planets")
//    public List<PlanetDto> getPlanetsDto() {
//        return planetService.getPlanetsDto();
//    }

    // wcalenie po potrzeba nic zwracać, bo i tak zwrócimy status 200 OK
    // jeśli wszystko będzie ok. return nie ma sensu
    @PostMapping("/dto/planets")
    public Planet addPlanet(@RequestBody PlanetDto planetDto) {
        return planetService.addPlanet(planetDto);
    }

    @PutMapping("/dto/planets")
    public void updatePlanet(@RequestBody PlanetDto planetDto) {
        planetService.updatePlanet(planetDto);
    }

    // można w sposób taki jak wyżej, ten poniższy jest dla funu
    @DeleteMapping("/dto/planets/{planetName}")
    public void deletePlanet(@PathVariable String planetName) {
        planetService.deletePlanet(planetName);
    }

    // dzięki zastosowaniu drugiego return, jeśli nie będzie parametru to
    // zostanie wywołana metoda zwracająca wszystkie planety DTO
    // (ta zakomentowana na górze)
    @GetMapping("/dto/planets")
    public List<PlanetDto> getPlanetsDto(@RequestParam(value = "distance",
            required = false) Long distance) {
        if (distance != null && distance > 0) {
            return planetService.getPlanetByDistanceFromSun(distance);
        }
        return planetService.getPlanetsDto();
    }
}
