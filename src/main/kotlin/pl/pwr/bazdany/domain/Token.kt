package pl.pwr.bazdany.domain

import com.fasterxml.jackson.annotation.JsonIgnore

import java.io.Serializable
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "token")
class Token(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    var id: Long? = null,

    @Column(name = "token")
    @field:NotNull
    var token: String? = null,

    @Column(name = "date_created")
    var dateCreated: Long? = null,

    @Column(name = "expiry_date")
    @field:NotNull
    var expiryDate: Long? = null,

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


    companion object {
        private const val serialVersionUID = 1L
    }
}
