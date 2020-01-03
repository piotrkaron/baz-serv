package pl.pwr.bazdany.repo
import pl.pwr.bazdany.domain.Training
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * Spring Data  repository for the [Training] entity.
 */
@Suppress("unused")
@Repository
interface TrainingRepository : JpaRepository<Training, Long> {
}
