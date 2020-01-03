package pl.pwr.bazdany.repo
import pl.pwr.bazdany.domain.Groups
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * Spring Data  repository for the [Groups] entity.
 */
@Suppress("unused")
@Repository
interface GroupsRepository : JpaRepository<Groups, Long> {
}
