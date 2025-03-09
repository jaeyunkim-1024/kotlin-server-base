package api.server.base.client.auth.security

import api.server.base.client.auth.user.repo.UserInfoRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.util.*

@Service
class CustomUserDetailService(
    private val userInfoRepository: UserInfoRepository
) : UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails {
        return Optional.ofNullable(CustomUserDetails(userInfoRepository.findUserInfoByEmail(username!!)))
            .orElseThrow { UsernameNotFoundException("사용자를 찾을 수 없습니다.") }
    }
}