package api.server.base.client.auth.security.service

import api.server.base.client.auth.security.model.CustomUserDetails
import api.server.base.client.auth.user.repo.UserInfoRepository
import api.server.base.client.auth.user.service.UserTokenService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.util.*

@Service
class CustomUserDetailService(
    private val userInfoRepository: UserInfoRepository,
    private val userTokenService: UserTokenService,
) : UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails {
        val user = Optional.ofNullable(userInfoRepository.findUserInfoByEmail(username!!))
            .orElseThrow { UsernameNotFoundException("사용자를 찾을 수 없습니다.") }
        /// 재로그인 시, 이전 토큰 비활성화
        userTokenService.setDisableToken(username)
        return CustomUserDetails(user)
    }
}