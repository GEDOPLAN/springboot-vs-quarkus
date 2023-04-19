package de.gedoplan.showcase.health;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class MaintenanceHealthIndicator implements HealthIndicator {

    private final MaintenanceConfig maintenanceConfig;

    @Override
    public Health health() {
        return findCurrentWindow()
                .map(w -> Health.outOfService()
                        .withDetail("Maintenance","active")
                        .withDetail("start",w.start().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                        .withDetail("start",w.end().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                        .build())
                .orElse(Health.up().withDetail("Maintenance","inactive").build());
    }

    private Optional<MaintenanceConfig.Window> findCurrentWindow() {
        LocalDateTime now = LocalDateTime.now();
        return maintenanceConfig.windows().stream()
                .filter(w -> !now.isBefore(w.start()) && now.isBefore(w.end()))
                .findFirst();
    }
}
