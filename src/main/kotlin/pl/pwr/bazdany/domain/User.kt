package pl.pwr.bazdany.domain

import java.io.Serializable
import java.time.LocalDate
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "user")
class User(

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "ID")
        var id: Long? = null,

        @Column(name = "email", unique = true)
        @field:NotNull
        var email: String? = null,

        @Column(name = "password")
        @field:NotNull
        var password: String? = null,

        @Column(name = "name")
        @field:NotNull
        var name: String? = null,

        @Column(name = "surname")
        @field:NotNull
        var surname: String? = null,

        @Column(name = "birth_date")
        @field:NotNull
        var birthDate: LocalDate? = null,

        @Column(name = "weight")
        var weight: Int? = null,

        @Column(name = "height")
        var height: Int? = null,

/*
        @OneToOne
        //@JoinColumn(unique = true, name = "token_id", referencedColumnName = "ID")
        var token: Token? = null,
*/

        @ManyToMany
        @JoinTable(name = "usergroupmap",
                joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")],
                inverseJoinColumns = [JoinColumn(name = "group_id", referencedColumnName = "id")])
        var groups: MutableSet<Groups> = mutableSetOf(),

        @OneToMany(mappedBy = "userId")
        var trainings: MutableList<Training> = mutableListOf()

) : Serializable {

    fun addGroups(groups: Groups): User {
        this.groups.add(groups)
        return this
    }

    fun removeGroups(groups: Groups): User {
        this.groups.remove(groups)
        return this
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is User) return false
        if (other.id == null || id == null) return false

        return id == other.id
    }

    override fun hashCode() = 31

    override fun toString() = "User2{" +
            "id=$id" +
            ", email='$email'" +
            ", password='$password'" +
            ", name='$name'" +
            ", surname='$surname'" +
            ", birthDate='$birthDate'" +
            ", weight=$weight" +
            ", height=$height" +
            "}"


    companion object {
        private const val serialVersionUID = 1L
    }
}