package pl.pwr.bazdany

import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import pl.pwr.bazdany.controller.LoginRequest
import pl.pwr.bazdany.domain.User
import java.time.LocalDate

const val EMAIL = "A@A.pl"
const val PASSWORD = "abcdef"
const val NAME = "name"
const val SURNAME = "nenene"
val BIRTH_DATE = LocalDate.of(1999, 12, 12)
const val WEIGHT = 65
const val HEIGHT = 190

object Util {

    val dummyUser: User
        get() = User(null, EMAIL, PASSWORD, NAME, SURNAME, BIRTH_DATE, WEIGHT, HEIGHT, mutableSetOf(), mutableListOf())

    val loginReq: LoginRequest
        get() = LoginRequest("email", "password")
}

fun Any.toJson(): String = jacksonObjectMapper()
        .registerModules(JavaTimeModule(), Jdk8Module())
        .writeValueAsString(this)

fun mapper() = jacksonObjectMapper()
        .registerModules(JavaTimeModule(), Jdk8Module())