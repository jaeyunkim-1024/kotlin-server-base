package api.server.base.common.kafka.interfaces

import org.springframework.kafka.core.ConsumerFactory

abstract class AbstractKafkaConsumerConfig<T>(
    var group: String
) {
    abstract fun consumerFactory(groupId:String): ConsumerFactory<String, T>
}