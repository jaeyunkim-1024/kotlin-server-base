package api.server.base.consumers.client.consumers

import api.server.base.common.kafka.dto.ClientKafkaDto
import api.server.base.common.service.SmtpService
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class ClientEmailConsumer(
    private val smtpService: SmtpService
) {
    @KafkaListener(
        topics = ["\${kafka.client-email.topic}"],
        groupId = "\${kafka.client-email.group-id}",
        containerFactory = "clientEmailListenerContainerFactory"
    )
    fun clientEmailListener(dto: ClientKafkaDto.EmailDto) {
        val email = dto.email ?: return
        val verifyCode = dto.verifyCode ?: return
        smtpService.sendEmail(email, "인증 이메일", "테스트 메일입니다. $verifyCode")
    }
}