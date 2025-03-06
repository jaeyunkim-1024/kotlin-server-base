package api.server.base.client

import api.server.base.BaseApplication
import api.server.base.BaseApplicationTests
import api.server.base.client.mappers.UserInfoMapper
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
class UserInfoRepositoryTest {
    @Autowired
    private lateinit var userInfoRepository: UserInfoRepository

    @Autowired
    private lateinit var userInfoMapper: UserInfoMapper

    companion object {
        @JvmStatic
        @BeforeAll
        fun setUp(): Unit {
            DotEnv()
        }
    }

    @Test
    fun jpaTest(){
        val list = userInfoRepository.findAll()
        assertEquals(2,list.size)
    }

    @Test
    fun mapperTest(){
        val list = userInfoMapper.selectUserInfo()
        assertEquals(2,list.size)
    }
}