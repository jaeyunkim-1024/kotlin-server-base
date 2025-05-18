package api.server.base.common.kafka

import org.apache.kafka.common.config.TopicConfig

interface AbstractTopicConfig {
    val defaultConfigs : Map<String, String>
        get() = mapOf(
            /// 카프카 default : 7일, 디폴트 1일로
            TopicConfig.RETENTION_MS_CONFIG to (1000 * 60 * 60 * 24).toString(),
            /// 카프카 default : 1048576byte -> 1MB, 디폴트 2mb로
            TopicConfig.MAX_MESSAGE_BYTES_CONFIG to (1048576 * 2).toString(),
        )

    fun getConfigs() : Map<String, String>
}