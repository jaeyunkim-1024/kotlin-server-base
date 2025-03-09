package api.server.base.client.auth.security.handler

import api.server.base.admin.user.entity.LoginHistory
import api.server.base.admin.user.repo.LoginHistoryRepository
import api.server.base.client.auth.user.entity.UserInfo
import api.server.base.client.auth.user.enums.AccessCode
import api.server.base.client.auth.user.repo.UserInfoRepository
import api.server.base.common.model.CustomResponseDto
import api.server.base.common.model.CustomResultCode
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler
import java.io.IOException

class LoginFailureHandler(
    private val loginHistoryRepository: LoginHistoryRepository,
    private val userInfoRepository: UserInfoRepository,
) : SimpleUrlAuthenticationFailureHandler(){
    private val mapper = ObjectMapper()

    @Throws(IOException::class, ServletException::class)
    override fun onAuthenticationFailure(
        request: HttpServletRequest,
        response: HttpServletResponse,
        exception: AuthenticationException
    ) {
        if (exception is BadCredentialsException) {
            if (exception.message != "Bad credentials") {
                val user: UserInfo? = userInfoRepository.findUserInfoByEmail(exception.message)
                if(user != null){
                    val loginHistory = LoginHistory(
                        accessCd = AccessCode.LOGIN_FAILED_NOT_CORRECT_EMAIL_PWD.code,
                        userSeq = user.userSeq
                    )
                    loginHistoryRepository.save(loginHistory)
                }
            }
        }

        // JSON 응답 출력
        val result = CustomResponseDto(
            resultCode = CustomResultCode.AUTH_003.name,
            message = CustomResultCode.AUTH_003.message,
            data = null
        )

        response.addHeader("Content-Type", "application/json; charset=UTF-8")
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.status = HttpStatus.OK.value()
        response.writer.write(mapper.writeValueAsString(result))
        response.writer.flush()
    }
}