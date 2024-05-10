package de.gedoplan.showcase.rest;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import org.jboss.logging.Logger;

@ApplicationScoped
@Path("greeting")
public class GreetingResource {

  @Inject
  Logger logger;

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String get() {
    logger.debugf("Hello!");
    return "Hello!";
  }
}
