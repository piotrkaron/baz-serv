package pl.pwr.bazdany

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])
class BazdanyApplication{

    @Bean
    fun bcrypt() = BCryptPasswordEncoder()

}

fun main(args: Array<String>) {
    runApplication<BazdanyApplication>(*args)
}


class BCryptPasswordEncoder(){

    private val key = 5;

    fun encode(str: String): String = str.asIterable()
            .map { it - key }
            .fold("") { acc, c -> acc + c }

    fun decode(str: String): String = str.asIterable()
            .map { it - key }
            .fold("") { acc, c -> acc + c }

}