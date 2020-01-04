package pl.pwr.bazdany.controller

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.data.repository.findByIdOrNull
import org.springframework.web.bind.annotation.*
import pl.pwr.bazdany.domain.Groups
import pl.pwr.bazdany.domain.User
import pl.pwr.bazdany.repo.GroupsRepository
import pl.pwr.bazdany.repo.UserRepository
import java.time.LocalDateTime

@RestController
class GroupController(
        private val userRepository: UserRepository,
        private val groupsRepository: GroupsRepository
) {

    @GetMapping("/api/groups")
    fun getGroups(): List<GroupDto> = groupsRepository.findAll().map(Groups::toDto)

    @GetMapping("/api/groups/mine")
    fun getUserGroups(@RequestAttribute("user_id") id: Long): List<GroupDto>
            = groupsRepository.findAllByUsers_Id(User(id)).map(Groups::toDto)

    @PostMapping("/api/group")
    fun createGroup(@RequestAttribute("user_id") userId: Long,
                    @RequestBody group: CreateGroupRequest
    ): GroupCreatedResponse {
        val user = userRepository.findByIdOrNull(userId)!!

        var groupDomain = group.toDomain(mutableSetOf(user))

        groupDomain = groupsRepository.saveAndFlush(groupDomain)

        return GroupCreatedResponse(groupDomain.id!!)
    }

    @PutMapping("/api/group")
    fun joinGroup(@RequestAttribute("user_id") userId: Long,
                  @RequestBody join: JoinGroupRequest){

        val group = groupsRepository.findByIdOrNull(join.groupId)
                ?: throw NotFoundException("Grupa nie istnieje")

        val user = userRepository.findByIdOrNull(userId)!!

        group.addUsers(user)

        groupsRepository.saveAndFlush(group)
    }
}

fun Groups.toDto() = GroupDto(
        name!!, city!!, dateCreated!!, users.size
)

data class GroupDto(
        val name: String,
        val city: String,
        @JsonProperty("dd-MM-yyyy-HH:mm:ss")
        val created: LocalDateTime,
        val membersCount: Int
)

data class CreateGroupRequest(
        val name: String,
        val city: String
)

fun CreateGroupRequest.toDomain(users: MutableSet<User>) = Groups(
        null, name, null, city, mutableSetOf()
).apply {
    users.forEach{ addUsers(it)}
}

data class GroupCreatedResponse(
        val groupId: Long
)

data class JoinGroupRequest(
        val groupId: Long
)