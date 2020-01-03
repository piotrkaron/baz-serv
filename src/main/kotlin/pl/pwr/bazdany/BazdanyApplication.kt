package pl.pwr.bazdany

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BazdanyApplication

fun main(args: Array<String>) {
    runApplication<BazdanyApplication>(*args)
}
