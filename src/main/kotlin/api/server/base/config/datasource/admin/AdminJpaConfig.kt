package api.server.base.config.datasource.admin

import api.server.base.common.enums.ProfileFlavor
import api.server.base.config.datasource.strategy.UpperCasePhysicalNamingStrategy
import jakarta.persistence.EntityManagerFactory
import org.hibernate.cfg.AvailableSettings
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.JpaVendorAdapter
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import javax.sql.DataSource

@Configuration
@EnableJpaRepositories(
    basePackages = ["api.server.base.admin"],
    entityManagerFactoryRef = "adminEntityManagerFactory",
    transactionManagerRef = "adminTransactionManager",
//    excludeFilters = [ComponentScan.Filter(
//        type = FilterType.REGEX,
//        pattern = ["api\\.server\\.base\\.(?!client)"] // 제외할 패키지 설정
//    )]
)
class AdminJpaConfig {
    @Bean(name = ["adminEntityManagerFactory"])
    fun adminEntityManagerFactory(
        @Qualifier("adminDataSource") adminDataSource: DataSource,
        jpaVendorAdapter: JpaVendorAdapter
    ): LocalContainerEntityManagerFactoryBean {
        val hibernateProperties = mutableMapOf<String, Any?>()

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
        hibernateProperties[AvailableSettings.HBM2DDL_AUTO] = "none" // ddl-auto: none
        hibernateProperties[AvailableSettings.SHOW_SQL] = showSql // SQL 출력 비활성화
        hibernateProperties[AvailableSettings.FORMAT_SQL] = formatSql // SQL 포맷팅 비활성화
        hibernateProperties[AvailableSettings.USE_SQL_COMMENTS] = useSqlComment // SQL 주석 비활성화
        hibernateProperties[AvailableSettings.PHYSICAL_NAMING_STRATEGY] = UpperCasePhysicalNamingStrategy::class.java.name
        hibernateProperties["hibernate.session.events.log.LOG_QUERIES_SLOWER_THAN_MS"] = 100L // 느린 쿼리 로그
        hibernateProperties[AvailableSettings.DEFAULT_BATCH_FETCH_SIZE] = 1000 // 배치 페치 크기
        // 원복 cp pool size 현재 100이 최대다.default:100
        hibernateProperties[AvailableSettings.C3P0_MAX_SIZE] = 100 // CP POOL MAX SIZE

        return EntityManagerFactoryBuilder(jpaVendorAdapter, hibernateProperties, null)
            .dataSource(adminDataSource)
            .packages("api.server.base.admin") // 엔티티 스캔 패키지
            .persistenceUnit("default") // 기본 Persistence Unit 이름
            .build()
    }

    @Bean(name = ["adminTransactionManager"])
    fun adminTransactionManager(
        @Qualifier("adminEntityManagerFactory") adminEntityManagerFactory: EntityManagerFactory
    ): JpaTransactionManager {
        return JpaTransactionManager(adminEntityManagerFactory)
    }
}