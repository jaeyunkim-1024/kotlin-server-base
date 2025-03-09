package api.server.base.config.datasource.admin//package api.server.base.config.datasource.admin

import api.server.base.common.enums.DotEnvScheme
import com.zaxxer.hikari.HikariDataSource
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
class AdminDataSourceConfig{
    @Bean(name = ["adminDataSource"])
    @ConfigurationProperties(prefix = "spring.datasource.hikari.admin")
    fun adminDataSource(): DataSource {
        val hikariDataSource = HikariDataSource()
        hikariDataSource.jdbcUrl = DotEnvScheme.DB_URL.toString()
        hikariDataSource.username = DotEnvScheme.DB_USERNAME.toString()
        hikariDataSource.password = DotEnvScheme.DB_PASSWORD.toString()
        hikariDataSource.driverClassName = "com.mysql.cj.jdbc.Driver"
        hikariDataSource.poolName = "admin-hikari-cp"
        hikariDataSource.maximumPoolSize = 100
        hikariDataSource.minimumIdle = 10
        hikariDataSource.idleTimeout = 300_000
        hikariDataSource.maxLifetime = 600_000
        return hikariDataSource
    }

}