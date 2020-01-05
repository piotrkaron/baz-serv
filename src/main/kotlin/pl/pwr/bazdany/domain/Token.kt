package pl.pwr.bazdany.domain

import com.fasterxml.jackson.annotation.JsonIgnore

import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "token")
class Token(

        @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    var id: Long? = null,

        @Column(name = "token", nullable = false, unique = true)
    @field:NotNull
    var token: String? = null,

        @Column(name = "date_created",
            columnDefinition = "timestamp default current_timestamp() not null on update current_timestamp()",
            nullable = false)
    var dateCreated: LocalDateTime? = LocalDateTime.now(),

        @Column(name = "expiry_date",
            columnDefinition = "timestamp default current_timestamp()",
            nullable = false)
    var expiryDate: LocalDateTime? = LocalDateTime.now().plusDays(10),

        @OneToOne
    @JsonIgnore
    @field:NotNull
    @JoinColumn(name = "user_id", referencedColumnName = "ID")
    var userId: User? = null

) : Serializable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Token) return false
        if (other.id == null || id == null) return false

        return id == other.id
    }

    override fun hashCode() = 31

    override fun toString() = "Token{" +
        "id=$id" +
        ", token='$token'" +
        ", dateCreated=$dateCreated" +
        ", expiryDate=$expiryDate" +
        ", userId=$userId" +
        "}"

    fun isExpired(): Boolean {
        return LocalDateTime.now().isAfter(expiryDate)
    }


    companion object {
        private const val serialVersionUID = 1L
    }
}
