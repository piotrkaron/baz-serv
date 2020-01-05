package pl.pwr.bazdany.controller

import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import pl.pwr.bazdany.domain.Training
import pl.pwr.bazdany.domain.Types
import pl.pwr.bazdany.domain.User
import pl.pwr.bazdany.repo.TrainingRepository
import pl.pwr.bazdany.repo.TypesRepository
import pl.pwr.bazdany.repo.UserRepository
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*

@RestController
class TrainingController(
        private val trainingRepo: TrainingRepository,
        private val userRepo: UserRepository,
        private val typeRepo: TypesRepository
) {

    @GetMapping("/api/trainings/date")
    fun getAllTrainingsDate(@RequestAttribute("user_id") id: Long,
                        @RequestBody dateDto: DateRangeDto
    ): List<TrainingDto> {
        val start = LocalDateTime.of(dateDto.start, LocalTime.MIN)
        val end = LocalDateTime.of(dateDto.end, LocalTime.MAX)

        val trainings = trainingRepo.findAllByDateBetweenAndUser_Id(start, end, id)

        return trainings.map(Training::toDto)
    }

    @GetMapping("/api/trainings/all/{num}")
    fun getAllTrainings(@RequestAttribute("user_id") id: Long,
                        @PathVariable("num") pageNum: Int
    ): List<TrainingDto> {
        val pageable: Pageable = PageRequest.of(pageNum, 20)
        val trainings = trainingRepo.findAll(pageable)

        return trainings.content.map(Training::toDto)
    }

    @GetMapping("/api/trainings")
    fun getTrainings(@RequestAttribute("user_id") id: Long): List<TrainingDto> {
        val trainings = trainingRepo.findAllByUser_Id(id)

        return trainings.map(Training::toDto)
    }

    @PostMapping("/api/training")
    @ResponseStatus(HttpStatus.CREATED)
    fun addTraining(@RequestAttribute("user_id") id: Long,
                    @RequestBody newTraining: NewTrainingDto): UploadResponse {

        val type: Types = typeRepo.findByName(newTraining.type)
                ?: typeRepo.findByName("undefined")!!

        val simulatedPath = "path/${UUID.randomUUID()}"

        val user = userRepo.findByIdOrNull(id)!!

        var training = newTraining.toDomain(simulatedPath, type, user)

        training = trainingRepo.saveAndFlush(training)

        return UploadResponse(training.id!!)
    }

    @GetMapping("/api/training/{id}")
    fun getTraining(@RequestAttribute("user_id") id: Long,
                    @PathVariable(name = "id") trainingId: Long): TrainingDto {

        val training = trainingRepo.findByIdOrNull(trainingId)
                ?: throw NotFoundException("Training not found")
        return training.toDto()
    }
}

data class UploadResponse(
        val trainingId: Long
)

data class NewTrainingDto(
        val duration: Int,
        @DateTimeFormat(pattern = "dd-MM-yyy-HH-mm-ss")
        @JsonFormat(pattern = "dd-MM-yyyy-HH-mm-ss")
        val date: LocalDateTime,
        val type: String,
        val rawData: String
)

fun NewTrainingDto.toDomain(
        path: String,
        type: Types,
        user: User
) = Training(
        null, duration, path, date, type, user
)

data class TypeDto(
        val name: String,
        val calories: Double
)

data class TrainingDto(
        val id: Long,
        val duration: Int,
        @DateTimeFormat(pattern = "dd-MM-yyy HH:mm:ss")
        @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
        val date: LocalDateTime,
        val type: TypeDto,
        val owner: OwnerDto
)

data class OwnerDto(
        val name: String,
        val surname: String
)

fun Training.toDto() = TrainingDto(
        id!!, duration!!, date!!, typeId!!.toDto(), OwnerDto(user!!.name!!, user!!.surname!!)
)

fun Types.toDto() = TypeDto(
        name!!, caloriesPerUnit!!
)

data class DateRangeDto(
        @JsonFormat(pattern = "dd-MM-yyyy")
        val start: LocalDate,
        @JsonFormat(pattern = "dd-MM-yyyy")
        val end: LocalDate
)