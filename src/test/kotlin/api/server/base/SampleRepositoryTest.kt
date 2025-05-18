package api.server.base

import jakarta.transaction.Transactional
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.boot.test.context.SpringBootTest

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@SpringBootTest(classes = [BaseApplication::class])
@Transactional
class SampleRepositoryTest(
//    @Autowired private var userRepository: UserRepository
) {
    @Test
    fun test(){
       print("test")
    }
}