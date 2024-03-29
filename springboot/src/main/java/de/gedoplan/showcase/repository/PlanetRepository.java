package de.gedoplan.showcase.repository;

import de.gedoplan.showcase.domain.Planet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanetRepository extends JpaRepository<Planet, String> {
  // Unused - just show Spring Data JPA magic
  public Planet findByName(String name);
}
