package api.server.base.common.enums

enum class RedisKeyPrefix(val prefix: String) {
    EMAIL_AUTH("EA"),
    USER_TOKEN("UT");
}