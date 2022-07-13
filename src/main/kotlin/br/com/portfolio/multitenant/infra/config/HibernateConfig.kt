package br.com.portfolio.multitenant.infra.config

import br.com.portfolio.multitenant.MultitenantApplication
import org.hibernate.MultiTenancyStrategy
import org.hibernate.cfg.Environment
import org.hibernate.context.spi.CurrentTenantIdentifierResolver
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.orm.jpa.JpaVendorAdapter
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import javax.sql.DataSource

@Configuration
class HibernateConfig {
    @Bean
    fun jpaVendorAdapter(): JpaVendorAdapter {
        return HibernateJpaVendorAdapter()
    }

    @Bean
    fun entityManagerFactory(
        dataSource: DataSource,
        jpaProperties: JpaProperties,
        multiTenantConnectionProvider: MultiTenantConnectionProvider,
        tenantIdentifierResolver: CurrentTenantIdentifierResolver
    ): LocalContainerEntityManagerFactoryBean {
        val em = LocalContainerEntityManagerFactoryBean()
        em.dataSource = dataSource
        em.setPackagesToScan(MultitenantApplication::class.java.getPackage().name)
        em.jpaVendorAdapter = jpaVendorAdapter()
        val jpaPropertiesMap: MutableMap<String, Any> = HashMap(jpaProperties.properties)
        jpaPropertiesMap[Environment.DIALECT] = "org.hibernate.dialect.PostgreSQLDialect"
        jpaPropertiesMap[Environment.MULTI_TENANT] = MultiTenancyStrategy.SCHEMA
        jpaPropertiesMap[Environment.MULTI_TENANT_CONNECTION_PROVIDER] = multiTenantConnectionProvider
        jpaPropertiesMap[Environment.MULTI_TENANT_IDENTIFIER_RESOLVER] = tenantIdentifierResolver
        em.setJpaPropertyMap(jpaPropertiesMap)
        return em
    }
}