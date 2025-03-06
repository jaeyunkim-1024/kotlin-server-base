package api.server.base

import api.server.base.config.dotenv.DotEnv
import api.server.base.config.dotenv.DotEnvScheme
import io.github.cdimascio.dotenv.Dotenv
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.stereotype.Component

@SpringBootApplication
class BaseApplication

fun main(args: Array<String>) {
	DotEnv()
	runApplication<BaseApplication>(*args)
}