package de.gedoplan.showcase.rest;

import java.util.Collection;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.metrics.annotation.Counted;

import de.gedoplan.showcase.domain.Planet;
import de.gedoplan.showcase.service.PlanetService;

@ApplicationScoped
@Path("planets")
public class PlanetResource {
  
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

  @Inject
  PlanetService planetService;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("{id1}-{id2}")
  @Counted
  public double getGravity(@PathParam("id1") String id1, @PathParam("id2") String id2) {
    return this.planetService.getGravity(id1, id2);
  }

}
