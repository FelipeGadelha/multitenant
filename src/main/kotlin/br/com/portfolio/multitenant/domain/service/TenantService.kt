package br.com.portfolio.multitenant.domain.service

import org.flywaydb.core.Flyway
import org.springframework.stereotype.Component
import javax.sql.DataSource


@Component
class TenantService(
    private val dataSource: DataSource
) {
    fun initDatabase(schema: String) {
        val flyway = Flyway.configure()
            .locations("db/migration/tenants")
            .dataSource(dataSource)
            .schemas(schema)
            .load()
        flyway.migrate()
    }
}