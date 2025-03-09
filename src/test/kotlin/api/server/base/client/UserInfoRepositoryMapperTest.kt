package api.server.base.client

import api.server.base.BaseApplication
import api.server.base.client.mappers.UserInfoMapper
import api.server.base.client.user.repo.UserInfoRepository
import api.server.base.config.dotenv.DotEnv
import org.junit.jupiter.api.BeforeAll
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.Test
import kotlin.test.assertEquals

@SpringBootTest(classes = [BaseApplication::class])
class UserInfoRepositoryMapperTest(

){
    companion object {
        @JvmStatic
        @BeforeAll
        fun setUp(): Unit {
            DotEnv()
        }
    }

    @Autowired
    private lateinit var userInfoRepository: UserInfoRepository

    @Autowired
    private lateinit var userInfoMapper: UserInfoMapper


    @Test
    fun contexTLoads() {
        assert(true)
    }

    @Test
    fun jpaTest(){
        val list = userInfoRepository.findAll()
        assertEquals(1,list.size)
    }

    @Test
    fun mapperTest(){
        val list = userInfoMapper.selectUserInfo()
        assertEquals(1,list.size)
    }
}