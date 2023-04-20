package de.gedoplan.showcase.service;

import de.gedoplan.showcase.domain.Planet;
import de.gedoplan.showcase.repository.PlanetRepository;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PlanetService {

    private  final PlanetRepository planetRepository;

    @Value("${G_CONST}")
    double G;

    @Timed
    public double getGravity(String id1, String id2) {
        Planet planet1 = planetRepository.findById(id1).orElseThrow(() -> new RuntimeException("Planet mit Id "+id1+"nicht vorhanden"));
        Planet planet2 = planetRepository.findById(id2).orElseThrow(() -> new RuntimeException("Planet mit Id "+id2+"nicht vorhanden"));

        // For demostration purposes: assume some distance
        double distance = 42_000_000;

        // Calculate gravity
        return G * planet1.getMass() * planet2.getMass() / distance / distance;
    }
}
