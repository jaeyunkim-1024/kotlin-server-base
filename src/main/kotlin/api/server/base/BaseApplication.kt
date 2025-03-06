package api.server.base

import api.server.base.config.dotenv.DotEnv
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BaseApplication

fun main(args: Array<String>) {
	DotEnv()
	runApplication<BaseApplication>(*args)
}