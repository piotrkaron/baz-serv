package pl.pwr.bazdany.repo
import pl.pwr.bazdany.domain.Training
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Suppress("unused")
@Repository
interface TrainingRepository : JpaRepository<Training, Long> {
    fun findAllByUser_Id(id: Long): List<Training>
}
