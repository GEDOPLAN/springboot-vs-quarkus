package de.gedoplan.showcase.rest;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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
