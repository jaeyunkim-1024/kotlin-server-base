package api.server.base.common.service

import org.slf4j.LoggerFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.ScanOptions
import org.springframework.data.redis.core.ValueOperations
import org.springframework.stereotype.Service
import java.util.*
import java.util.concurrent.TimeUnit

@Service
class RedisService(
    private val redisTemplate: RedisTemplate<String, String>
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    private val unit = TimeUnit.SECONDS
    /// ttl 디폴트 3시간
    private val defaultTtl:Long = 3 * 60 * 60

    fun setValue(key: String, value: String, expirationTime: Long) {
        setStringValue(key, value, expirationTime)
    }

    // expire == -2: 키 X
    // expire == -1: 키는 존재하지만 만료되지 않음(만료시간 설정 안된 키, 즉 무한)
    // expire >= 0: 남은 시간
    fun setValue(key: String, value: String, expirationTime: Long? = null) {
        setStringValue(key, value, expirationTime)
    }

    fun increment(key: String, delta: Long){
        val valueOperation: ValueOperations<String?, String?> = redisTemplate.opsForValue()
        valueOperation.increment(key, delta)
    }


    // expire == -2: 키 X
    // expire == -1: 키는 존재하지만 만료되지 않음(만료시간 설정 안된 키, 즉 무한)
    // expire >= 0: 남은 시간
    fun isExist(key: String): Boolean {
        return redisTemplate.hasKey(key)
    }

    fun deleteKeys(keys: List<String>): Boolean {
        var result = true;
        for(key in keys){
            if(!deleteKey(key)){
                result = false
            }
        }
        return result
    }

    fun deleteKey(key: String?): Boolean {
        return java.lang.Boolean.TRUE == redisTemplate.delete(key!!)
    }

    fun deleteKeysByPattern(pattern: String): Long {
        val keysToDelete = HashSet<String>()

        redisTemplate.execute { connection ->
            val scanOptions = ScanOptions.scanOptions()
                .match("$pattern*")
                .count(1000)
                .build()

            connection.scan(scanOptions).use { cursor ->
                while (cursor.hasNext()) {
                    keysToDelete.add(String(cursor.next()))
                }
            }
            null
        }

        return if (keysToDelete.isNotEmpty()) {
            redisTemplate.delete(keysToDelete)
        } else 0L
    }

    fun getValue(key: String): String {
        return Optional.ofNullable(redisTemplate.opsForValue()[key]).orElse("0")
    }

    private fun setStringValue(key: String, value: String, expirationTime: Long?) {
        try{
            redisTemplate.opsForValue()[key, value,
                expirationTime ?: defaultTtl
            ] = unit
        }catch(e:Exception){
            logger.error(e.message)
        }
    }
}