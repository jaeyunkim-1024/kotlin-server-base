package api.server.base.config.redis

import api.server.base.common.enums.DotEnvScheme
import org.redisson.Redisson
import org.redisson.api.RedissonClient
import org.redisson.config.Config
import org.redisson.spring.data.connection.RedissonConnectionFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.StringRedisSerializer

class RedisConfig {
    @Bean
    fun redisTemplate(
        @Qualifier("redisConnectionFactory") redisConnectionFactory: RedisConnectionFactory
    ): RedisTemplate<String, String> {
        val redisTemplate: RedisTemplate<String, String> = RedisTemplate()
        redisTemplate.connectionFactory = redisConnectionFactory
        redisTemplate.keySerializer = StringRedisSerializer()
        redisTemplate.valueSerializer = StringRedisSerializer()
        redisTemplate.hashKeySerializer = StringRedisSerializer()
        redisTemplate.setEnableTransactionSupport(false)
        return redisTemplate
    }

    @Bean
    fun redisConnectionFactory(redissonClient: RedissonClient): RedisConnectionFactory {
        return RedissonConnectionFactory(redissonClient)
    }

    @Bean(destroyMethod = "shutdown")
    fun redissonClient(): RedissonClient {
        val host = DotEnvScheme.REDIS_HOST.toString()
        val port = DotEnvScheme.REDIS_PORT.toString().toInt()
        val password = DotEnvScheme.REDIS_PASSWORD.toString()

        val config: Config = Config()
        config.useSingleServer()
            .setAddress("redis://$host:$port")
            .setDnsMonitoringInterval(-1)
            .apply {
//                if(!environmentProfileChecker.isLocal()){
//                    setPassword(password)
//                }
            }
        return Redisson.create(config)
    }
}