package br.com.portfolio.multitenant.infra.config

import br.com.portfolio.multitenant.domain.entity.User
import br.com.portfolio.multitenant.domain.repository.UserRepository
import org.flywaydb.core.Flyway
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.function.Consumer
import javax.sql.DataSource

@Configuration
class FlywayConfig {
    @Bean
    fun flyway(dataSource: DataSource?): Flyway {
        val flyway = Flyway.configure()
            .locations("db/migration/default")
            .dataSource(dataSource)
            .schemas(TenantIdentifierResolver.DEFAULT_TENANT)
            .load()
        flyway.migrate()
        return flyway
    }

    @Bean
    fun commandLineRunner(repository: UserRepository, dataSource: DataSource): CommandLineRunner {
        return CommandLineRunner {
            repository.findAll().forEach(Consumer { user: User ->
                val flyway = Flyway.configure()
                    .locations("db/migration/tenants")
                    .dataSource(dataSource)
                    .schemas(user.username)
                    .load()
                flyway.migrate()
            })
        }
    }
}