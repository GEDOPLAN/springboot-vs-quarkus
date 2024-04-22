package de.gedoplan.showcase.rest;

import de.gedoplan.showcase.domain.Planet;
import de.gedoplan.showcase.repository.PlanetRepository;
import de.gedoplan.showcase.service.PlanetService;
import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RequiredArgsConstructor
@RestController
@RequestMapping("planets")
public class PlanetResource {

    // Using Lombok magic:
    // @RequiredArgsConstructor creates constructor with parameters for final fields
    // See below for a non-Lombok alternative
    private final PlanetService planetService;
    private final PlanetRepository planetRepository;

//    @Autowired
//    public PlanetResource(PlanetRepository planetRepository, PlanetService planetService) {
//        this.planetRepository = planetRepository;
//        this.planetService = planetService;
//    }

    @GetMapping
    public Collection<Planet> get() {
        return planetRepository.findAll();
    }

    @PostMapping
    public void post(@RequestBody Planet planet) {
        planetRepository.save(planet);
    }

    @GetMapping(path = "{id1}-{id2}")
    public double getGravity(@PathVariable String id1, @PathVariable String id2) {
        return this.planetService.getGravity(id1, id2);
    }

}
