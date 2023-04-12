package de.gedoplan.showcase.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import de.gedoplan.showcase.domain.Planet;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class PlanetServiceTest {

  @Inject
  PlanetService planetService;
  
  @BeforeAll
  @Transactional
  static void initTestData() {
    if (Planet.count() == 0) {
      Planet.persist(
        new Planet("sol3", "Earth", 5.972e24),
        new Planet("sol4", "Mars", 6.417e23)
        );
    }
  }

  @Test
  void testGetGravity() {
    double gravity = planetService.getGravity("sol3", "sol4");
    assertEquals(1.449e23, gravity, 0.001e23);
  }
}
