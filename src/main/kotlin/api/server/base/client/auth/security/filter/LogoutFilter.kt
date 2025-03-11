package api.server.base.client.auth.security.filter

import api.server.base.client.auth.security.provider.JwtTokenProvider
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.security.web.authentication.logout.LogoutFilter
import org.springframework.security.web.authentication.logout.LogoutHandler

class LogoutFilter(
    val logoutUrl: String,
    val logoutSuccessHandler: LogoutHandler,
    private val jwtTokenProvider: JwtTokenProvider,
) : LogoutFilter(logoutUrl,logoutSuccessHandler) {
    private val logger = LoggerFactory.getLogger(javaClass)

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        logger.info("[kbug] LogoutFilter.doFilter()")
        if(requiresLogout(request as HttpServletRequest, response as HttpServletResponse)) {
            val token = jwtTokenProvider.resolveToken(request)
            val authentication = jwtTokenProvider.getAuthentication(token)
            logoutSuccessHandler.logout(request, response, authentication)
        }
        chain.doFilter(request, response)
    }
}