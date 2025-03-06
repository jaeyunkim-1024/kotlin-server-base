package api.server.base.config.kafka

import org.apache.kafka.clients.admin.NewTopic
import org.apache.kafka.common.config.TopicConfig
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.TopicBuilder
import org.springframework.kafka.core.KafkaAdmin

@Configuration
class KafkaTopicBuilder(
    @Value("\${kafka.topic.sample.name}") private val sampleTopicName: String,
) {

    @Bean
    fun topicsBuild(): KafkaAdmin.NewTopics{
        return KafkaAdmin.NewTopics(
            sampleTopic(),
        );
    }

    private fun sampleTopic(): NewTopic {
        return TopicBuilder
            .name(sampleTopicName)
            // 파티션 개수
            .partitions(1)
            // 파티션 복제 계수, 1인 경우 리더 파티션만 존재하는것.
            .replicas(1)
            // kafka borker가 메시지를 어느 시간까지 보관할지 시간이나 byte에 따라서 초과 시, 데이터를 삭제한다.
            // 디스크 크기와 데이터의 중요성에 따라 판단한다.-> 1분
            .config(TopicConfig.RETENTION_MS_CONFIG, (1000 * 60).toString())
            .build()
    }
}