package api.server.base.client.auth.security.handler

import api.server.base.admin.user.entity.LoginHistory
import api.server.base.admin.user.repo.LoginHistoryRepository
import api.server.base.client.auth.user.entity.UserInfo
import api.server.base.client.auth.user.enums.AccessCode
import api.server.base.client.auth.user.repo.UserInfoRepository
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler
import java.io.IOException

class LoginFailureHandler(
    private val loginHistoryRepository: LoginHistoryRepository,
    private val userInfoRepository: UserInfoRepository,
) : SimpleUrlAuthenticationFailureHandler(){
    @Throws(IOException::class, ServletException::class)
    override fun onAuthenticationFailure(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
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
        super.onAuthenticationFailure(request, response, exception)
    }
}