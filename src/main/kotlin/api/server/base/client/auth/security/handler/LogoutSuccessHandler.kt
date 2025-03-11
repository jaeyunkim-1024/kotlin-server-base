package api.server.base.client.auth.security.handler

import api.server.base.client.auth.security.provider.JwtTokenProvider
import api.server.base.common.model.CommonResponseDto
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.logout.LogoutHandler

class LogoutSuccessHandler(
    val jwtTokenProvider: JwtTokenProvider
) : LogoutHandler {
    override fun logout(request: HttpServletRequest, response: HttpServletResponse, authentication: Authentication?) {
        // 로그아웃 처리
        println("[kbug] ??? LogoutSuccessHandler.logout()")
        authentication?.principal.let {
            jwtTokenProvider.tokenExpire(it as String)
        }
        SecurityContextHolder.clearContext()

        // JSON 응답 출력
        val mapper = ObjectMapper()
        val result = CommonResponseDto(
            data = "로그아웃 성공"
        )
        response.addHeader("Content-Type", "application/json; charset=UTF-8")
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.status = HttpStatus.OK.value()
        response.writer.write(mapper.writeValueAsString(result))
        response.writer.flush()
    }
}