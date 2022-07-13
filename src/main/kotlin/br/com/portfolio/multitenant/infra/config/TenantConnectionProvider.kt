package br.com.portfolio.multitenant.infra.config

import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider
import org.springframework.stereotype.Component
import java.sql.Connection
import java.sql.SQLException
import javax.sql.DataSource

@Component
class TenantConnectionProvider(
    private val datasource: DataSource
) : MultiTenantConnectionProvider {

    @Throws(SQLException::class)
    override fun getAnyConnection(): Connection {
        return datasource.connection
    }

    @Throws(SQLException::class)
    override fun releaseAnyConnection(connection: Connection) {
        connection.close()
    }

    @Throws(SQLException::class)
    override fun getConnection(tenantIdentifier: String): Connection {
        val connection = anyConnection
        connection.createStatement()
            .execute("SET SCHEMA '$tenantIdentifier';")
        return connection
    }

    @Throws(SQLException::class)
    override fun releaseConnection(tenantIdentifier: String, connection: Connection) {
        connection.createStatement()
            .execute("SET SCHEMA '${TenantIdentifierResolver.DEFAULT_TENANT}';")
        releaseAnyConnection(connection)
    }

    override fun supportsAggressiveRelease(): Boolean {
        return false
    }

    override fun isUnwrappableAs(unwrapType: Class<*>?): Boolean {
        return false
    }

    override fun <T> unwrap(unwrapType: Class<T>): T? {
        return null
    }
}
