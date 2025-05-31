package api.server.base.config.datasource.client

import api.server.base.common.enums.DotEnvScheme
import com.zaxxer.hikari.HikariDataSource
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile
import javax.sql.DataSource

@Profile("local","dev")
@Configuration
class ClientDataSourceConfig{
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.hikari.client")
    @Bean(name = ["clientDataSource"])
    fun clientDataSource(): DataSource {
        val hikariDataSource = HikariDataSource()
        hikariDataSource.jdbcUrl = DotEnvScheme.DB_URL.toString()
        hikariDataSource.username = DotEnvScheme.DB_USERNAME.toString()
        hikariDataSource.password = DotEnvScheme.DB_PASSWORD.toString()
        hikariDataSource.driverClassName = "com.mysql.cj.jdbc.Driver"
        hikariDataSource.poolName = "client-hikari-cp"
        hikariDataSource.maximumPoolSize = 100
        hikariDataSource.minimumIdle = 10
        hikariDataSource.idleTimeout = 300_000
        hikariDataSource.maxLifetime = 600_000
        return hikariDataSource
    }

}