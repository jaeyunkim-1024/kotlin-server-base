package api.server.base.config.smtp

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl
import java.util.*

@Configuration
class MailConfig(
    private val mailProperties: SmtpProperties
) {
    var pt: Properties = Properties()

    @Bean
    fun javaMailService(): JavaMailSender {
        val javaMailSender = JavaMailSenderImpl()
        javaMailSender.host = mailProperties.getHost()
        javaMailSender.username = mailProperties.getUsername()
        javaMailSender.password = mailProperties.getPassword()
        javaMailSender.port = mailProperties.getPort()

        pt["mail.smtp.socketFactory.port"] = mailProperties.getPort()
        pt["mail.smtp.auth"] = true
        pt["mail.smtp.starttls.enable"] = true
        pt["mail.smtp.starttls.required"] = true
        pt["mail.smtp.socketFactory.fallback"] = false
        pt["mail.smtp.socketFactory.class"] = mailProperties.getSocketFactoryClass()

        javaMailSender.javaMailProperties = pt
        javaMailSender.defaultEncoding = "UTF-8"

        return javaMailSender
    }
}