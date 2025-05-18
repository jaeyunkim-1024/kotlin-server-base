package api.server.base

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.context.WebApplicationContext

@AutoConfigureMockMvc
@SpringBootTest(classes = [BaseApplication::class])
@WithMockUser(username = "admin", roles = ["SUPER_ADMIN","ADMIN","USER"])
class SampleControllerTest(
//    private val authService: AuthService,
//    private val userService: UserService,
) {
    @Autowired
    private val context: WebApplicationContext? = null

    lateinit var mockMvc: MockMvc

    val token: String = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJrb29reS5pbyIsImlhdCI6MTczNjkyNjMwNCwiZXhwIjoxNzYyODQ2MzA0LCJzdWIiOiI4In0.CCclwkz-hZSDjMR_Y7fVC3XfUobQ0ZxUqpZIOGZUQVE";

    val objectMapper: ObjectMapper = ObjectMapper()

    lateinit var userAgent: String

    @BeforeEach
    fun beforeEach() {
        /// 앱 UA
        userAgent = "Dart/JunitTest"
        mockMvc = MockMvcBuilders.webAppContextSetup(context!!)
         .apply<DefaultMockMvcBuilder>(SecurityMockMvcConfigurers.springSecurity())
         .build()
    }

    fun requestBodyTest(){
        val email = ""
        val json = """
           "email":"$email"                     
        """.trimIndent()
        val apiUrl = "/v3/auth/email/validation"
        val result: MvcResult = mockMvc.perform(
            put(apiUrl)
                .header("User-Agent", userAgent)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json)
        )
            .andDo { println(MockMvcResultHandlers.print()) }
            .andExpect(status().isOk)
            .andReturn()

        val resDto: HashMap<*,*> = objectMapper.readValue(result.response.contentAsString, HashMap::class.java)
        assertEquals("SUCCESS",resDto["resultCode"])
    }

    fun requestParamTest(){
        val email = ""
        val requestParams: MultiValueMap<String, String> = LinkedMultiValueMap<String, String>().apply {
            add("key1", "value1")
            add("key2", "value2")
        }
        val apiUrl = "/v3/auth/email/validation"
        val result: MvcResult = mockMvc.perform(
            get(apiUrl)
                .header("User-Agent", userAgent)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .params(requestParams)
        )
            .andDo { println() }
            .andExpect(status().isOk)
            .andReturn()

        val resDto: HashMap<*,*> = objectMapper.readValue(result.response.contentAsString, HashMap::class.java)
        assertEquals("SUCCESS",resDto["resultCode"])
    }
}