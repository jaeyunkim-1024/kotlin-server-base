package api.server.base.common.kafka.interfaces

import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory

abstract class AbstractKafkaProducerConfig<T> {
    abstract fun producerFactory(): ProducerFactory<String, T>

    abstract fun kafkaTemplate(): KafkaTemplate<String, T>
}