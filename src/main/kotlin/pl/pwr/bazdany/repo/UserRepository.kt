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
        value = "select distinct user from User user left join fetch user.groups",
        countQuery = "select count(distinct user) from User user"
    )
    fun findAllWithEagerRelationships(pageable: Pageable): Page<User>

    @Query(value = "select distinct user from User user left join fetch user.groups")
    fun findAllWithEagerRelationships(): MutableList<User>

    @Query("select user from User user left join fetch user.groups where user.id =:id")
    fun findOneWithEagerRelationships(@Param("id") id: Long): Optional<User>

   // @Query("select user from User user where user.email =:email")
    fun findByEmail(@Param("email") email: String): User?

}
