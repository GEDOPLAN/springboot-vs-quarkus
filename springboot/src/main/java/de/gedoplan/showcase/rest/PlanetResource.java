package de.gedoplan.showcase.rest;

import de.gedoplan.showcase.domain.Planet;
import de.gedoplan.showcase.repository.PlanetRepository;
import de.gedoplan.showcase.service.PlanetService;
import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RequiredArgsConstructor
@RestController
@RequestMapping("planets")
public class PlanetResource {

    private final PlanetRepository planetRepository;
    private final PlanetService planetService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<Planet> get() {
        return planetRepository.findAll();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void post(@RequestBody Planet planet) {
        planetRepository.save(planet);
    }

    @GetMapping(path = "{id1}-{id2}", produces = MediaType.APPLICATION_JSON_VALUE)
    public double getGravity(@PathVariable String id1, @PathVariable String id2) {
        return this.planetService.getGravity(id1, id2);
    }

}
