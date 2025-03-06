package api.server.base.common.utils

import java.sql.Timestamp
import java.time.LocalDateTime
import java.time.LocalTime

object CustomTimeUtils {
    fun getCurrentTime(): Timestamp {
        val now = LocalDateTime.now()
        return Timestamp.valueOf(now)
    }

    fun getExpireTimeByDays(days: Long, max: Boolean): Timestamp {
        val now = LocalDateTime.now()
        val expireDate = now.plusDays(days)
        if (max) {
            return Timestamp.valueOf(expireDate.with(LocalTime.MAX))
        }
        return Timestamp.valueOf(expireDate)
    }

    fun getExpireTimeByHours(hours: Long): Timestamp {
        val now = LocalDateTime.now()
        val expireDate = now.plusHours(hours)
        return Timestamp.valueOf(expireDate)
    }

    fun getExpireTimeByTime(time: Long): Timestamp {
        val now = LocalDateTime.now()
        val expireDate = now.plusSeconds(time)
        return Timestamp.valueOf(expireDate)
    }
}