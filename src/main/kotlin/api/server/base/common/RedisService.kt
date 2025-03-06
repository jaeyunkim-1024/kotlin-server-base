package api.server.base.common

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import java.util.*
import java.util.concurrent.TimeUnit

@Service
class RedisService(
    private val redisTemplate: RedisTemplate<String, String>
) {
    private val unit = TimeUnit.SECONDS

    fun setValue(key: String, value: String, expirationTime: Long) {
        setStringValue(key, value, expirationTime)
    }

    // expire == -2: 키 X
    // expire == -1: 키는 존재하지만 만료되지 않음(만료시간 설정 안된 키, 즉 무한)
    // expire >= 0: 남은 시간
    fun isExist(key: String): Boolean {
        val expire: Long = Optional.ofNullable(redisTemplate.getExpire(key, unit)).orElse(-2L)
        return expire == -1L || expire > 0L
    }

    fun deleteKey(key: String?): Boolean {
        return java.lang.Boolean.TRUE == redisTemplate.delete(key!!)
    }

    fun getValue(key: String): String {
        return Optional.ofNullable(redisTemplate.opsForValue()[key]).orElse("")
    }

    private fun setStringValue(key: String, value: String, expirationTime: Long) {
        redisTemplate.opsForValue()[key, value, expirationTime.toString().toInt().toLong()] = unit
    }
}