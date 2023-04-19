package de.gedoplan.showcase.rest;

import de.gedoplan.baselibs.test.PostgresTest;
import de.gedoplan.showcase.domain.Planet;
import de.gedoplan.showcase.repository.PlanetRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.client.MockMvcWebTestClient;

import java.util.Arrays;

@AutoConfigureMockMvc
@SpringBootTest
public class PlanetResourceTest implements PostgresTest {

    static WebTestClient webTestClient;

    static Planet[] test_data = {
            new Planet("sol3", "Earth", 5.972e24),
            new Planet("sol4", "Mars", 6.417e23)};


    @BeforeAll
    static void initWebTestClient(@Autowired MockMvc mockMvc) {
        webTestClient = MockMvcWebTestClient.bindTo(mockMvc).build();
    }

    @BeforeAll
    static void initTestData(@Autowired PlanetRepository planetRepository) {
        if (planetRepository.count() == 0) {
            planetRepository.saveAll(Arrays.asList(test_data));
        }
    }

    @Test
    public void testGet() {
        webTestClient.get()
                .uri("/planets")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Planet.class)
                .hasSize(2)
                .contains(test_data);
    }
}
