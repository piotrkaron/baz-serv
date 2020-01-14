package pl.pwr.bazdany.controller

import com.fasterxml.jackson.annotation.JsonFormat
import org.hibernate.Session
import org.hibernate.SessionFactory
import org.hibernate.boot.Metadata
import org.hibernate.boot.MetadataSources
import org.hibernate.boot.SessionFactoryBuilder
import org.hibernate.boot.registry.StandardServiceRegistryBuilder
import org.springframework.data.repository.findByIdOrNull
import org.springframework.web.bind.annotation.*
import pl.pwr.bazdany.BCryptPasswordEncoder
import pl.pwr.bazdany.domain.Token
import pl.pwr.bazdany.domain.User
import pl.pwr.bazdany.repo.TokenRepository
import pl.pwr.bazdany.repo.UserRepository
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*


@RestController
class LoginController(
        private val userRepo: UserRepository,
        private val tokenRepository: TokenRepository,
        private val bcryptencoder: BCryptPasswordEncoder
) {

    @PostMapping("/applogin")
    fun login(@RequestBody request: LoginRequest): LoginResponse{
        val request = request.copy(email = request.email.toLowerCase())
        val user = userRepo.findByEmail(request.email)
        user ?: throw RequestException("Błędne dane")

        val encPwd = bcryptencoder.encode(request.password)
        if(encPwd != user.password)
            throw RequestException("Błędne dane")

        val token = Token(
                token = UUID.randomUUID().toString(),
                userId = user
        )

        tokenRepository.saveAndFlush(token)

        val userDto = user.toDto()

        return LoginResponse(
                userId = user.id!!,
                token = token.token!!,
                expireAt = token.expiryDate!!,
                userDto = userDto
        )
    }

    @GetMapping("/api/logout")
    fun logout(@RequestAttribute("user_id") userId: Long){
        tokenRepository.deleteAllByUserId(User(userId))
    }
}

data class LoginRequest(
        val email: String,
        val password: String
)

data class LoginResponse(
        val userId: Long,
        val token: String,
        @JsonFormat(pattern = "dd-MM-yyyy-HH:mm:ss")
        val expireAt: LocalDateTime,
        val userDto: UserDto
)

data class UserDto(
        val name: String,
        val surname: String,
        val email: String,
        @JsonFormat(pattern = "dd-MM-yyyy")
        val birth_day: LocalDate,
        val weight: Int?,
        val height: Int?
)

fun User.toDto() = pl.pwr.bazdany.controller.UserDto(
        name!!,
        surname!!,
        email!!,
        birthDate!!,
        weight,
        height
)
