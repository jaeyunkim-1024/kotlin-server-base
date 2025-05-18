package api.server.base.common

import api.server.base.BaseApplicationTests
import api.server.base.common.service.SmtpService
import api.server.base.config.smtp.SmtpProperties
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.mail.javamail.JavaMailSender

@SpringBootTest(classes = [BaseApplicationTests::class])
class SmtpServiceTest{
    private lateinit var mailSender: JavaMailSender
    private lateinit var smtpProperties: SmtpProperties
    private lateinit var smtpService: SmtpService

    @BeforeEach
    fun setup() {
        mailSender = mock()
        smtpProperties = mock()
        smtpService = SmtpService(mailSender, smtpProperties)
    }

    @Test
    fun 이메일발송테스트(){
        smtpService.sendEmail("rlawodbs1024@gmail.com","테스트메일","테스트메일입니다.")
    }
}