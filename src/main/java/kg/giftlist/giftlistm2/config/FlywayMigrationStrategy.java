package kg.giftlist.giftlistm2.config;

import lombok.Getter;
import lombok.Setter;
import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
@Setter
@Getter
public abstract class FlywayMigrationStrategy {

    @Bean
    FlywayMigrationInitializer flywayInitializer(Flyway flyway) {
        return new FlywayMigrationInitializer(flyway, (f) -> {
        });
    }

    @Bean
    @DependsOn("entityManagerFactory")
    FlywayMigrationInitializer delayedFlywayInitializer(Flyway flyway) {
        return new FlywayMigrationInitializer(flyway, new org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy() {
            @Override
            public void migrate(Flyway flyway) {

            }
        });
    }

}
