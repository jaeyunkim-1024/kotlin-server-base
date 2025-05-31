package api.server.base.config.security

import api.server.base.admin.user.repo.LoginHistoryRepository
import api.server.base.client.auth.security.filter.JwtFilter
import api.server.base.client.auth.security.filter.LoginFilter
import api.server.base.client.auth.security.filter.LogoutFilter
import api.server.base.client.auth.security.handler.LoginFailureHandler
import api.server.base.client.auth.security.handler.LoginSuccessHandler
import api.server.base.client.auth.security.handler.LogoutSuccessHandler
import api.server.base.client.auth.security.provider.JwtTokenProvider
import api.server.base.client.auth.user.repo.UserInfoRepository
import api.server.base.common.enums.SecurityPaths.allowOrigin
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

@Profile("local","dev")
@Configuration
class SecurityFilterConfig(
    private val jwtTokenProvider: JwtTokenProvider,
    private val userInfoRepository: UserInfoRepository,
    private val loginHistoryRepository: LoginHistoryRepository,
) {
    @Bean
    @Throws(Exception::class)
    fun loginFilter(
        @Qualifier("authenticationManager") authenticationManager: AuthenticationManager
    ): LoginFilter {
        val loginFilter: LoginFilter = LoginFilter("/api/auth/sign-in")
        loginFilter.setAuthenticationManager(authenticationManager)
        loginFilter.setAuthenticationSuccessHandler(
            LoginSuccessHandler(
                jwtTokenProvider = jwtTokenProvider,
                loginHistoryRepository =  loginHistoryRepository
            )
        )
        loginFilter.setAuthenticationFailureHandler(
            LoginFailureHandler(
                userInfoRepository =  userInfoRepository,
                loginHistoryRepository =  loginHistoryRepository
            )
        )
        return loginFilter
    }

    @Bean
    fun logoutFilter(): LogoutFilter {
        val logoutFilter = LogoutFilter(
            logoutUrl = "/api/auth/sign-out",
            logoutSuccessHandler = LogoutSuccessHandler(jwtTokenProvider),
            jwtTokenProvider = jwtTokenProvider
        )
        logoutFilter.setFilterProcessesUrl("/api/auth/sign-out")
        return logoutFilter
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
}