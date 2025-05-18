package api.server.base.common.kafka

import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory

abstract class AbstractKafkaProducerConfig {
    abstract fun producerFactory(): ProducerFactory<String, Any>

    abstract fun kafkaTemplate(): KafkaTemplate<String, Any>
}