package es.urjc.realfood.clients.domain

import javax.persistence.Embeddable

@Embeddable
class LastName(private val value: String) {

    init {
        if (value.isNullOrBlank())
            throw IllegalArgumentException("Last name cannot be empty")
    }

    override fun toString(): String {
        return value
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LastName

        if (value != other.value) return false

        return true
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

}