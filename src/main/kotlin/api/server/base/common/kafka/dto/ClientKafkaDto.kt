package api.server.base.common.kafka.dto

object ClientKafkaDto {
    data class EmailDto(
        var email: String? = null,
        var verifyCode: String? = null,
    )
}