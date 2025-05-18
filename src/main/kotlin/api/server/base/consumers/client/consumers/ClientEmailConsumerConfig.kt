package api.server.base.consumers.client.consumers

import api.server.base.common.enums.DotEnvScheme
import api.server.base.common.kafka.dto.ClientKafkaDto
import api.server.base.common.kafka.interfaces.AbstractKafkaConsumerConfig
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.support.serializer.JsonDeserializer

@Configuration
class ClientEmailConsumerConfig(
    @Value("\${kafka.client-email.group-id}") private val clientEmailGrp: String,

    ) : AbstractKafkaConsumerConfig<ClientKafkaDto.EmailDto>(clientEmailGrp){
    @Bean
    fun clientEmailListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, ClientKafkaDto.EmailDto> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, ClientKafkaDto.EmailDto>()
        factory.consumerFactory = consumerFactory(clientEmailGrp)
        return factory
    }

    override fun consumerFactory(groupId: String): ConsumerFactory<String, ClientKafkaDto.EmailDto> {
        val config: MutableMap<String, Any> = HashMap()
        config[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = DotEnvScheme.KAFKA_BOOTSTRAP_SERVERS.toString()
        config[ConsumerConfig.GROUP_ID_CONFIG] = groupId
        config[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        config[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = JsonDeserializer::class.java

        config[ConsumerConfig.AUTO_OFFSET_RESET_CONFIG] = "latest"

        val jsonDeserializer = JsonDeserializer(ClientKafkaDto.EmailDto::class.java).apply {
            setRemoveTypeHeaders(false)
            addTrustedPackages("api.server.base.common.kafka.dto") // 또는 패키지 명시: "api.server.base.common.dto"
            setUseTypeMapperForKey(true)
        }

        return DefaultKafkaConsumerFactory(config, StringDeserializer(), jsonDeserializer)

    }
}