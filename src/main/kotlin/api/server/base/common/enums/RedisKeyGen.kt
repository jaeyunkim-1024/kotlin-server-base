package api.server.base.common.enums

import java.util.concurrent.TimeUnit

enum class RedisKeyGen(val prefix: String, val hms: Long, val unit: TimeUnit) {
    EMAIL_AUTH("EA",5,TimeUnit.MINUTES),
    USER_TOKEN("UT",3,TimeUnit.HOURS);

    val redisKey: (Any) -> String = { key -> "${prefix}:$key" }
    val ttl: () -> Long = {
        when (unit) {
            TimeUnit.HOURS -> {
                hms * 60 * 60
            }
            TimeUnit.MINUTES -> {
                hms * 60
            }
            else -> hms
        }
    }
}