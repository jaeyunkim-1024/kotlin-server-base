package api.server.base.config.datasource.admin//package api.server.base.config.datasource.admin

import api.server.base.admin.user.entity.LoginHistory
import org.apache.ibatis.session.SqlSessionFactory
import org.apache.ibatis.type.TypeAliasRegistry
import org.mybatis.spring.SqlSessionFactoryBean
import org.mybatis.spring.SqlSessionTemplate
import org.mybatis.spring.annotation.MapperScan
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.core.io.support.PathMatchingResourcePatternResolver
import javax.sql.DataSource

@Profile("local","dev")
@ComponentScan(basePackages = ["api.server.base.admin"])
@MapperScan(
    basePackages = ["api.server.base.admin.mappers"],
    sqlSessionFactoryRef = "adminSqlSessionFactory"
)
@Configuration
class AdminMybatisConfig {
    @Bean(name = ["adminSqlSessionFactory"])
    fun adminSqlSessionFactory(
        @Qualifier("adminDataSource") adminDataSource: DataSource,
        applicationContext: ApplicationContext
    ): SqlSessionFactory {
        // MyBatis Configuration 설정
        val configuration = org.apache.ibatis.session.Configuration()
        configuration.isMapUnderscoreToCamelCase = true
        configureTypeAliases(configuration)

        val sessionFactory = SqlSessionFactoryBean()
        sessionFactory.setDataSource(adminDataSource)
        sessionFactory.setMapperLocations(
            *PathMatchingResourcePatternResolver()
                .getResources("classpath:mybatis/admin/*.xml")
        )
        sessionFactory.setConfiguration(configuration)
        return sessionFactory.`object`!!
    }

    fun configureTypeAliases(configuration: org.apache.ibatis.session.Configuration) {
        val typeAliasRegistry: TypeAliasRegistry = configuration.typeAliasRegistry

        // typeAliases 설정

        // 공통
        typeAliasRegistry.registerAlias("LoginHistory", LoginHistory::class.java)
    }

    @Bean(name = ["adminSqlSession"])
    fun adminSqlSession(
        @Qualifier("adminSqlSessionFactory") adminSqlSessionFactory: SqlSessionFactory
    ): SqlSessionTemplate {
        return SqlSessionTemplate(adminSqlSessionFactory)
    }
}