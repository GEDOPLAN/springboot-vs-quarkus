package de.gedoplan.showcase.health;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

@ApplicationScoped
@Readiness
public class MaintenanceReadinessCheck implements HealthCheck {

  @Inject
  @ConfigProperty(name = "MAINT_START")
  Optional<LocalDateTime> maintStartOptional;

  @Inject
  @ConfigProperty(name = "MAINT_DURATION")
  Duration maintDuration;

  @Override
  public HealthCheckResponse call() {

    if (!maintStartOptional.isPresent())
      return HealthCheckResponse
          .named("Non-Maintenance")
          .up()
          .build();

    LocalDateTime maintStart = maintStartOptional.get();
    LocalDateTime maintEnd = maintStart.plus(maintDuration);
    LocalDateTime now = LocalDateTime.now();

    return HealthCheckResponse
        .named("Non-Maintenance")
        .withData("maintStart", maintStart.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
        .withData("maintEnd", maintEnd.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
        .status(now.isBefore(maintStart) || !now.isBefore(maintEnd))
        .build();
  }

}
