package api.server.base.config.datasource.client

import api.server.base.config.dotenv.DotEnvScheme
import com.zaxxer.hikari.HikariDataSource
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.*
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import javax.sql.DataSource

@Configuration
@EnableJpaRepositories(
    basePackages = ["api.server.base.client"],
    entityManagerFactoryRef = "clientEntityManagerFactory",
    transactionManagerRef = "clientTransactionManager",
    includeFilters = [ComponentScan.Filter(
        type = FilterType.REGEX,
        pattern = ["api\\.server\\.base\\.(?!admin)"] // 제외할 패키지 설정
    )]
)
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