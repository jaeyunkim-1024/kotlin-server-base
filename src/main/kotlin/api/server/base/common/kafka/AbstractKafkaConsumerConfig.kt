package api.server.base.common.kafka

import org.springframework.kafka.core.ConsumerFactory

abstract class AbstractKafkaConsumerConfig(
    vararg groups: String
) {
    abstract fun consumerFactory(groupId:String): ConsumerFactory<String, String>
}