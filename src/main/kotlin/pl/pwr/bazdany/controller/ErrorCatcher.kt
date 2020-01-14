package pl.pwr.bazdany.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ErrorCatcher {

    @ExceptionHandler
    fun unauthorized(ex: UnauthorizedException) = error(ex.msg, HttpStatus.UNAUTHORIZED)

    @ExceptionHandler
    fun catchError(ex: RequestException) = error(ex.msg, HttpStatus.BAD_REQUEST)

    @ExceptionHandler
    fun catchError(ex: RuntimeException) = error(ex.message, HttpStatus.INTERNAL_SERVER_ERROR)

    @ExceptionHandler
    fun catchError(ex: NotFoundException) = error(ex.msg
            , HttpStatus.NOT_FOUND)

    private fun error(msg: String?, status: HttpStatus)
            = ResponseEntity(Error(msg
            ?: ""), status)
}