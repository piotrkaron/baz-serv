package pl.pwr.bazdany.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

class RequestException(val msg: String) : RuntimeException(msg)

@ControllerAdvice
class ErrorCatcher {

    @ExceptionHandler
    fun catchError(ex: RequestException) = ResponseEntity<String>(ex.msg, HttpStatus.BAD_REQUEST)

    @ExceptionHandler
    fun catchError(ex: RuntimeException) = ResponseEntity(ex.message ?: "", HttpStatus.INTERNAL_SERVER_ERROR)
}