package api.server.base.client.auth.security.filter

import api.server.base.client.auth.security.model.AuthRequestDto
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.util.StringUtils
import java.util.*

class LoginFilter(
    loginUrl:String
) : AbstractAuthenticationProcessingFilter(loginUrl) {
    override fun attemptAuthentication(request: HttpServletRequest?, response: HttpServletResponse?): Authentication {
        logger.info("[kbug] LoginFilter attemptAuthentication")
        val objectMapper = ObjectMapper()
        val dto: AuthRequestDto.LoginRequestDto = objectMapper.readValue(request!!.inputStream, AuthRequestDto.LoginRequestDto::class.java)
        val email: String = Optional.ofNullable(dto.email).orElse( "" )
        val password: String = Optional.ofNullable(dto.password).orElse( "" )
        if (!StringUtils.hasLength(email)) {
            throw AuthenticationServiceException(email)
        }
        if (!StringUtils.hasLength(password) || password.length < 6 || password.length > 20) {
            throw AuthenticationServiceException(email)
        }
        val authRequest = UsernamePasswordAuthenticationToken(email, password)
        return authenticationManager.authenticate(authRequest)
    }
}