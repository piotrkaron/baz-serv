package pl.pwr.bazdany.controller

import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.data.repository.findByIdOrNull
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestAttribute
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import pl.pwr.bazdany.BCryptPasswordEncoder
import pl.pwr.bazdany.repo.UserRepository
import java.time.LocalDate

@RestController
class AccountController(
        private val userRepo: UserRepository,
        private val encoder: BCryptPasswordEncoder
) {

    @PostMapping("/api/account")
    fun edit(@RequestBody editRequest: EditRequest,
             @RequestAttribute("user_id") userId: Long
    ) {
        val user = userRepo.findByIdOrNull(userId)!!

        with(editRequest){
            if (password != null) {
                user.password = encoder.encode(password)
            }
            if (name != null) user.name = name
            if (surname != null) user.surname = surname
            if (birth_date != null) user.birthDate = birth_date
            if (weight != null) user.weight = weight
            if (height != null) user.height = height
        }

        userRepo.saveAndFlush(user)
    }

}

data class EditRequest(
        val userId: Long,
        val password: String?,
        val name: String?,
        val surname: String?,
        @DateTimeFormat(pattern = "dd-MM-yyy")
        @JsonFormat(pattern = "dd-MM-yyyy")
        val birth_date: LocalDate?,
        val weight: Int?,
        val height: Int?
)