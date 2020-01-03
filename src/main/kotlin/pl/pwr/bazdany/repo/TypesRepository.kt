package pl.pwr.bazdany.repo
import pl.pwr.bazdany.domain.Types
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * Spring Data  repository for the [Types] entity.
 */
@Suppress("unused")
@Repository
interface TypesRepository : JpaRepository<Types, Long> {
}
