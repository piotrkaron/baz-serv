package pl.pwr.bazdany.controller

import com.fasterxml.jackson.annotation.JsonFormat
import org.slf4j.LoggerFactory
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import pl.pwr.bazdany.BCryptPasswordEncoder
import pl.pwr.bazdany.domain.User
import pl.pwr.bazdany.repo.UserRepository
import java.time.LocalDate

@RestController
class RegisterController(
        private val userRepo: UserRepository,
        private val bcryptencoder: BCryptPasswordEncoder
) {

    private val logger = LoggerFactory.getLogger(javaClass)

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    fun createUser(@RequestBody request: RegisterRequest){
        val currentUser = userRepo.findByEmail(request.email)
        if(currentUser != null) throw RequestException("Użytkownik już istnieje.")

        val user: User
        with(request){
            user = User(
                    email = email,
                    password = bcryptencoder.encode(password),
                    name = name,
                    surname = surname,
                    birthDate = birth_date,
                    weight = weight,
                    height = height
            )
            userRepo.saveAndFlush(user)
            logger.info("Registered user: $name $surname $email")
        }
    }

}

data class RegisterRequest(
        val email: String,
        val password: String,
        val name: String,
        val surname: String,
        @DateTimeFormat(pattern = "dd-MM-yyy")
        @JsonFormat(pattern = "dd-MM-yyyy")
        val birth_date: LocalDate,
        val weight: Int?,
        val height: Int?
)