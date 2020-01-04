package pl.pwr.bazdany.repo
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import pl.pwr.bazdany.domain.Training
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.time.LocalDateTime

@Suppress("unused")
@Repository
interface TrainingRepository : JpaRepository<Training, Long> {
    fun findAllByUser_Id(id: Long): List<Training>

    fun findAllByDateBetweenAndUser_Id(date: LocalDateTime, date2: LocalDateTime, user_id: Long): List<Training>
}

