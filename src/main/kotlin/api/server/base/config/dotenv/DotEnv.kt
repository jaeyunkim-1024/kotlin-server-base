package api.server.base.config.dotenv

import api.server.base.common.ProfileFlavor
import io.github.cdimascio.dotenv.Dotenv


class DotEnv {
    init {
        val profiles = System.getProperty("spring.profiles.active", ProfileFlavor.LOCAL.env)
        val obj = Dotenv.configure()
            .directory("./profiles/$profiles")
            .load()

        DotEnvScheme.entries.forEach { scheme ->
            System.setProperty(scheme.name, obj[scheme.name])
        }
    }
}