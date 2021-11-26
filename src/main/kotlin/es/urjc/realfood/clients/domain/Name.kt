package es.urjc.realfood.clients.domain

import java.lang.IllegalArgumentException
import javax.persistence.Embeddable

@Embeddable
class Name(private val value: String) {

    init {
        if (value.isNullOrBlank())
            throw IllegalArgumentException("Name cannot be empty")
    }
}