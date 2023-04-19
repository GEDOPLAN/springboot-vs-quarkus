package de.gedoplan.showcase.health;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@ConfigurationProperties("maintenance")
public record MaintenanceConfig(List<Window> windows) {

    record Window(LocalDateTime start, Duration duration){

        public LocalDateTime end() {
            return start.plus(duration);
        }
    }
}
