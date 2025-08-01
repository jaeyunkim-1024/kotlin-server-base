package api.server.base.config.dotenv

import api.server.base.common.enums.DotEnvScheme
import api.server.base.common.enums.ProfileFlavor
import io.github.cdimascio.dotenv.Dotenv
import org.slf4j.LoggerFactory


class DotEnv {
    private val logger = LoggerFactory.getLogger(javaClass)

    init {
        val profiles = System.getProperty("spring.profiles.active", ProfileFlavor.LOCAL.env)
        val obj = Dotenv.configure()
            .directory("./profiles/$profiles")
            .load()
        logger.info("########## Profile : {} ##########",profiles)
        DotEnvScheme.entries.forEach { scheme ->
            System.setProperty(scheme.name, obj[scheme.name])
        }
    }
}