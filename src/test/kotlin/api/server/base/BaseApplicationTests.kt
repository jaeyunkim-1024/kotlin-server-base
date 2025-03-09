package api.server.base

import api.server.base.config.dotenv.DotEnv
import org.junit.jupiter.api.BeforeAll
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class BaseApplicationTests {
	companion object {
		@JvmStatic
		@BeforeAll
		fun setUp(): Unit {
			DotEnv()
		}
	}

}
