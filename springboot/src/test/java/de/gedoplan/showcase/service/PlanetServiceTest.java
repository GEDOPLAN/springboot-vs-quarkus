package de.gedoplan.showcase.service;

import de.gedoplan.baselibs.test.PostgresTest;
import de.gedoplan.showcase.domain.Planet;
import de.gedoplan.showcase.repository.PlanetRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PlanetServiceTest implements PostgresTest {

    @Autowired
    PlanetService planetService;

    @BeforeAll
    static void initTestData(@Autowired PlanetRepository planetRepository) {
        if (planetRepository.count() == 0) {
            planetRepository.saveAll(List.of(
                    new Planet("sol3", "Earth", 5.972e24),
                    new Planet("sol4", "Mars", 6.417e23)
            ));
        }
    }

    @Test
    void testGetGravity() {
        double gravity = planetService.getGravity("sol3", "sol4");
        assertEquals(1.449e23, gravity, 0.001e23);
    }
}
