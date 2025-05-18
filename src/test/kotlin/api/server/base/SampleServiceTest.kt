package api.server.base

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest(classes = [BaseApplication::class])
@Transactional
class SampleServiceTest(
//    @Autowired private val service: Service
) {

}