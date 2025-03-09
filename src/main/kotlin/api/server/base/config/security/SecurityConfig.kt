package api.server.base.config.security

import api.server.base.admin.user.repo.LoginHistoryRepository
import api.server.base.client.auth.security.filter.JwtFilter
import api.server.base.client.auth.security.filter.LoginFilter
import api.server.base.client.auth.security.handler.LoginFailureHandler
import api.server.base.client.auth.security.handler.LoginSuccessHandler
import api.server.base.client.auth.security.provider.CustomAuthenticationProvider
import api.server.base.client.auth.security.provider.JwtTokenProvider
import api.server.base.client.auth.security.service.CustomUserDetailService
import api.server.base.client.auth.user.enums.UserRoles
import api.server.base.client.auth.user.repo.UserInfoRepository
import api.server.base.common.enums.SecurityPaths.adminPath
import api.server.base.common.enums.SecurityPaths.allowNoCertUserPaths
import api.server.base.common.enums.SecurityPaths.allowOrigin
import api.server.base.common.enums.SecurityPaths.allowPermitAllPaths
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

@Configuration
class SecurityConfig(
    private val jwtTokenProvider: JwtTokenProvider,
    private val customUserDetailService: CustomUserDetailService,

    private val userInfoRepository: UserInfoRepository,
    private val loginHistoryRepository: LoginHistoryRepository,
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.invoke{
            allowNoCertUserPaths.forEach{
                securityMatcher(it)
            }
            securityMatcher(adminPath)

            authorizeHttpRequests {
                authorize(HttpMethod.OPTIONS, "/**", permitAll)

                allowPermitAllPaths.forEach {
                    authorize(it, permitAll)
                }

                allowNoCertUserPaths.forEach {
                    authorize(it, hasAnyRole(UserRoles.ROLE_ADMIN.role, UserRoles.ROLE_USER.role, UserRoles.ROLE_NO_CERT.role))
                }

                authorize(adminPath, hasRole(UserRoles.ROLE_ADMIN.role))

                authorize(anyRequest, denyAll)
            }

            addFilterAt<CorsFilter>(corsFilter())
            addFilterAt<UsernamePasswordAuthenticationFilter>(loginFilter())
            addFilterBefore<LoginFilter>(jwtFilter())
        }

        return http.build()
    }

    @Bean
    fun daoAuthenticationProvider(): DaoAuthenticationProvider {
        val customAuthenticationProvider = CustomAuthenticationProvider()
        customAuthenticationProvider.setUserDetailsService(customUserDetailService)
        customAuthenticationProvider.setPasswordEncoder(passwordEncoder())
        return customAuthenticationProvider
    }

    @Bean
    @Throws(java.lang.Exception::class)
    fun authenticationManager(): AuthenticationManager {
        val provider = daoAuthenticationProvider()
        return ProviderManager(provider)
    }

    @Bean
    @Throws(Exception::class)
    fun loginFilter(): LoginFilter {
        val loginFilter: LoginFilter = LoginFilter("/api/auth/sign-in")
        loginFilter.setAuthenticationManager(authenticationManager())
        loginFilter.setAuthenticationSuccessHandler(LoginSuccessHandler(
            jwtTokenProvider = jwtTokenProvider,
            userInfoRepository =  userInfoRepository,
            loginHistoryRepository =  loginHistoryRepository
        ))
        loginFilter.setAuthenticationFailureHandler(LoginFailureHandler(
            userInfoRepository =  userInfoRepository,
            loginHistoryRepository =  loginHistoryRepository
        ))
        return loginFilter
    }

    @Bean
    fun corsFilter(): CorsFilter {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()
        config.allowCredentials = true
        // config.addAllowedOrigin("*") 대신 사용
        config.setAllowedOriginPatterns(allowOrigin)
        config.addAllowedHeader("*")
        config.addAllowedMethod("*")

        source.registerCorsConfiguration("/**", config)
        return CorsFilter(source)
    }

    @Bean
    fun jwtFilter(): JwtFilter {
        return JwtFilter(jwtTokenProvider)
    }

    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }
}