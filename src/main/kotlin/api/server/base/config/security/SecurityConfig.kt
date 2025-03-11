package api.server.base.config.security

import api.server.base.client.auth.security.filter.JwtFilter
import api.server.base.client.auth.security.filter.LoginFilter
import api.server.base.client.auth.security.filter.LogoutFilter
import api.server.base.client.auth.security.service.CustomUserDetailService
import api.server.base.client.auth.user.enums.UserRoles
import api.server.base.common.enums.SecurityPaths.adminPath
import api.server.base.common.enums.SecurityPaths.allowNoCertUserPaths
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.filter.CorsFilter

@Configuration
class SecurityConfig(
    private val customUserDetailService: CustomUserDetailService,
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @Bean
    fun filterChain(
        http: HttpSecurity,
        @Qualifier("loginFilter") loginFilter: LoginFilter,
        @Qualifier("logoutFilter") logoutFilter: LogoutFilter,
        @Qualifier("corsFilter") corsFilter: CorsFilter,
        @Qualifier("jwtFilter") jwtFilter: JwtFilter,
    ): SecurityFilterChain {
        http.invoke{
            httpBasic { disable() }
            csrf { disable() }
            formLogin { disable() }
            sessionManagement { sessionCreationPolicy = SessionCreationPolicy.STATELESS }

            allowNoCertUserPaths.forEach{
                securityMatcher(it)
            }
            securityMatcher(adminPath)

            authorizeHttpRequests {
                authorize(HttpMethod.OPTIONS, "/**", permitAll)
//                allowPermitAllPaths.forEach {
//                    authorize(it, permitAll)
//                }
                allowNoCertUserPaths.forEach {
                    authorize(it, hasAnyRole(UserRoles.ROLE_ADMIN.role, UserRoles.ROLE_USER.role, UserRoles.ROLE_NO_CERT.role))
                }

                authorize(adminPath, hasRole(UserRoles.ROLE_ADMIN.role))
                authorize(anyRequest, denyAll)
            }

            addFilterAt<CorsFilter>(corsFilter)
            addFilterAt<UsernamePasswordAuthenticationFilter>(loginFilter)
            addFilterAfter<UsernamePasswordAuthenticationFilter>(logoutFilter)
            addFilterBefore<LoginFilter>(jwtFilter)
        }

        return http.build()
    }

    @Bean
    @Throws(java.lang.Exception::class)
    fun authenticationManager(): AuthenticationManager {
        val provider = DaoAuthenticationProvider()
        provider.setUserDetailsService(customUserDetailService)
        provider.setPasswordEncoder(bCryptPasswordEncoder())
        return ProviderManager(provider)
    }

    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }
}