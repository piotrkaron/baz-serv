package pl.pwr.bazdany.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

import java.io.Serializable
import javax.validation.constraints.NotNull

@Entity
@Table(name = "types")
class Types(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,

    @Column(name = "name", unique = true)
    @field:NotNull
    var name: String? = null,

    @Column(name = "calories_per_unit")
    @field:NotNull
    var caloriesPerUnit: Double? = null

) : Serializable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Types) return false
        if (other.id == null || id == null) return false

        return id == other.id
    }

    override fun hashCode() = 31

    override fun toString() = "Types{" +
        "id=$id" +
        ", name='$name'" +
        ", caloriesPerUnit=$caloriesPerUnit" +
        "}"


    companion object {
        private const val serialVersionUID = 1L
    }
}
