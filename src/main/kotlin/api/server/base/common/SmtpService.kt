package api.server.base.common

import api.server.base.config.smtp.SmtpProperties
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils

@Service
class SmtpService(
    private val mailSender: JavaMailSender,
    private val mailProperties: SmtpProperties
) {
    fun sendEmail(to: String, title: String?, content: String?): Boolean {
        if (!StringUtils.hasLength(to)) return false

        try {
            val message = mailSender.createMimeMessage()
            val messageHelper = MimeMessageHelper(message, false, "UTF-8")

            messageHelper.setSubject(title!!)
            messageHelper.setText(content!!, true)
            messageHelper.setFrom(mailProperties.getFromMail())

            val toArr = to.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

            messageHelper.setTo(toArr)

            mailSender.send(message) // 메일발송
            return true
        } catch (e: Exception) {
            e.stackTrace
            return false
        }
    }
}