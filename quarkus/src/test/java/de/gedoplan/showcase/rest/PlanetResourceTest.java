package de.gedoplan.showcase.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import de.gedoplan.showcase.domain.Planet;
import de.gedoplan.showcase.service.PlanetService;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.common.mapper.TypeRef;

@QuarkusTest
public class PlanetResourceTest {

  @Inject
  PlanetService planetService;

  @BeforeAll
  @Transactional
  static void initTestData() {
    if (Planet.count() == 0) {
      Planet.persist(
          new Planet("sol3", "Earth", 5.972e24),
          new Planet("sol4", "Mars", 6.417e23));
    }
  }

  @Test
  public void testGet() {
    List<Planet> planets = given()
        .when().get("/api/planets")
        .then()
        .statusCode(200)
        .extract()
        .as(new TypeRef<List<Planet>>(){});
    
    assertEquals(2, planets.size());
  }
}
