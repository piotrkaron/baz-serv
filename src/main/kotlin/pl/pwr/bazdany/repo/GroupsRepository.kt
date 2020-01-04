package pl.pwr.bazdany.repo
import pl.pwr.bazdany.domain.Groups
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import pl.pwr.bazdany.domain.User

/**
 * Spring Data  repository for the [Groups] entity.
 */
@Suppress("unused")
@Repository
interface GroupsRepository : JpaRepository<Groups, Long> {
    fun findAllByUsers_Id(id: User): List<Groups>
}
