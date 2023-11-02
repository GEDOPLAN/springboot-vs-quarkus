package de.gedoplan.showcase.rest;

import de.gedoplan.showcase.domain.Planet;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("greeting")
public class GreetingResource {

  Logger logger = LoggerFactory.getLogger(GreetingResource.class);

  @GetMapping(produces = MediaType.TEXT_PLAIN_VALUE)
  public String get() {
    logger.debug("Hello");
    return "Hello";
  }
}
