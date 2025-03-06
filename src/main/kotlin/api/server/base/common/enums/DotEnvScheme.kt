package api.server.base.common.enums

enum class DotEnvScheme {
    DB_URL,
    DB_USERNAME,
    DB_PASSWORD,

    REDIS_HOST,
    REDIS_PORT,
    REDIS_PASSWORD,

    JWT_SECRETKEY,
    JWT_EXPIRATION,
    JWT_SALT,
    JWT_NUM,

    KAFKA_BOOTSTRAP_SERVERS,

    SMTP_HOST,
    SMTP_PORT,
    SMTP_SOCKET_FACTORY_CLASS,
    SMTP_USERNAME,
    SMTP_PASSWORD,
    SMTP_FROM_MAIL;

    override fun toString(): String {
        return System.getProperty(name) ?: System.getenv(name)
        ?: throw IllegalArgumentException("Environment variable $name is not set.")
    }
}