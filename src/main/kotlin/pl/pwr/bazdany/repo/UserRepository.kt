package pl.pwr.bazdany.repo
import pl.pwr.bazdany.domain.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

import java.util.Optional

/**
 * Spring Data  repository for the [User] entity.
 */
@Repository
interface UserRepository : JpaRepository<User, Long> {

    @Query(
        value = "select distinct user2 from User user2 left join fetch user2.groups",
        countQuery = "select count(distinct user2) from User user2"
    )
    fun findAllWithEagerRelationships(pageable: Pageable): Page<User>

    @Query(value = "select distinct user2 from User user2 left join fetch user2.groups")
    fun findAllWithEagerRelationships(): MutableList<User>

    @Query("select user2 from User user2 left join fetch user2.groups where user2.id =:id")
    fun findOneWithEagerRelationships(@Param("id") id: Long): Optional<User>
}
