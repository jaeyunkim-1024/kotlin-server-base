package api.server.base.config.redis

import api.server.base.common.enums.DotEnvScheme
import org.springframework.context.annotation.Bean
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.RedisPassword
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.StringRedisSerializer

class RedisConfig {
    @Bean
    fun redisTemplate(): RedisTemplate<String, String> {
        val redisTemplate: RedisTemplate<String, String> = RedisTemplate()
        redisTemplate.connectionFactory = redisConnectionFactory()
        redisTemplate.keySerializer = StringRedisSerializer()
        redisTemplate.valueSerializer = StringRedisSerializer()
        redisTemplate.hashKeySerializer = StringRedisSerializer()
        redisTemplate.setEnableTransactionSupport(false)
        return redisTemplate
    }

    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory {
        val redisConfig = RedisStandaloneConfiguration()
        val host = DotEnvScheme.REDIS_HOST.toString()
        val port = DotEnvScheme.REDIS_PORT.toString().toInt()
        val password = DotEnvScheme.REDIS_PASSWORD.toString()
        redisConfig.hostName = host
        redisConfig.port = port
        redisConfig.password = RedisPassword.of(password)
        return LettuceConnectionFactory(redisConfig)
    }
}