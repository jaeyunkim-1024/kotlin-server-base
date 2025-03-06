package api.server.base.config.datasource.client

import api.server.base.common.ProfileFlavor
import api.server.base.config.datasource.strategy.UpperCasePhysicalNamingStrategy
import jakarta.persistence.EntityManagerFactory
import org.hibernate.cfg.AvailableSettings
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.JpaVendorAdapter
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import javax.sql.DataSource

@Configuration
class ClientJpaConfig {
    @Primary
    @Bean(name = ["clientEntityManagerFactory"])
    fun clientEntityManagerFactory(
        @Qualifier("clientDataSource") clientDataSource: DataSource,
        jpaVendorAdapter: JpaVendorAdapter
    ): LocalContainerEntityManagerFactoryBean {
        val properties = mutableMapOf<String, Any?>()

        var showSql:Boolean = false
        var formatSql:Boolean = false
        var useSqlComment:Boolean = false
        val profiles = System.getProperty("spring.profiles.active")
        if(arrayOf(ProfileFlavor.LOCAL.env, ProfileFlavor.DEV.env).contains(profiles)) {
            showSql = true
            formatSql = true
            useSqlComment = true
        }

        // JPA Hibernate properties
        properties[AvailableSettings.HBM2DDL_AUTO] = "none" // ddl-auto: none
        properties[AvailableSettings.DIALECT] = "org.hibernate.dialect.MySQLDialect" // MySQL Dialect
        properties[AvailableSettings.SHOW_SQL] = showSql // SQL 출력 비활성화
        properties[AvailableSettings.FORMAT_SQL] = formatSql // SQL 포맷팅 비활성화
        properties[AvailableSettings.USE_SQL_COMMENTS] = useSqlComment // SQL 주석 비활성화
        properties[AvailableSettings.PHYSICAL_NAMING_STRATEGY] = UpperCasePhysicalNamingStrategy::class.java.name
        properties["hibernate.session.events.log.LOG_QUERIES_SLOWER_THAN_MS"] = 100L // 느린 쿼리 로그
        properties[AvailableSettings.DEFAULT_BATCH_FETCH_SIZE] = 1000 // 배치 페치 크기
        // 원복 cp pool size 현재 100이 최대다.default:100
        properties[AvailableSettings.C3P0_MAX_SIZE] = 100 // CP POOL MAX SIZE

        return EntityManagerFactoryBuilder(jpaVendorAdapter, properties, null)
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