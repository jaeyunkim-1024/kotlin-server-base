package api.server.base.client.auth.user.service

import api.server.base.common.enums.RedisKeyGen
import api.server.base.common.service.RedisService
import org.springframework.stereotype.Service

@Service
class UserTokenService(
    private val redisService: RedisService
) {
    fun setEnableToken(token: String?, email: String?) {
        if(token == null || email == null) {
            throw Exception("Token or Email is null")
        }
        val emailTokenKey: String = RedisKeyGen.USER_TOKEN.redisKey(email)
        val ttl = RedisKeyGen.USER_TOKEN.ttl()
        /** 유저 토큰 맵핑 */
        redisService.setValue(emailTokenKey, token, ttl)
        /** 토큰 관리 */
        redisService.setValue(token, "Y", ttl)
    }

    fun setDisableToken(email: String?) {
        if(email == null) {
            throw Exception("Email is null")
        }
        val emailTokenKey: String = RedisKeyGen.USER_TOKEN.redisKey(email)
        val token = redisService.getValue(emailTokenKey)
        val ttl = RedisKeyGen.USER_TOKEN.ttl()
        if (redisService.isExist(token) && redisService.getValue(token) == "Y") {
            redisService.setValue(token, "N", ttl)
        }
    }

    fun isEnableToken(token: String?): Boolean {
        if (!redisService.isExist(token!!)) {
            return true
        }
        val isYn = redisService.getValue(token)
        return isYn == "Y"
    }

}