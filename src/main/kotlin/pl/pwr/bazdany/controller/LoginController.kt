package pl.pwr.bazdany.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import pl.pwr.bazdany.BCryptPasswordEncoder
import pl.pwr.bazdany.domain.Token
import pl.pwr.bazdany.repo.TokenRepository
import pl.pwr.bazdany.repo.UserRepository
import java.util.*

@RestController
class LoginController(
        private val userRepo: UserRepository,
        private val tokenRepository: TokenRepository,
        private val bcryptencoder: BCryptPasswordEncoder
) {

    @PostMapping("/applogin")
    fun login(@RequestBody request: LoginRequest): LoginResponse{
        val user = userRepo.findByEmail(request.email)
        user ?: throw RequestException("Błędne dane")

        val encPwd = bcryptencoder.encode(request.password)
        if(encPwd != user.password)
            throw RequestException("Błędne dane")

       // val existToken = tokenRepository.deleteByUser(user)

        val token = Token(
                token = UUID.randomUUID().toString(),
                userId = user
        )

        tokenRepository.saveAndFlush(token)

        return LoginResponse(
                userId = user.id!!,
                token = token.token!!
        )
    }
}

data class LoginRequest(
        val email: String,
        val password: String
)

data class LoginResponse(
        val userId: Long,
        val token: String
)