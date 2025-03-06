package api.server.base.admin

import api.server.base.BaseApplication
import api.server.base.BaseApplicationTests
import api.server.base.admin.mappers.LoginHistoryMapper
import api.server.base.config.dotenv.DotEnv
import jakarta.transaction.Transactional
import org.junit.jupiter.api.BeforeAll
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.Test
import kotlin.test.assertEquals

@Transactional
@SpringBootTest(classes = [BaseApplication::class])
class LoginHistoryRepositoryTest {
    @Autowired
    private lateinit var loginHistoryRepository: LoginHistoryRepository

    @Autowired
    private lateinit var loginHistoryMapper: LoginHistoryMapper

    companion object {
        @JvmStatic
        @BeforeAll
        fun setUp(): Unit {
            DotEnv()
        }
    }

    @Test
    fun jpaTest(){
        val list = loginHistoryRepository.findAll()
        assertEquals(true,list.size > 2)
    }

    @Test
    fun mapperTest(){
        val list = loginHistoryMapper.selectLoginHistory()
        assertEquals(true,list.size > 2)
    }
}