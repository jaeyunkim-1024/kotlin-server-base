package api.server.base.consumers.client.config

import api.server.base.common.enums.DotEnvScheme
import api.server.base.common.kafka.interfaces.AbstractKafkaTopicConfig
import api.server.base.common.kafka.interfaces.AbstractTopicConfig
import org.apache.kafka.clients.admin.AdminClientConfig
import org.apache.kafka.clients.admin.NewTopic
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.TopicBuilder
import org.springframework.kafka.core.KafkaAdmin

@Configuration
class ClientKafkaTopicConfig(
    @Value("\${kafka.client-email.topic}") private val clientEmailTopicName: String,
) : AbstractKafkaTopicConfig(clientEmailTopicName){
    @Bean
    fun clientKafkaAdmin(): KafkaAdmin {
        val kafkaAdmin = getKafkaAdmin()
        return kafkaAdmin
    }

    override fun getKafkaAdmin(): KafkaAdmin {
        val configs: MutableMap<String?, Any?> = HashMap<String?, Any?>()
        val bootStrapSevers = DotEnvScheme.KAFKA_BOOTSTRAP_SERVERS.toString()
        configs[AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG] = bootStrapSevers
        return KafkaAdmin(configs)
    }

    @Bean
    fun clientTopicsBuild(): KafkaAdmin.NewTopics{
        return initTopic();
    }

    override fun initTopic(): KafkaAdmin.NewTopics {
        val topics = KafkaAdmin.NewTopics(
            createTopic(
                topicName = clientEmailTopicName,
                partitions = 3,
                replicas = 3,
                configs = ClientTopicConfigBuilder.DefaultTopicConfig()
            ),
        )
        return topics
    }

    override fun createTopic(
        topicName: String,
        partitions: Int,
        replicas: Int,
        configs: AbstractTopicConfig
    ): NewTopic {
        return TopicBuilder
            .name(topicName)
            .partitions(partitions)
            .replicas(replicas)
            .configs(configs.getConfigs())
            .build()
    }
}