package api.server.base.admin

import api.server.base.BaseApplication
import api.server.base.admin.mappers.LoginHistoryMapper
import api.server.base.admin.user.repo.LoginHistoryRepository
import api.server.base.config.dotenv.DotEnv
import jakarta.transaction.Transactional
import org.junit.jupiter.api.BeforeAll
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.Test
import kotlin.test.assertEquals

@Transactional
@SpringBootTest(classes = [BaseApplication::class])
class LoginHistoryRepositoryMapperTest {
    @Autowired(required = false)
    private lateinit var loginHistoryRepository: LoginHistoryRepository

    @Autowired(required = false)
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
        assertEquals(true,list.size > 0)
    }

    @Test
    fun mapperTest(){
        val list = loginHistoryMapper.selectLoginHistory()
        assertEquals(true,list.size > 0)
    }
}