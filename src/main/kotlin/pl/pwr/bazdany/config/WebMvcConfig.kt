package pl.pwr.bazdany.config

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import pl.pwr.bazdany.controller.Error
import pl.pwr.bazdany.controller.UnauthorizedException
import pl.pwr.bazdany.repo.TokenRepository
import java.net.http.HttpResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Configuration
class WebMvcConfig(
        private val authInterceptor: AuthInterceptor
): WebMvcConfigurer{

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/api/**")
    }
}

@Component
class AuthInterceptor(
        val tokenRepo: TokenRepository,
        val objectMapper: ObjectMapper
): HandlerInterceptor{

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val header: String = request.getHeader("Authorization")
                ?: return err(response, "No header")

        val extracted = header.replace("""Bearer """, "")

        val token = tokenRepo.findByToken(extracted)

        token?: return err(response, "Wrong token")

        if(token.isExpired()) return err(response,"Token expired")

        request.setAttribute("user_id", token.id!!)

        return true
    }

    private fun err(response: HttpServletResponse, msg: String): Boolean{
        val error = Error(msg)

        response.contentType = "application/json";
        response.status = HttpServletResponse.SC_UNAUTHORIZED;
        response.writer.write(objectMapper.writeValueAsString(error));
        return false
    }
}