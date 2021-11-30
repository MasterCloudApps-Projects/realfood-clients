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

}