package api.server.base.client.auth.security.kafka

import api.server.base.common.enums.DotEnvScheme
import api.server.base.common.kafka.dto.ClientKafkaDto
import api.server.base.common.kafka.interfaces.AbstractKafkaProducerConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.support.serializer.JsonSerializer

@Configuration
class ClientEmailKafkaProducerConfig : AbstractKafkaProducerConfig<ClientKafkaDto.EmailDto>() {
    @Bean(name = ["clientEmailKafkaTemplate"])
    override fun kafkaTemplate(): KafkaTemplate<String, ClientKafkaDto.EmailDto> {
        return KafkaTemplate(producerFactory())
    }

    override fun producerFactory(): ProducerFactory<String, ClientKafkaDto.EmailDto> {
        val config: MutableMap<String, Any> = HashMap()
        config[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = DotEnvScheme.KAFKA_BOOTSTRAP_SERVERS.toString()
        config[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        config[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = JsonSerializer::class.java

        return DefaultKafkaProducerFactory(config)
    }
}