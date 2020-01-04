package pl.pwr.bazdany.domain

import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.NotNull

/**
 * A Training.
 */
@Entity
@Table(name = "training")
class Training(

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "ID")
        var id: Long? = null,

        @Column(name = "duration", nullable = false)
        var duration: Int? = null,

        @Column(name = "file_path", unique = true)
        var filePath: String? = null,

        @Column(name = "date", columnDefinition = "timestamp default current_timestamp() not null on update current_timestamp()")
        var date: LocalDateTime? = null,

        @OneToOne
        @JoinColumn(name = "type_id", referencedColumnName = "ID")
        var typeId: Types? = null,

        @ManyToOne
        @JoinColumn(name ="user_id", referencedColumnName = "ID")
        var user: User? = null

) : Serializable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Training) return false
        if (other.id == null || id == null) return false

        return id == other.id
    }

    override fun hashCode() = 31

    override fun toString() = "Training{" +
            "id=$id" +
            ", userId=$user" +
            ", typeId=$typeId" +
            ", duration=$duration" +
            ", filePath='$filePath'" +
            ", date=$date" +
            "}"


    companion object {
        private const val serialVersionUID = 1L
    }
}
