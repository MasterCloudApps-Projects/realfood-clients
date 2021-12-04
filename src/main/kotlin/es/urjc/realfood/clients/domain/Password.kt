package es.urjc.realfood.clients.domain

import javax.persistence.Embeddable

@Embeddable
class Password(private val value: String
) {

    init {
        if (value.isNullOrBlank())
            throw IllegalArgumentException("Password cannot be empty")
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Password

        if (value != other.value) return false

        return true
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    override fun toString(): String {
        return value
    }

}