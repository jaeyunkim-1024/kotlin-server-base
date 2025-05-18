package api.server.base.common.kafka

import org.apache.kafka.clients.admin.NewTopic
import org.springframework.kafka.core.KafkaAdmin

abstract class AbstractKafkaTopicConfig(
    vararg topics: String
) {
    abstract fun getKafkaAdmin(): KafkaAdmin
    abstract fun initTopic(): KafkaAdmin.NewTopics
    abstract fun createTopic(
        topicName: String,
        partitions: Int, // 파티션 개수
        replicas: Int,  // 파티션 복제 계수, 1인 경우 리더 파티션만 존재하는것.
        configs: AbstractTopicConfig
    ): NewTopic
}