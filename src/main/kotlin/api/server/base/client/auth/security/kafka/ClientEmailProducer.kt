package api.server.base.client.auth.security.kafka

import api.server.base.common.kafka.dto.ClientKafkaDto
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component
import java.security.SecureRandom

@Component
class ClientEmailProducer(
    private val clientEmailKafkaTemplate: KafkaTemplate<String,ClientKafkaDto.EmailDto>,
    @Value("\${kafka.client-email.topic}") private val clientEmailTopic: String,
) {

    fun sendEmail(email: String) {
        val dto = ClientKafkaDto.EmailDto(
            email = email,
            verifyCode = generateVerifyCode()
        )
        clientEmailKafkaTemplate.send(clientEmailTopic, dto)
    }


    fun generateVerifyCode(): String {
        val random = SecureRandom()
        val number = random.nextInt(900000) + 100000 // 100000 ~ 999999
        return number.toString()
    }
}