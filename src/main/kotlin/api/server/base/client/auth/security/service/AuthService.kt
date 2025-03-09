package api.server.base.client.auth.security.service

import api.server.base.client.auth.security.model.AuthRequestDto
import api.server.base.client.auth.security.model.CustomUserDetails
import api.server.base.client.auth.user.entity.UserInfo
import api.server.base.client.auth.user.repo.UserInfoRepository
import api.server.base.common.model.CustomException
import api.server.base.common.model.CustomResultCode
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils

@Service
class AuthService(
    private val userInfoRepository: UserInfoRepository
) {

    fun signUp(dto: AuthRequestDto.SignUpRequestDto) : UsernamePasswordAuthenticationToken{
        val email: String = dto.email
        val username: String = dto.userName
        require(!(!StringUtils.hasLength(email) || !StringUtils.hasLength(username))) { "빈 값입니다." }

        require(!userInfoRepository.existsUserInfoByEmail(email)) {
            throw CustomException(CustomResultCode.AUTH_002)
        }

        val size: Int = dto.password.length
        require(!(size < 6 || size > 20)) { "비밀번호는 최소 6자, 최대 20자를 만족해야합니다." }
        val user = userInfoRepository.save(UserInfo(dto))
        return UsernamePasswordAuthenticationToken(CustomUserDetails(user), user.password, arrayListOf(
            SimpleGrantedAuthority(user.userRole)
        ))
    }
}