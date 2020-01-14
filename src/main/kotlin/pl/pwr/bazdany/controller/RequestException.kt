package pl.pwr.bazdany.controller

open class RequestException(val msg: String) : RuntimeException(msg)

class NotFoundException(val msg: String?): RuntimeException(msg)

class UnauthorizedException(msg: String? = null)
    : RequestException(
        if(msg == null){
            "Brak autoryzacji"
        }else{
            "Brak autoryzacji: $msg"
        }
    )

data class Error(
    val error: String?
)