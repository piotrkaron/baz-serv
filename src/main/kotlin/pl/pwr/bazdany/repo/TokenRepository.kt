package pl.pwr.bazdany.repo
import pl.pwr.bazdany.domain.Token
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import pl.pwr.bazdany.domain.User
import javax.validation.constraints.NotNull

/**
 * Spring Data  repository for the [Token] entity.
 */
@Suppress("unused")
@Repository
interface TokenRepository : JpaRepository<Token, Long> {
    fun findByUserId(user: User): Token?

    fun deleteByUserId(user: User)
}
