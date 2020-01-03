package pl.pwr.bazdany.repo
import pl.pwr.bazdany.domain.Token
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * Spring Data  repository for the [Token] entity.
 */
@Suppress("unused")
@Repository
interface TokenRepository : JpaRepository<Token, Long> {
}
