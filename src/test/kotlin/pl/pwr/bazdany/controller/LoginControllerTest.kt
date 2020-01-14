package pl.pwr.bazdany.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import pl.pwr.bazdany.BCryptPasswordEncoder
import pl.pwr.bazdany.BazdanyApplication
import pl.pwr.bazdany.Util
import pl.pwr.bazdany.domain.Token
import pl.pwr.bazdany.mapper
import pl.pwr.bazdany.repo.TokenRepository
import pl.pwr.bazdany.repo.UserRepository
import pl.pwr.bazdany.toJson
import java.time.LocalDateTime

@SpringBootTest(classes = [BazdanyApplication::class])
class LoginControllerTest @Autowired constructor(
        private val bcryptencoder: BCryptPasswordEncoder,
        private val exceptionTranslator: ErrorCatcher,
        private val httpMessageConverters: Array<HttpMessageConverter<*>>
) {

    @MockK
    private lateinit var userRepo: UserRepository

    @MockK
    private lateinit var tokenRepo: TokenRepository

    private lateinit var restMvc: MockMvc

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)

        val loginController = LoginController(userRepo, tokenRepo, bcryptencoder)

        this.restMvc = MockMvcBuilders.standaloneSetup(loginController)
                .setMessageConverters(*httpMessageConverters)
                .setControllerAdvice(exceptionTranslator)
                .build()
    }

    @Test
    fun `Given incorrect request Should return internal error`(){
        val requ = """{
            "email":""
        """.trimMargin()

        restMvc.perform(
                post("/applogin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requ.toJson())
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isInternalServerError)
    }

    @Test
    fun `Given non existent credentials Should return bad request`(){
        val requ= Util.dummyUser

        restMvc.perform(
                post("/applogin")
                        .content(requ.toJson())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isInternalServerError)
    }

    @Test
    fun `Given wrong credentials Should return bad request`(){
        val requ= Util.loginReq

        every { userRepo.findByEmail(any()) } returns null

        restMvc.perform(
                post("/applogin")
                        .content(requ.toJson())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isBadRequest)
    }

    @Test
    fun `Given correct credentials Should login and return dto`(){
        val email = "a@a.pl"
        val password ="xdddddddd"
        val requ = LoginRequest(email, password)
        val user = Util.dummyUser.copy(id = 123, email = email, password = bcryptencoder.encode(password))

        every { userRepo.findByEmail(any()) } returns user
        every { tokenRepo.saveAndFlush(any() as Token) } returns Token(token = "123", expiryDate = LocalDateTime.now())

        val res = restMvc.perform(
                post("/applogin")
                        .content(requ.toJson())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk)
                .andReturn()

        val loginResponse = mapper().readValue(res.response.contentAsString, LoginResponse::class.java)

        assertThat(loginResponse.userId).isEqualTo(user.id)
    }

}