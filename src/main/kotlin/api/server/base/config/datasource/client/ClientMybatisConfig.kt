package api.server.base.config.datasource.client

import api.server.base.client.user.entity.UserInfo
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
import org.springframework.context.annotation.Primary
import org.springframework.core.io.support.PathMatchingResourcePatternResolver
import javax.sql.DataSource

@ComponentScan(basePackages = ["api.server.base.client"])
@MapperScan(
    basePackages = ["api.server.base.client"],
    sqlSessionFactoryRef = "clientSqlSessionFactory",
    sqlSessionTemplateRef = "clientSqlSession"
)
@Configuration
class ClientMybatisConfig {
    @Primary
    @Bean(name = ["clientSqlSessionFactory"])
    fun clientSqlSessionFactory(
        @Qualifier("clientDataSource") clientDataSource: DataSource,
        applicationContext: ApplicationContext
    ): SqlSessionFactory {
        // MyBatis Configuration 설정
        val configuration = org.apache.ibatis.session.Configuration()
        configuration.isMapUnderscoreToCamelCase = true
        configureTypeAliases(configuration)

        val sessionFactory = SqlSessionFactoryBean()
        sessionFactory.setDataSource(clientDataSource)
        sessionFactory.setMapperLocations(
            *PathMatchingResourcePatternResolver()
                .getResources("classpath:mybatis/client/*.xml")
        )
        sessionFactory.setConfiguration(configuration)
        return sessionFactory.`object`!!
    }

    fun configureTypeAliases(configuration: org.apache.ibatis.session.Configuration) {
        val typeAliasRegistry: TypeAliasRegistry = configuration.typeAliasRegistry

        // typeAliases 설정

        // 공통
        typeAliasRegistry.registerAlias("UserInfo", UserInfo::class.java)
    }

    @Primary
    @Bean(name = ["clientSqlSession"])
    fun clientSqlSession(
        @Qualifier("clientSqlSessionFactory") clientSqlSessionFactory: SqlSessionFactory
    ): SqlSessionTemplate {
        return SqlSessionTemplate(clientSqlSessionFactory)
    }
}