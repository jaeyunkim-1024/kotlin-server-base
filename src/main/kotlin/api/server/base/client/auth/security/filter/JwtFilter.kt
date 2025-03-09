package api.server.base.client.auth.security.filter

import api.server.base.client.auth.security.provider.JwtTokenProvider
import api.server.base.common.enums.SecurityPaths.allowPermitAllPaths
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter

class JwtFilter(
    private val jwtTokenProvider: JwtTokenProvider
) : OncePerRequestFilter(){

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        logger.info("[kbug] JwtFilter doFilter")
        val accessToken: String = jwtTokenProvider.resolveToken(request as HttpServletRequest)
        if (StringUtils.hasText(accessToken)) {
            if (jwtTokenProvider.validateToken(accessToken)) {
                val authentication: Authentication = jwtTokenProvider.getAuthentication(accessToken)
                SecurityContextHolder.getContext().authentication = authentication
            }
        }
        filterChain.doFilter(request, response)
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        return allowPermitAllPaths.contains(request.requestURI)
    }
}