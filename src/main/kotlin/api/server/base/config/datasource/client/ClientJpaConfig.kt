package api.server.base.config.datasource.client

import api.server.base.common.enums.ProfileFlavor
import api.server.base.config.datasource.strategy.UpperCasePhysicalNamingStrategy
import jakarta.persistence.EntityManagerFactory
import org.hibernate.cfg.AvailableSettings
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.JpaVendorAdapter
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import javax.sql.DataSource

@Profile("local","dev")
@Configuration
@EnableJpaRepositories(
    basePackages = ["api.server.base.client"],
    entityManagerFactoryRef = "clientEntityManagerFactory",
    transactionManagerRef = "clientTransactionManager",
)
class ClientJpaConfig {
    @Primary
    @Bean(name = ["clientEntityManagerFactory"])
    fun clientEntityManagerFactory(
        @Qualifier("clientDataSource") clientDataSource: DataSource,
        jpaVendorAdapter: JpaVendorAdapter
    ): LocalContainerEntityManagerFactoryBean {
        val hibernateProperties = mutableMapOf<String, Any?>()

        var showSql:Boolean = false
        var formatSql:Boolean = false
        val profiles = System.getProperty("spring.profiles.active")
        if(arrayOf(ProfileFlavor.LOCAL.env, ProfileFlavor.DEV.env).contains(profiles)) {
            showSql = true
            formatSql = true
        }

        // JPA Hibernate properties
        hibernateProperties[AvailableSettings.HBM2DDL_AUTO] = "none" // ddl-auto: none
        hibernateProperties[AvailableSettings.SHOW_SQL] = showSql // SQL 출력 비활성화
        hibernateProperties[AvailableSettings.FORMAT_SQL] = formatSql // SQL 포맷팅 비활성화
        hibernateProperties[AvailableSettings.USE_SQL_COMMENTS] = false // SQL 주석 비활성화
        hibernateProperties[AvailableSettings.PHYSICAL_NAMING_STRATEGY] = UpperCasePhysicalNamingStrategy::class.java.name
        hibernateProperties["hibernate.session.events.log.LOG_QUERIES_SLOWER_THAN_MS"] = 100L // 느린 쿼리 로그
        hibernateProperties[AvailableSettings.DEFAULT_BATCH_FETCH_SIZE] = 1000 // 배치 페치 크기
        // 원복 cp pool size 현재 100이 최대다.default:100
        hibernateProperties[AvailableSettings.C3P0_MAX_SIZE] = 100 // CP POOL MAX SIZE


        return EntityManagerFactoryBuilder(jpaVendorAdapter, hibernateProperties, null)
            .dataSource(clientDataSource)
            .packages("api.server.base.client") // 엔티티 스캔 패키지
            .persistenceUnit("default") // 기본 Persistence Unit 이름
            .build()
    }

    @Primary
    @Bean(name = ["clientTransactionManager"])
    fun clientTransactionManager(
        @Qualifier("clientEntityManagerFactory") clientEntityManagerFactory: EntityManagerFactory
    ): JpaTransactionManager {
        return JpaTransactionManager(clientEntityManagerFactory)
    }
}