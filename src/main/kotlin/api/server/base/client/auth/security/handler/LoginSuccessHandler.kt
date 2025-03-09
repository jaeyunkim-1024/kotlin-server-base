package api.server.base.client.auth.security.handler

import api.server.base.admin.user.entity.LoginHistory
import api.server.base.admin.user.repo.LoginHistoryRepository
import api.server.base.client.auth.security.model.CustomUserDetails
import api.server.base.client.auth.security.model.JwtTokenModel
import api.server.base.client.auth.security.provider.JwtTokenProvider
import api.server.base.client.auth.user.entity.UserInfo
import api.server.base.client.auth.user.enums.AccessCode
import api.server.base.client.auth.user.repo.UserInfoRepository
import api.server.base.common.model.CustomResponseDto
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import java.io.IOException

class LoginSuccessHandler(
    private val loginHistoryRepository: LoginHistoryRepository,
    private val userInfoRepository: UserInfoRepository,
    private val jwtTokenProvider: JwtTokenProvider,
) : SimpleUrlAuthenticationSuccessHandler(){
    @Throws(IOException::class, ServletException::class)
    override fun onAuthenticationSuccess(
        request: HttpServletRequest?,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        val mapper = ObjectMapper()
        val token: JwtTokenModel = jwtTokenProvider.issuedToken(authentication)
        val result = CustomResponseDto(
            data = token
        )
        insertHistory(authentication)
        // JSON 응답 출력
        response.addHeader("Content-Type", "application/json; charset=UTF-8")
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.status = HttpStatus.OK.value()
        response.writer.write(mapper.writeValueAsString(result))
        response.writer.flush()
    }

    private fun insertHistory(authentication: Authentication) {
        val d: CustomUserDetails = authentication.principal as CustomUserDetails
        val userInfo: UserInfo? = userInfoRepository.findUserInfoByEmail(d.username)
        if(userInfo != null){
            val loginHistory = LoginHistory(
                accessCd = AccessCode.LOGIN_SUCCESS.code,
                userSeq = userInfo.userSeq
            )
            loginHistoryRepository.save(loginHistory)
        }
    }
}