package api.server.base.config.smtp

import api.server.base.common.enums.DotEnvScheme
import org.springframework.context.annotation.Configuration

@Configuration
class SmtpProperties {
    fun getHost(): String {
        return DotEnvScheme.SMTP_HOST.toString()
    }

    fun getPort(): Int {
        return DotEnvScheme.SMTP_PORT.toString().toInt()
    }

    fun getUsername(): String {
        return DotEnvScheme.SMTP_USERNAME.toString()
    }

    fun getPassword(): String {
        return DotEnvScheme.SMTP_PASSWORD.toString()
    }

    fun getFromMail(): String {
        return DotEnvScheme.SMTP_FROM_MAIL.toString()
    }
}