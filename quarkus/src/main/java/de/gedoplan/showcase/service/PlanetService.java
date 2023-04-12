package de.gedoplan.showcase.service;

import javax.enterprise.context.ApplicationScoped;

import de.gedoplan.showcase.domain.Planet;

@ApplicationScoped
public class PlanetService {

  public double getGravity(String id1, String id2) {
    Planet planet1 = Planet.findById(id1);
    Planet planet2 = Planet.findById(id2);

    // For demostration purposes: assume some distance
    double distance = 42_000_000;

    // Calculate gravity
    double G = 6.674e-11;
    return G * planet1.getMass() * planet2.getMass() / distance / distance;
  }
}
