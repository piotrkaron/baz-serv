package pl.pwr.bazdany.domain

import com.fasterxml.jackson.annotation.JsonIgnore

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToMany
import javax.persistence.Table

import java.io.Serializable
import java.time.LocalDateTime
import javax.validation.constraints.NotNull

@Entity
@Table(name = "training_groups")
class Groups(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    var id: Long? = null,

    @Column(name = "name", unique = true, nullable = false)
    @field:NotNull
    var name: String? = null,

    @Column(name = "date_created",
            columnDefinition = "timestamp default current_timestamp() null")
    var dateCreated: LocalDateTime? = null,

    @Column(name = "city", nullable = false)
    @field:NotNull
    var city: String? = null,

    @ManyToMany(mappedBy = "groups")
    @JsonIgnore
    var users: MutableSet<User> = mutableSetOf()

) : Serializable {

    fun addUsers(user: User): Groups {
        this.users.add(user)
        user.groups.add(this)
        return this
    }

    fun removeUsers(user: User): Groups {
        this.users.remove(user)
        user.groups.remove(this)
        return this
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Groups) return false
        if (other.id == null || id == null) return false

        return id == other.id
    }

    override fun hashCode() = 31

    override fun toString() = "Groups{" +
        "id=$id" +
        ", name='$name'" +
        ", dateCreated=$dateCreated" +
        ", city='$city'" +
        "}"


    companion object {
        private const val serialVersionUID = 1L
    }
}
