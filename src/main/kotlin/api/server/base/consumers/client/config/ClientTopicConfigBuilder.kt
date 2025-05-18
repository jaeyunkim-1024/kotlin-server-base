package api.server.base.consumers.client.config

import api.server.base.common.kafka.interfaces.AbstractTopicConfig

object ClientTopicConfigBuilder {
    data class DefaultTopicConfig(
        val configProps: MutableMap<String, String>? = null
    ) : AbstractTopicConfig {
        override fun getConfigs() : Map<String, String> {
            val combineMap = mutableMapOf<String, String>()
            combineMap.putAll(defaultConfigs)
            if(configProps != null){
                combineMap.putAll(configProps)
            }
            return combineMap
        }
    }
}