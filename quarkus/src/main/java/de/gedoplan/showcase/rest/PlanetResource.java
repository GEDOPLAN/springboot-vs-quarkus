package de.gedoplan.showcase.rest;

import java.util.Collection;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import org.eclipse.microprofile.metrics.annotation.Counted;

import de.gedoplan.showcase.domain.Planet;
import de.gedoplan.showcase.service.PlanetService;

@ApplicationScoped
@Path("planets")
public class PlanetResource {

  // Using field injection here
  // See below for contructor injection alternative
  @Inject
  PlanetService planetService;

//  @Inject
//  public PlanetResource(PlanetService planetService) {
//    this.planetService = planetService;
//  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Collection<Planet> get() {
    return Planet.listAll();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Transactional
  public void post(Planet planet) {
    planet.persist();
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("{id1}-{id2}")
  @Counted
  public double getGravity(@PathParam("id1") String id1, @PathParam("id2") String id2) {
    return this.planetService.getGravity(id1, id2);
  }

}
