package de.gedoplan.showcase.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import de.gedoplan.showcase.domain.Planet;

@ApplicationScoped
public class PlanetService {

  @Inject
  @ConfigProperty(name = "G_CONST")
  double G;

  public double getGravity(String id1, String id2) {
    Planet planet1 = Planet.findById(id1);
    Planet planet2 = Planet.findById(id2);

    // For demostration purposes: assume some distance
    double distance = 42_000_000;

    // Calculate gravity
    return G * planet1.getMass() * planet2.getMass() / distance / distance;
  }
}
