package com.jakubfilipiak.restthymeleaf.controllers;

import com.jakubfilipiak.restthymeleaf.extras.CreatorXls;
import com.jakubfilipiak.restthymeleaf.models.dtos.PlanetDto;
import com.jakubfilipiak.restthymeleaf.services.PlanetService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by Jakub Filipiak on 08.05.2019.
 */
@Controller
public class HomeController {

    private PlanetService planetService;

    public HomeController(PlanetService planetService) {
        this.planetService = planetService;
    }

    @GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("planets", planetService.getPlanetsDto());
        SecurityContext securityContext = SecurityContextHolder.getContext();
        model.addAttribute("message", "You are logged in as " +
                securityContext.getAuthentication().getName());
        // .getPrincipal zwróci wszystkie info, w tym nazwę użytkownika
        // poprzedzoną name=..., więc trzeba by wyciągnąć
        return "index";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/planets")
    public String planetPage(Model model) {
        model.addAttribute("planets", planetService.getPlanetsDto());
        return "planets";
    }

    @GetMapping("/delete")
    public String deletePlanet(@RequestParam(value = "planet") String planetName) {
        planetService.deletePlanet(planetName);
        return "redirect:/planets";
    }

    @PostMapping("/add")
    public String addPlanet(@ModelAttribute PlanetDto planet) {
        planetService.addPlanet(planet);
        return "redirect:/planets";
    }

    @GetMapping("/excel")
    public String createFile() throws NoSuchMethodException, IOException,
            IllegalAccessException, InvocationTargetException {
        CreatorXls<PlanetDto> creatorXls = new CreatorXls<>(PlanetDto.class);
        creatorXls.createFile(planetService.getPlanetsDto(),
                "src/main/resources/", "planets");
        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
}
